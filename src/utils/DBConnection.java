package utils;
import java.sql.*;

public class DBConnection {
	
    static String url = "jdbc:mysql://localhost:3306/biblioteca_java";
    static String user = "root";
    static String pass = "";
    
    public static Connection conectar() {
    	Connection con = null;
    	try {
    		con = DriverManager.getConnection(url,user,pass);
    			System.out.println("Conexión exitosa");
    	} catch(SQLException e) {
    		e.printStackTrace();
    	}
    	
    	return con;
    }
    
}



