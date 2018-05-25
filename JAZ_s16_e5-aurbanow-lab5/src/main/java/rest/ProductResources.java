package rest;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
//import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import domain.Comment;
import domain.Product;

@Path("/products")
//zapewnia transakcyjnoœæ
@Stateless
public class ProductResources {

	@PersistenceContext
	//zmienna metody dodawania do bazy - wykorzystanie EntityManagera
	EntityManager em;
	
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<Product> getAllProducts() {
		return em.createNamedQuery("product.all", Product.class).getResultList();
	}
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public Response addProduct(Product product) {

		em.persist(product);
		return Response.ok(product.getId()).build();
	}
	
	@GET
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getProductById(@PathParam("id") int id){
		Product result = em.createNamedQuery("product.id", Product.class)
				.setParameter("productId", id)
				.getSingleResult();
		if(result==null){
			return Response.status(404).build();
		}
		return Response.ok(result).build();
	}
	

	@PUT
	@Path("/{id}")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response update(@PathParam("id") int id, Product p){
		Product result = em.createNamedQuery("product.id", Product.class)
				.setParameter("productId", id)
				.getSingleResult();
		if(result==null)
			return Response.status(404).build();
		result.setName(p.getName());
		result.setPrice(p.getPrice());
		result.setCategory(p.getCategory());
		em.persist(result);
		return Response.ok().build();
	}
	
	
	@GET
	@Path("/byName/{Name}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getProductByName(@PathParam("name") String name){
		Product result = em.createNamedQuery("product.name", Product.class)
				.setParameter("productName", name)
				.getSingleResult();
		if(result==null){
			return Response.status(404).build();
		}
		return Response.ok(result).build();
	}
	
	
	@GET
	@Path("/byCategory/{category}")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Product> getProductByCategory(@PathParam("category") String category){
		return em.createNamedQuery("product.category", Product.class)
				.setParameter("productCategory", category)
				.getResultList();
	}
	
	
	@GET
	@Path("/byPrice/{min}/{max}")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Product> getProductByPrice(@PathParam("min") int min, @PathParam("max") int max)
	{
		return em.createNamedQuery("product.price", Product.class)
				         .setParameter("min", min)
				         .setParameter("max", max)
				         .getResultList();
	}
	
	//dodawanie komentarzy do danego produktu
	@POST
	@Path("/{productId}/comments")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response addComment(@PathParam("productId") int productId, Comment comment){
		Product result = em.createNamedQuery("product.id", Product.class)
				.setParameter("productId", productId)
				.getSingleResult();
		if(result==null) {
			return Response.status(404).build();
		}
		result.getComments().add(comment);
		comment.setProduct(result);
		em.persist(comment);
		return Response.ok().build();
	}
	
	//wyswietlanie komentarzy danego produktu
	@GET
	@Path("/{productId}/comments")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Comment> getComments(@PathParam("productId") int productId)
	{
		Product result = em.createNamedQuery("product.id", Product.class)
				.setParameter("productId", productId)
				.getSingleResult();
		if(result==null)
		{
			return null;
		}
		return result.getComments();
	}
	//usuwanie komentarzy 
	@DELETE
	@Path("/{productId}/comments/{commentId}")
	public Response deleteComment(@PathParam("ProductId") int productId, @PathParam("commentId") int commentId) {
		Product result = em.createNamedQuery("product.id", Product.class)
				.setParameter("productId", productId)
				.getSingleResult();
		if(result==null) {
			return Response.status(404).build();
		}
		em.remove(result);
		return Response.ok().build();		
	}		
}
