package records.cart;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import records.product.Product;

@Entity
public class CartItem{
	
	private int amount;
	private double totalPrice;
	@OneToOne
	private Product product;
	
	
	@Id
	@GeneratedValue
	private int id;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		System.out.println("set amount " + amount);
		this.amount = amount;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}
	
	public double getTotalPrice() {
		return amount * product.getPrice();
		//return totalPrice;
	}

	public void setTotalPrice(double totalPrice) {
		System.out.println("set totalPrice " + totalPrice);
		this.totalPrice = totalPrice;
	}
	
	@Override
	public String toString() {
		return "CartItem [amount=" + amount + ", product=" + product + ", totalPrice=" + totalPrice + ", id=" + id
				+ "]";
	}

	

}
