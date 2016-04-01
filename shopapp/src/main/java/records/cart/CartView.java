package records.cart;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import org.primefaces.context.RequestContext;
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
	
	private Product product;
	
	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	private int orderId;
	private String email;
	private String password;
	private boolean loggedIn;
	private boolean inStock;

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
		System.out.println(TAG + " in setThecart " + thecart);
		this.thecart = thecart;
	}
	
	public List<CartItem> getThecart() {
		//not in the db thecart = store.findAll();
		System.out.println(TAG + " in getThecart " + thecart);
		return thecart;
	}

	public boolean isLoggedIn() {
		return loggedIn;
	}

	public void setLoggedIn(boolean loggedIn) {
		this.loggedIn = loggedIn;
	}

	public boolean isInStock() {
		return inStock;
	}

	public void setInStock(boolean inStock) {
		this.inStock = inStock;
	}

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

	public void buy(Product p){
		//create a new CartItem object and add it into an arrayList of CartItems
		System.out.println(TAG + " selected product to buy: " + p.toString());
		thecart.add(cartItem);
		cartItem.setProduct(p);
		cartItem.setAmount(1);
		//do not save in db jet store.save(cartItem);
		System.out.println(TAG + " saved cartItem to Thecart: " + cartItem.toString());
		cartItem = new CartItem();
	}

	public void remove(CartItem ci){
		System.out.println(TAG + " cartitem to be removed: " + ci.toString());
		System.out.println(TAG + " thecart before removal: " + thecart.toString());
		thecart.remove(ci);
		System.out.println(TAG + " thecart after removal: " + thecart.toString());
	}

	public void update(){
		for(CartItem c :getThecart()){
			int productQty = c.getProduct().getQty();
			int cartItemQty = c.getAmount();
			System.out.println(TAG + " product amount: " + productQty);
			System.out.println(TAG + " cartitem amount: " + cartItemQty);
			inStock = productQty>cartItemQty;
			if(!inStock){
				System.out.println(TAG + " ALERT: CAN'T CREATE CUSTOMER ORDER");
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Not enough in stock", null));
			}
		}

	}

	public String checkout() {
		if(!inStock){
			System.out.println(TAG + " ALERT: CAN'T CREATE CUSTOMER ORDER");
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Not enough itms in stock", null));
		}else{
			System.out.println(TAG + " in checkout: " + orderId);
			System.out.println(TAG + " email: " + email);
			System.out.println(TAG + " password: " + password);
			RequestContext context = RequestContext.getCurrentInstance();
			FacesMessage message = null;

			customer = customerService.loginCustomer(email, password);

			if(customer != null){
				System.out.println(TAG + " Customer in the checkout " + customer.getEmail() + " " + customer.getPassword());
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
				//create CustOrder and add it to database
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
		}
		return null;
	}

	public String goOrder(){
		System.out.println(TAG + " in goOrder: " + orderId);
		if(!inStock){
			System.out.println(TAG + " ALERT: CAN'T CREATE CUSTOMER ORDER");
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Not enough itms in stock", null));
		}else{
			if(loggedIn){
				//view updated
				System.out.println(TAG + " The Cart: " + thecart.toString());
				//update database
				store.saveAll(thecart);
				return "./order.xhtml?faces-redirect=true&orderId=" + orderId +"\"";
			}
		}
		return null;
	}

	public String goCart(){
		return "./cart.xhtml?faces-redirect=true";
	}

	public String goAdmin(){
		return "./admin.xhtml";
	}

}
