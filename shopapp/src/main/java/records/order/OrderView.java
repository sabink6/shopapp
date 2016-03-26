package records.order;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

@ManagedBean
@RequestScoped
public class OrderView {
	
	static final String TAG = OrderView.class.getSimpleName().toUpperCase();
	
	@ManagedProperty("#{orderService}")
	private OrderService orderService;
	
	private CustOrder order;

	public OrderService getOrderService() {
		return orderService;
	}

	public void setOrderService(OrderService orderService) {
		this.orderService = orderService;
	}

	public CustOrder getOrder() {
		return order;
	}

	public void setOrder(CustOrder order) {
		this.order = order;
	}

	private String orderId;
	
	
	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public void onload(String id) {
		FacesContext facesContext = FacesContext.getCurrentInstance();
	    if (!facesContext.isPostback() && !facesContext.isValidationFailed()) {
	    	System.out.println(TAG + " OrderId on load: " + id);
	    	CustOrder order = orderService.findOrderById(Integer.valueOf(id));
	    	System.out.println(TAG + " Order on load: " + order.toString());
	    }
	}


}
