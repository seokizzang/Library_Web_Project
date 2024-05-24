package utils;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DBHelper {
	
	 String strDriver = "oracle.jdbc.driver.OracleDriver";
	    //String strURL = "jdbc:oracle:thin:@10.70.41.81:1521:xe";
	    String strURL = "jdbc:oracle:thin:@localhost:1521:xe";
	    String strUser ="root";
	    String strPWD = "rootpw";
	   
	    public Connection DB_con;
	    public Statement DB_stmt;
	    public ResultSet DB_rs;
	   
	    public void dbOpen() throws IOException {
	        try {
	            Class.forName(strDriver);
	            DB_con = DriverManager.getConnection(strURL,strUser,strPWD);
	            DB_stmt = DB_con.createStatement();
	        }catch (Exception e) {
	            System.out.println("SQLException:" + e.getMessage());
	        }
	    }
	    public void dbClose() throws IOException {
	        try {
	            DB_stmt.close();
	            DB_con.close();
	        }catch(SQLException e) {
	            System.out.println("SQLException:" + e.getMessage());
	        }
	    }
	
}