import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Contact implements Serializable{
	private int contactId;
	private String contactName;
	private String email;
	private List<String> contactNumber;
	
	Contact(){
		this.contactId=0;
		this.contactName="";
		this.email="";
		this.contactNumber=new ArrayList<String>();
	}
	
	//setter methods
	void setID(int id) {
		this.contactId=id;
	}
	void setContactName(String name) {
		this.contactName=name;
	}
	void setEmail(String email) {
		this.email=email;
	}
	void setNumber(ArrayList num) {
		this.contactNumber=num;
	}
	
	int getId() {
		return contactId;
	}
	String getName() {
		return contactName;
	}
	String getEmail() {
		return email;
	}
	List<String> getContact(){
		return contactNumber;
	}
	
	@Override
	public String toString() {
		return this.contactId+" "+this.contactName+" "+this.email+" "+this.contactNumber;
	}

}
