package records.order;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import records.cart.CartItem;

@Service
public class OrderService {

	@Autowired
	private OrderRepository orderRepository;

	public List<CustOrder> findAll(){
		return orderRepository.findAll();
	}

	public void save(CustOrder order){
		orderRepository.save(order);
	}

	public void remove(CustOrder order){
		orderRepository.delete(order);
	}

	public CustOrder findOrderById(int id) {
		return orderRepository.findOne(id);
	}
	
//	public List<CartItem> findAllCartItemsByOrderId(int id){
//		return orderRepository.findAllCartItems(id);
//		
//		
//	}
}
