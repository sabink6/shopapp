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

	private List<CartItem> thecart;
	private CartItem cartItem;
	private List<Product> products;

	public List<Product> getProducts() {
		products = productService.findAll();
		return products;
	}

	public void setProducts(List<Product> products) {
		this.products = products;
	}

	public CartItemService getStore() {
		return store;
	}

	public void setStore(CartItemService store) {
		this.store = store;
	}

	public List<CartItem> getThecart() {
		//theCart = store.findAll();
		System.out.println(TAG + " getThecart");
		return thecart;
	}

	public ProductService getProductService() {
		return productService;
	}

	public void setProductService(ProductService productService) {
		this.productService = productService;
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
		//theCart = store.findAll();
	}

	//create a new CartItem object and add it into an arrayList of CartItems
	public String buy(Product p){
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

	public void checkout(){
		//build up order
		System.out.println(TAG + " git test saved cartItem: " + cartItem.toString());
		System.out.println(TAG + " saved cartItem: " + cartItem.toString());
	}

}
