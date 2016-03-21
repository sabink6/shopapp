package records.product;

import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;


@ManagedBean
@ApplicationScoped
public class ProductView {
	
	@ManagedProperty("#{productService}")
	private ProductService store;
	private List<Product> products;
	private Product product;
	
	
	public ProductService getStore() {
		return store;
	}
	public void setStore(ProductService store) {
		this.store = store;
	}
	public List<Product> getProducts() {
		products = store.findAll();
		return products;
	}
	public void setProducts(List<Product> products) {
		this.products = products;
	}
	public Product getProduct() {
		return product;
	}
	public void setProduct(Product product) {
		this.product = product;
	}

	@PostConstruct
    public void makeProduct(){
    	product = new Product();
    	System.out.println("postconstruct product");
    	products = store.findAll();
    }
	
	public String add(){
		System.out.println(" about to save product: " + product.toString());
		store.save(product);
		System.out.println(" saved product: " + product.toString());
		product = new Product();
    	System.out.println("add to store");
    	return "./Product.xhtml";
	}
	
	public void remove(){
		
	}
	
	public void adding() {
		System.out.println("adding " +product);
	}
	
	

}
