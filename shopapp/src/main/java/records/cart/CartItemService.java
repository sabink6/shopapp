package records.cart;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CartItemService {
	
	@Autowired
	private CartItemRepository cartItemRepository;
	
	public List<CartItem> findAll() {
		return cartItemRepository.findAll();
	}

	public void save(CartItem c) {
		cartItemRepository.save(c);
	}

	public void remove(CartItem cust) {
		cartItemRepository.delete(cust);
	}
	
	public void saveAll(List<CartItem> custList) {
		cartItemRepository.save(custList);
	}
	
	
}
