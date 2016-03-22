package records.order;

import java.util.List;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import records.cart.CartItem;
import records.customer.Customer;

public class CustOrder {
	@OneToOne
	private Customer customer;
	@OneToMany
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
		return "CustOrder [customer=" + customer + ", items=" + items + ", id=" + id + "]";
	}
	
	

}
