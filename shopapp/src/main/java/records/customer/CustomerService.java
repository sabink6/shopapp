package records.customer;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CustomerService {

	@Autowired
	private CustomerRepository customerRepository;
	
	public List<Customer> findAll() {
		return customerRepository.findAll();
	}

	public void save(Customer cust) {
		customerRepository.save(cust);
	}

	public void remove(Customer cust) {
		customerRepository.delete(cust);
	}
	
	//where is this code
	public Customer loginCustomer(String email, String password){
		Customer customer = this.findCustomer(email);
		if(customer != null && customer.getPassword().equals(password)){
			return customer;
		}
		return null;
	}

	private Customer findCustomer(String email) {
		return customerRepository.findByEmailAddress(email);
	}

}

