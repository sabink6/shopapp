package records.cart;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.persistence.criteria.Order;

import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.primefaces.context.RequestContext;
import org.primefaces.util.Constants;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.ContextLoader;

import javafx.event.ActionEvent;
import records.customer.Customer;
import records.customer.CustomerService;
import records.order.CustOrder;
import records.order.OrderService;
import records.product.Product;
import records.product.ProductService;

@ManagedBean
@ApplicationScoped
public class CartView {

	static final String TAG = CartView.class.getSimpleName().toUpperCase();

	@ManagedProperty("#{cartItemService}")
	private CartItemService store;
	@ManagedProperty("#{productService}")
	private ProductService productService;
	@ManagedProperty("#{orderService}")
	private OrderService orderService;
	@ManagedProperty("#{customerService}")
	private CustomerService customerService;

	private List<CartItem> thecart;
	private CartItem cartItem;
	private List<Product> products;
	private CustOrder order;
	private Customer customer;

	private int orderId;

	public int getOrderId() {
		return orderId;
	}

	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}

	public CartItemService getStore() {
		return store;
	}

	public void setStore(CartItemService store) {
		this.store = store;
	}

	public ProductService getProductService() {
		return productService;
	}

	public void setProductService(ProductService productService) {
		this.productService = productService;
	}

	public OrderService getOrderService() {
		return orderService;
	}

	public void setOrderService(OrderService orderService) {
		this.orderService = orderService;
	}

	public List<Product> getProducts() {
		products = productService.findAll();
		return products;
	}

	public void setProducts(List<Product> products) {
		this.products = products;
	}

	public CustomerService getCustomerService() {
		return customerService;
	}

	public void setCustomerService(CustomerService customerService) {
		this.customerService = customerService;
	}

	public List<CartItem> getThecart() {
		//theCart = store.findAll();
		System.out.println(TAG + " getThecart");
		return thecart;
	}

	public void setTheCart(List<CartItem> theCart) {
		this.thecart = theCart;
	}

	public CartItem getCartItem() {
		return cartItem;
	}

	public void setCartItem(CartItem cartItem) {
		this.cartItem = cartItem;
	}

	@PostConstruct
	public void makeCartItem(){
		cartItem = new CartItem();
		thecart = new ArrayList<CartItem>();
		System.out.println(TAG + " postconstruct cartItem");
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

	//create a new CartItem object and add it into an arrayList of CartItems
	public String buy(Product p){
		System.out.println(TAG + " selected product to buy: " + p.toString());
		thecart.add(cartItem);
		cartItem.setProduct(p);
		cartItem.setAmount(1);
		//System.out.println(TAG + " about to save cartItem: " + cartItem.toString());
		//store.save(cartItem);
		System.out.println(TAG + " saved cartItem to Thecart: " + cartItem.toString());
		cartItem = new CartItem();

		//System.out.println(TAG + " added to store");
		return "";
	}


	//	public void remove(CartItem ci){
	//		//removes CartItem from database
	//		System.out.println(TAG + " about to remove from store" + ci.toString());
	//		store.remove(ci);
	//		//on the view update=":cartTable, :messages"
	//		thecart = store.findAll();
	//		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "CartItem removed!", null));
	//	}

	public void update(){
	}

	private String email;

	private String password;

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setThecart(List<CartItem> thecart) {
		this.thecart = thecart;
	}

	private boolean loggedIn;


	public boolean isLoggedIn() {
		return loggedIn;
	}

	public void setLoggedIn(boolean loggedIn) {
		this.loggedIn = loggedIn;
	}

	public String checkout() {
			
			System.out.println(TAG + " email: " + email);
			System.out.println(TAG + " password: " + password);
			RequestContext context = RequestContext.getCurrentInstance();
			FacesMessage message = null;
			
			customer = customerService.loginCustomer(email, password);
			
			if(customer != null){
				System.out.println(TAG + " Customer in the login " + customer.getEmail() + " " + customer.getPassword());
				message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Welcome", email);
				loggedIn = true;
			}else{
				message = new FacesMessage(FacesMessage.SEVERITY_WARN, "Loggin Error", "Invalid credentials");
				loggedIn = false;
			}
			
			//message can't be null when message goes back
			FacesContext.getCurrentInstance().addMessage(null, message);
			context.addCallbackParam("loggedIn", loggedIn);
			
			if(loggedIn){
				//create CustOrder
				System.out.println(TAG + " The Cart: " + thecart.toString());
				order = new CustOrder();
				order.setItems(thecart);
				order.setCustomer(customer);
				orderService.save(order);
	
				List<CustOrder> orders = new ArrayList<CustOrder>();
				orders.add(order);
				customer.setCustOrders(orders);
				//creates an entry in customer_custorder table otherwise empty set
				customerService.save(customer);
				System.out.println(TAG + " Customer saved: " + customer);
				orderId = order.getId();
				System.out.println(TAG + " OrderId to be sent: " + orderId);
				return "./order.xhtml?faces-redirect=true&orderId=" + orderId +"\"";
			}
			return null;
			
	
		}
	
	//this doesn't work 
	public String goOrder(){
		return "./order.xhtml";
	}

	public String goCart(){
		return "./cart.xhtml";
	}

	public String goAdmin(){
		return "./admin.xhtml";
	}

}
