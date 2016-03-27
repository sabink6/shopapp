package records.customer;


import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import records.order.CustOrder;

@Entity
public class Customer {
	private String fname;
	private String lname;
	private String address;
	private String email;
	private String password;
	
	@Id
	@GeneratedValue
	private int id;
	
	@OneToMany(cascade = {CascadeType.MERGE})
	private List<CustOrder> custOrders;
	
	public void setId(int id) {
		this.id = id;
	}
	
	public int getId() {
		return id;
	}
	
	public String getFname() {
		System.out.println("customer getFname"+ fname);
		return fname;
	}

	public void setFname(String fname) {
		System.out.println("set Lname "+lname);
		this.fname = fname;
	}


	public String getLname() {
		return lname;
	}

	public void setLname(String lname) {
		System.out.println("set Lname "+lname);
		this.lname = lname;
	}

	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	
	
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	
	public List<CustOrder> getCustOrders() {
		return custOrders;
	}

	public void setCustOrders(List<CustOrder> custOrders) {
		this.custOrders = custOrders;
	}
	
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public String toString() {
		return "Customer [fname=" + fname + ", lname=" + lname + ", address=" + address + ", email=" + email + ", id="
				+ id + 
				//", custOrders=" + custOrders + 
				"]";
	}

	

}
