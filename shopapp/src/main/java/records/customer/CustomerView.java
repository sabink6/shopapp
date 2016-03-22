package records.customer;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

import java.util.List;

import javax.annotation.PostConstruct;

@ManagedBean
@RequestScoped
public class CustomerView {
   
	@ManagedProperty("#{customerService}")
    private CustomerService store;
	private List<Customer> customers;
	private Customer customer;
	
    @PostConstruct
    public void makeCustomer(){
    	customer = new Customer();
    	System.out.println("postconstruct customer" + customer);
    	customers = store.findAll();
    }
    public void remove(Customer c) {
		store.remove(c);
		customers = store.findAll();
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Customer removed!", null));
	}
    
    public Customer getCustomer() {
    	System.out.println("in getCust "+customer);
		return customer;
	}
  
	public void setCustomer(Customer customer) {
		System.out.println("set customer" +customer);
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

	public String add(){
		System.out.println(" about to save customer: "+customer.toString());
		store.save(customer);
		System.out.println(" saved customer: "+customer.toString());
		customer = new Customer();
    	System.out.println("add to store");
    	return "./Customer.xhtml";
    }
  	
	 public void adding() {
			System.out.println("adding" +customer);
		}
	
}