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

}
