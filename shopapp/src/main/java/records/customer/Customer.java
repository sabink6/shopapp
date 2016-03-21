package records.customer;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Customer {
	private String fname;
	private String lname;
	private String address;
	private String email;
	
	@Id
	@GeneratedValue
	private int id;
	
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

	@Override
	public String toString() {
		return "Customer [fname=" + fname + ", lname=" + lname + ", Address=" + address + ", Email=" + email +", id=" + id + "]";
	}


}
