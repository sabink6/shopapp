package records.order;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import records.cart.CartItem;
import records.customer.Customer;

public interface OrderRepository extends JpaRepository<CustOrder, Integer>{

}
