import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ContactService {
	ArrayList<Contact> contactList= new ArrayList<Contact>();
	public static void main(String[] args) throws ClassNotFoundException, SQLException {
		// TODO Auto-generated method stub
		Connection con = CreateConnection.connect();
		Statement smt = con.createStatement();
		Scanner sc = new Scanner(System.in);
		ContactService cs=new ContactService();
		
		while(true) {
			System.out.println("Enter 1 to Add Contacts\nEnter 2 to populate from db\nEnter 3 to display contacts\n"
					+ "Enter 4 to remove Contact from DB\nEnter 5 to search by name\nEnter 6 to search by Number"
					+ "\nEnter 7 to add Contact Number\nEnter 8 to sort Contacts by name\n"
					+ "Enter 9 to serialize\nEnter 10 to deserialize");
			
			int val =sc.nextInt();
			switch(val) {
			
			case 1:
				cs.getContactDetails();
				continue;
			case 2:
				cs.populateContactFromDb();
				
				continue;
			case 3:
				cs.displayContact();
				continue;
			case 4:
				cs.removeContact();
				continue;
			case 5:
				cs.SearchContactByName();
				continue;
			case 6:
				cs.searchByNumber();
				continue;
			case 7:
				cs.addContactNumber();
				continue;
			case 8:
				cs.sortByName();
				
				
				
				
				
			}
			
		}
		
		
	}
	private void sortByName() {
		contactList.sort(new sortContactByName());
		System.out.println("Contact List has been sorted by Contact Name");
	}
	private void addContactNumber() {
		System.out.println("Enter the Contact id of contact");
		Scanner sc = new Scanner(System.in);
		int id=sc.nextInt();
		
		boolean b=true;
		for(Contact c: contactList) {
			if(c.getId()==id) {
				System.out.println("Enter Contact Number to add");
				String num=sc.next();
				b=false;
				ArrayList<String> contact=(ArrayList<String>) c.getContact();
				contact.add(num);
				c.setNumber(contact);
				System.out.println("Contact List is updated");
				System.out.println();
			}
		}
		if(b){
			System.out.println("This id is not available");
			System.out.println();
		}
		
		
	}
	void searchByNumber() throws ClassNotFoundException, SQLException {
		System.out.println("Enter the Number of contact");
		Scanner sc = new Scanner(System.in);
		String num=sc.next();
		Connection con = CreateConnection.connect();
		Statement smt = con.createStatement();
		String query="select contactlist,contactname from contact_tbl";
		ResultSet rs=smt.executeQuery(query);
		boolean b=false;
		String s1="";
		String s2="";
		while(rs.next()) {
			s1=rs.getString(1);
			s2=rs.getString(2);
			if(s1!=null && s1.contains(num)) {
				System.out.println("Number is matched");
				System.out.println("The contact name is: "+s2+" Contact number is"+s1);
				b=true;
				
			}
		}
		
		if(b==false) {
			try {
				throw new ContactNotFoundException("Contact is not found");
			} catch (ContactNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}
	void SearchContactByName() throws ClassNotFoundException, SQLException {
		System.out.println("Enter the name of contact");
		Scanner sc = new Scanner(System.in);
		String name=sc.next();
		Connection con = CreateConnection.connect();
		Statement smt = con.createStatement();
		String query="select contactname from contact_tbl";
		ResultSet rs=smt.executeQuery(query);
		boolean b=false;
		while(rs.next()) {
			if(rs.getString(1).equalsIgnoreCase(name)) {
				
				b=true;
			}
		}
		if(b)
			System.out.println("The contact is available");
		else
			try {
				throw new ContactNotFoundException("Contact is not found");
			} catch (ContactNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
	}
	void removeContact() throws ClassNotFoundException, SQLException {
		System.out.println("Enter the ID of contact to remove");
		Scanner sc = new Scanner(System.in);
		int id=sc.nextInt();
		Connection con = CreateConnection.connect();
		Statement smt = con.createStatement();
		String query="select contactid from contact_tbl";
		ResultSet rs=smt.executeQuery(query);
		if(rs.next()) {
			String query1="delete from contact_tbl where contactid="+id;
			smt.execute(query1);
		} else
			try {
				throw new ContactNotFoundException("Contact is not found");
			} catch (ContactNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}
	void displayContact() {
		for(Contact c:contactList) {
			System.out.println("ID : "+c.getId()+" NAME : "+c.getName()+" EMAIL : "+c.getEmail()+" NUMBER : "+c.getContact());
		}
	}
	void populateContactFromDb() throws ClassNotFoundException, SQLException{
		ArrayList<Contact> al= new ArrayList<Contact>();
		Connection con = CreateConnection.connect();
		Statement smt = con.createStatement();
		ResultSet rs=smt.executeQuery("Select * from contact_tbl");
		while(rs.next()) {
			Contact c= new Contact();
			c.setID(rs.getInt(1));
			c.setContactName(rs.getString(2));
			c.setEmail(rs.getString(3));
			String num=rs.getString(4);
			ArrayList<String> contactNum=new ArrayList<String>();
			if(num!=null) {
				String [] num1=num.split(",");
	
				
				for(String n:num1) {
					contactNum.add(n);
					
				}
			}
			c.setNumber(contactNum);
				
		
			al.add(c);
		}
		contactList.addAll(al);
	}
	int getMaxContactId() throws ClassNotFoundException, SQLException {
		Connection con = CreateConnection.connect();
		//step3 create the statement object  
		Statement stmt=con.createStatement();
		String query="select max(contactid) from contact_tbl";
		ResultSet rs=stmt.executeQuery(query); 
		int id=0;
		while(rs.next())
			id=rs.getInt(1);
		
		con.close();
		return id;
			
			
	}
	void getContactDetails() throws ClassNotFoundException, SQLException {
		Connection con = CreateConnection.connect();
		ArrayList<Contact> al=new ArrayList<Contact>();
		Contact c=new Contact();
		int id=getMaxContactId();
		id++;
		System.out.println("Enter Contact Name");
		Scanner sc = new Scanner(System.in);
		String name=sc.next();
		System.out.println("Enter email");
		String email=sc.next();
		System.out.println("Enter Contact Number");
		String num=sc.next();
		
		Statement stmt=con.createStatement();
		String query="insert into contact_tbl values("+id+",'"+name+"','"+email+"','"+num+"')";
		stmt.executeUpdate(query);
		c.setID(id);
		c.setContactName(name);
		c.setEmail(email);
		ArrayList<String> contactNum=new ArrayList<String>();
		if(num!=null) {
			String [] num1=num.split(",");

			
			for(String n:num1) {
				contactNum.add(n);
				
			}
		}
		c.setNumber(contactNum);
		
		al.add(c);
		
		
		
		
		addContact(c,al);
		
	}
	void addContact(Contact contact,List<Contact> contacts) {
		contactList.addAll(contacts);
		
		
	}

}
