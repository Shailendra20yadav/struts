package test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class DBConnectionTest {
	
	public static void main (String... a) throws Exception{
		
		Connection con = getDb2Connection();
		
		try{
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery("select count(*) from orders");
			while(rs.next()){
				System.out.println("Count = "+rs.getLong(1));
			}
		}finally{
			if(con !=null)
				con.close();
		}
		
	}
	
	public static Connection getDb2Connection(){
		String jdbcClassName="com.ibm.db2.jcc.DB2Driver";
        //String url="jdbc:db2://9.149.23.108:50000/DBTEST";
        String url="jdbc:db2://9.149.23.108:50000/WCDEV";
        String user="db2inst1";
        String password="db2inst1";
 
        Connection connection = null;
        try {
            //Load class into memory
            Class.forName(jdbcClassName);
            //Establish connection
            connection = DriverManager.getConnection(url, user, password);
 
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }finally{
            if(connection!=null){
                System.out.println("Connected successfully.");
                /*try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }*/
            }
        }
        return connection;
	}

}
