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
@SessionScoped
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

	public String goCart(){
		return "./cartView.xhtml";
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

	
	public void checkout() {
		System.out.println(TAG + " In the login");
		System.out.println(TAG + " email: " + email);
		System.out.println(TAG + " password: " + password);
		RequestContext context = RequestContext.getCurrentInstance();
		FacesMessage message = null;
		boolean loggedIn = false;
		
		Customer customer = customerService.loginCustomer(email, password);
		if(customer!=null){
			System.out.println(TAG + " Customer in the login " + customer.getEmail() + " " + customer.getPassword());
		}
		
		
		if(email != null && email.equals("joe@cork.com") && password != null && password.equals("1234")) {
			loggedIn = true;
			message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Welcome", email);
		} else {
			loggedIn = false;
			message = new FacesMessage(FacesMessage.SEVERITY_WARN, "Loggin Error", "Invalid credentials");
		}

		FacesContext.getCurrentInstance().addMessage(null, message);
		context.addCallbackParam("loggedIn", loggedIn);
		
		//create CustOrder
		System.out.println(TAG + " The Cart: " + thecart.toString());
		CustOrder o = new CustOrder();
		o.setItems(thecart);
		o.setCustomer(customer);
		orderService.save(o);
		
		Configuration configuration = new Configuration().configure();
		StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties());
		SessionFactory sf = configuration.buildSessionFactory(builder.build());
		sf.getCurrentSession();
		Hibernate.initialize(customer.getCustOrders());
		//.add(o);
	} 


}
