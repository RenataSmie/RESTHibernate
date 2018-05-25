package domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;


@XmlRootElement
//klas� traktujemy jako encj�
@Entity
//NamedQuery z parametrem - wyci�ganie danych
@NamedQueries({
	@NamedQuery(name="product.all", query="SELECT p FROM Product p"),
	@NamedQuery(name="product.id", query="FROM Product p WHERE p.id=:productId"),
	@NamedQuery(name="product.price", query="FROM Product p WHERE p.price BETWEEN :min AND :max"),
	@NamedQuery(name="product.name", query="FROM Product p WHERE p.name LIKE :productName"),
	@NamedQuery(name="product.category", query="FROM Product p WHERE p.category LIKE :productCategory")
})
public class Product {
	//oznaczenie pola bedacego polem glownym
	@Id
	//info o tym, jak ma byc generowany klucz glowny
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String name;
	private String category;
	private double price; 
	
//zainicjowa� list� komentarzy w klasie �Product� � inaczej przy operacji na tej li�cie mog� si� pojawi� niechcianie wyj�tki �NullPointerException�.
	private List<Comment> comments = new ArrayList<Comment>();
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	//pole nie b�dzie serializowane
	@XmlTransient
	//m�wi nam jak nazywa si� pole w klasie �Comments� na kt�re ma zmapowa� referencj� do obiektu klasy 
	//�Product�, kt�ry zawiera dany komentarz.
	@OneToMany(mappedBy="product")
	public List<Comment> getComments() {
		return comments;
	}
	public void setComments(List<Comment> comments) {
		this.comments = comments;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	
}
