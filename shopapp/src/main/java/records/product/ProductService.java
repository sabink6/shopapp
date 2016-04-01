package records.product;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import records.customer.Customer;

@Service
public class ProductService {
	
	@Autowired
	private ProductRepository productRepository;
	
	public List<Product> findAll() {
		return productRepository.findAll();
	}

	public void save(Product p) {
		productRepository.save(p);
	}

	public void remove(Product p) {
		productRepository.delete(p);
	}
	
	public Product findProductById(int id) {
		return productRepository.findOne(id);
	}
}
