package records.product;

import java.util.ArrayList;

public class ProductStore {

	private static ArrayList<Product> store = new ArrayList<Product>();

	public ProductStore(){
		System.out.println("construct product store");
		makeProductStore();
	}
	public ArrayList<Product> getStore() {
		return store;
	}
	public void setStore(ArrayList<Product> s) {
		store = s;
	}

	public void makeProductStore(){
		store = new ArrayList<Product>();
		System.out.println("make product store");
	}
}
