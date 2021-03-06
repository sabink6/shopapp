package records.order;

import java.util.Arrays;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import records.cart.CartItem;
import records.customer.Customer;

@Entity
public class CustOrder {
	
	@OneToOne(cascade = {CascadeType.MERGE})
	private Customer customer;
	@OneToMany(cascade = {CascadeType.PERSIST})
	private List<CartItem> items;
	
	@Id
	@GeneratedValue
	private int id;

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public List<CartItem> getItems() {
		return items;
	}

	public void setItems(List<CartItem> items) {
		this.items = items;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return "CustOrder [customer=" + customer + 
				//", items=" + items + 
				", id=" + id + "]";
	}
	
	

}
