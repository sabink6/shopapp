package records.product;


import java.io.IOException;
import java.io.InputStream;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.primefaces.model.UploadedFile;

@Entity
public class Product {

	private String name;
	private String description;
	private int qty;
	private double price;
	private String imageFile;
	

	@Id
	@GeneratedValue
	private int id;

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
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public int getQty() {
		return qty;
	}
	public void setQty(int i) {
		this.qty = i;
	}
	
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}

	public String getImageFile() {
		return imageFile;
	}
	public void setImageFile(String imageFile) {
		this.imageFile = imageFile;
	}
	@Override
	public String toString() {
		return "Product [name=" + name + ", description=" + description + ", qty=" + qty + ", price=" + price
				+ ", imageFile=" + imageFile 
				+ ", id=" + id + "]";
	}
	
	
	
	

}
