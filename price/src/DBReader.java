import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class DBReader {
	static String inputFilePath=null;
	private static String uniquePartNumbersCommaSeparatedList=null;
	private static  String partNubmersInDbList = null;
	private static String outputFilePath = null;
	private static  String jdbcClassName = null;
	private static String dbUrl = null;
	private static  String dbUser = null;
	private static String dbPassword = null;

	static {
		try{
			Properties prop = new Properties();
			//prop.load(new FileInputStream("configuration.properties"));
			prop.load(ClassLoader.getSystemResourceAsStream("configuration.properties"));
			inputFilePath = prop.getProperty("inputFilePath");
			uniquePartNumbersCommaSeparatedList = prop.getProperty("uniquePartNumbersCommaSeparatedList");
			partNubmersInDbList = prop.getProperty("partNubmersInDbList");
			outputFilePath = prop.getProperty("outputFilePath");
			dbUser = prop.getProperty("dbUser");
			jdbcClassName = prop.getProperty("jdbcClassName");
			dbUrl = prop.getProperty("dbUrl");
			dbPassword = prop.getProperty("dbPassword");
			
			//System.out.println(inputFilePath);
		}catch(Exception e){
			e.printStackTrace();
			throw new ExceptionInInitializerError(e.getMessage());
		}
	} 
	
	public static Connection getDb2Connection(){
		 
        Connection connection = null;
        try {
            //Load class into memory
            Class.forName(jdbcClassName);
            //Establish connection
            connection = DriverManager.getConnection(dbUrl, dbUser, dbPassword);
 
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
	public static void readPartnumbersFromDB() throws Exception{
		try{
			BufferedReader br =new BufferedReader(new FileReader(outputFilePath+uniquePartNumbersCommaSeparatedList));
			StringBuffer partNumbersinFiles=new StringBuffer();
			String line=null;
			while ((line = br.readLine()) !=null) {
				if(line.length()==0)
					continue;
				partNumbersinFiles.append(line);
			}
			String sql = "select partnumber from WCDBUSER.catentry where partnumber in ("+partNumbersinFiles.toString()+") with ur";
			System.out.println(sql);
			br.close();
			Connection con = getDb2Connection();
			Statement st = con.createStatement();
			ResultSet rs = st.executeQuery(sql);
			BufferedWriter writer = new BufferedWriter(new FileWriter(outputFilePath+partNubmersInDbList));
			while (rs.next()){
				String value = rs.getString(1);
				writer.write(value);
				writer.newLine();
				
			}
			writer.close();
			con.close();
		}catch(Exception e){
			System.err.println(e.getMessage());
			throw e;
		}
		
	}
	public static void main (String [] args) throws Exception{
		readPartnumbersFromDB();
		

	}

}
