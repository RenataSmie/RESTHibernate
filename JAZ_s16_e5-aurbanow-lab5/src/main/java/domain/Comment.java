package domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@Entity
public class Comment {

	@Id
	//obs³uga dodawania komentarzy do danego produktu. Relacja bazodanow¹  1-* miêdzy produktem a komentarzami
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	private String commentText;
	
	//referencja do obiektu product w klasie Comment
	@ManyToOne
	private Product product;
	
	public Product getProduct() {
		return product;
	}
	
	public void setProduct(Product product) {
		this.product = product;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getCommentText() {
		return commentText;
	}
	public void setCommentText(String commentText) {
		this.commentText = commentText;
	}
	
}
