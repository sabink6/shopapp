package records.cart;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

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
	private List<Customer> customers;
//	private Customer customer;
//	private CustOrder custOrder;

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

	public List<Customer> getCustomers() {
		customers = customerService.findAll();
		return customers;
	}

	public void setCustomers(List<Customer> customers) {
		this.customers = customers;
	}
	
//	public CustOrder getCustOrder() {
//		return custOrder;
//	}
//
//	public void setCustOrder(CustOrder custOrder) {
//		this.custOrder = custOrder;
//	}

//	public Customer getCustomer() {
//		return customer;
//	}
//
//	public void setCustomer(Customer customer) {
//		this.customer = customer;
//	}

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
	
//	@PostConstruct
//	public void makeCustomer(){
//		customer = new Customer();
//		//thecart = new ArrayList<CartItem>();
//		System.out.println(TAG + " postconstruct customer");
//	}


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


	public void remove(CartItem ci){
		//removes CartItem from database
		System.out.println(TAG + " about to remove from store" + ci.toString());
		store.remove(ci);
		//on the view update=":cartTable, :messages"
		thecart = store.findAll();
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "CartItem removed!", null));
	}

	public void update(){

	}

	public String goCart(){
		return "./cartView.xhtml";
	}
	

	public void checkout(Customer c){
		//build up order from cartItems in theCart
		System.out.println(TAG + " The Cart: " + thecart.toString());
		CustOrder o = new CustOrder();
		o.setItems(thecart);
		//TODO: add check for customer
		customers = new ArrayList<Customer>(getCustomers());
		for(Customer cust : customers){
			if(cust.getFname().equalsIgnoreCase(c.getFname())){
//				System.out.println(TAG + " Customer c fname: " + c.getFname());
//				System.out.println(TAG + " Customer cust fname: " + cust.getFname());
				o.setCustomer(cust);
			}
		}
		
//		o.setCustomer(c);
		//save order to database
//		System.out.println(TAG + " Order: " + o.toString());
		orderService.save(o);
		
	}



}
