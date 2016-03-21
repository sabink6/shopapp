package records.cart;

import java.util.ArrayList;

import org.springframework.stereotype.Service;

@Service
public class Cart {
	
	private static ArrayList<CartItem> store = new ArrayList<CartItem>();
	
	public Cart(){
		System.out.println("construct product store");
		makeCartItemStore();
	}
	public ArrayList<CartItem> getStore() {
		return store;
	}
	public void setStore(ArrayList<CartItem> s) {
		store = s;
	}

	public void makeCartItemStore(){
		store = new ArrayList<CartItem>();
		System.out.println("make cart store");
	}

}
