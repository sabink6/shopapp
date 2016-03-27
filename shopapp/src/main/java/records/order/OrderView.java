package records.order;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

import records.cart.CartItem;
import records.cart.CartItemService;
import records.customer.Customer;
import records.customer.CustomerService;

@ManagedBean
@RequestScoped
public class OrderView {

	static final String TAG = OrderView.class.getSimpleName().toUpperCase();

	@ManagedProperty("#{orderService}")
	private OrderService orderService;
	@ManagedProperty("#{customerService}")
	private CustomerService customerService;
	@ManagedProperty("#{cartItemService}")
	private CartItemService cartItemService;



	private CustOrder order;
	private Customer customer;
	private List<CartItem> items;
	private double total;

	public OrderService getOrderService() {
		return orderService;
	}

	public void setOrderService(OrderService orderService) {
		this.orderService = orderService;
	}

	public CustomerService getCustomerService() {
		return customerService;
	}

	public void setCustomerService(CustomerService customerService) {
		this.customerService = customerService;
	}

	public CartItemService getCartItemService() {
		return cartItemService;
	}

	public void setCartItemService(CartItemService cartItemService) {
		this.cartItemService = cartItemService;
	}

	public CustOrder getOrder() {
		return order;
	}

	public void setOrder(CustOrder order) {
		this.order = order;
	}


	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	private String orderId;


	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}


	public List<CartItem> getItems() {
		return items;
	}

	public void setItems(List<CartItem> items) {
		this.items = items;
	}

	public double getTotal() {
		return total;
	}

	public void setTotal(double total) {
		this.total = total;
	}

	public void onload(String id) {
		FacesContext facesContext = FacesContext.getCurrentInstance();
		if (!facesContext.isPostback() && !facesContext.isValidationFailed()) {
			System.out.println(TAG + " OrderId on load: " + id);
			order = orderService.findOrderById(Integer.valueOf(id));
			int custId = Integer.valueOf(order.getCustomer().getId());
			System.out.println(TAG + " CustomerId on load: " + custId);
			//get customer details by Id
			customer = customerService.findCustomerById(custId);
			System.out.println(TAG + " Customer on load: " + customer);
			//getAll CartItems from cartItem
			items = cartItemService.findAll();
			//set total field
			for(CartItem i : items){
				total = total + i.getTotalPrice();
			}
		}
	}



}
