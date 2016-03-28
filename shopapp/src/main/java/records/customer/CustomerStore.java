package records.customer;

import java.util.ArrayList;
import org.springframework.stereotype.Service;

@Service
public class CustomerStore {
	private static ArrayList<Customer> store = new ArrayList<Customer>();

	public CustomerStore(){
		System.out.println("construct customer store");
		makeCustomerStore();
	}

	public ArrayList<Customer> getStore() {
		return store;
	}

	public void setStore(ArrayList<Customer> s) {
		store = s;
	}

	public void makeCustomerStore(){
		store = new ArrayList<Customer>();
		System.out.println("make customer store");
	}
}
