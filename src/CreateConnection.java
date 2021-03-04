import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class CreateConnection {
	
	static Connection connect() throws SQLException, ClassNotFoundException {
		  
			 
		//step1 load the driver class  
					Class.forName("oracle.jdbc.driver.OracleDriver");  
		  
		//step2 create  the connection object  
					Connection con=DriverManager.getConnection(  
					"jdbc:oracle:thin:@localhost:1521:orcl","scott","tiger");  
		  
		
					
					
					//step3 create the statement object  
				
			//return con;  
			return con;
		  
			}  
	
		 

}
