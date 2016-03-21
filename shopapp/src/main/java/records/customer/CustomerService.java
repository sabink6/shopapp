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

}

