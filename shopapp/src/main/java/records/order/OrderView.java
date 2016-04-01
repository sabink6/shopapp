package records.order;

import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import records.cart.CartItem;
import records.cart.CartItemService;
import records.cart.CartView;
import records.customer.Customer;
import records.customer.CustomerService;
import records.product.Product;
import records.product.ProductService;

@ManagedBean
@ApplicationScoped//@RequestScoped
public class OrderView {

	static final String TAG = OrderView.class.getSimpleName().toUpperCase();

	@ManagedProperty("#{orderService}")
	private OrderService orderService;
	@ManagedProperty("#{customerService}")
	private CustomerService customerService;
	@ManagedProperty("#{cartItemService}")
	private CartItemService cartItemService;
	@ManagedProperty("#{productService}")
	private ProductService productService;

	private CustOrder order;
	private Customer customer;
	private List<CartItem> items;
	private Product product;
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
	
	public ProductService getProductService() {
		return productService;
	}

	public void setProductService(ProductService productService) {
		this.productService = productService;
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
	
	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
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
			setTotal(0);
			for(CartItem i : items){
				total = total + i.getTotalPrice();
			}
		}
	}
	
	public void pay(ActionEvent e){
		System.out.println(TAG + " in pay listener ");
		}
	
	public String pay(){
		System.out.println(TAG + " in pay ");
		items = cartItemService.findAll();
		for(CartItem i : items){
			int productQty = i.getProduct().getQty();
			int cartItemQty = i.getAmount();
			int productAmount = productQty-cartItemQty;
			int productId = Integer.valueOf(i.getProduct().getId());
			product = productService.findProductById(productId);
			product.setQty(productAmount);
			productService.save(product);
			i.getProduct().setQty(productAmount);
			System.out.println(TAG + " product qty: " + productAmount);	
		}
//		set thecart to empty or loose a scope??
		//CartView cv = new CartView();
		return "./shop.xhtml?faces-redirect=true";
	}



}
