package records.customer;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import records.order.CustOrder;

public interface CustomerRepository extends JpaRepository<Customer, Integer>{

	@Query("select c from Customer c where c.email = ?1")
	Customer findByEmailAddress(String email);

}