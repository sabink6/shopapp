package records.customer;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import java.util.List;
import javax.annotation.PostConstruct;

@ManagedBean
@SessionScoped //or @ApplicationScoped //needed instead of @RequestScoped otherwise update doesn't work -> new entry is created
public class CustomerView {

	static final String TAG = CustomerView.class.getSimpleName().toUpperCase();

	@ManagedProperty("#{customerService}")
	private CustomerService store;
	private List<Customer> customers;
	private Customer customer;

	@PostConstruct
	public void makeCustomer(){
		customer = new Customer();
		System.out.println(TAG + " postconstruct customer " + customer);
		//customers = store.findAll();
	}

	public Customer getCustomer() {
		System.out.println(TAG + " in getCustomer " + customer);
		return customer;
	}

	public void setCustomer(Customer customer) {
		System.out.println(TAG + " in set customer " + customer);
		this.customer = customer;
	}

	public CustomerService getStore() {
		return store;
	}

	public void setStore(CustomerService store) {
		this.store = store;
	}

	public List<Customer> getCustomers() {
		customers = store.findAll();
		return customers;
	}

	public void setCustomers(List<Customer> customers) {
		this.customers = customers;
	}

	public void remove(Customer customer) {
		store.remove(customer);
		customers = store.findAll();
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Customer removed!", null));
	}

	public void adding() {
		System.out.println(TAG + " adding customer " + customer);
	}

	public void add(){
		System.out.println(TAG + " about to save customer: " + customer.toString());
		store.save(customer);
		System.out.println(TAG + " saved customer: " + customer.toString());
		customer = new Customer();
		System.out.println(TAG + " added to a store ");
		
	}

}