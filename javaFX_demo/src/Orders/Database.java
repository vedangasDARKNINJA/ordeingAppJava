package Orders;
import java.sql.*;
public class Database {
	Connection myConn; 
	Statement myStatement;
	public ResultSet myResult;
	
	public Database(String database_name){
		// TODO Auto-generated constructor stub
		try {
			if(database_name != null)
			{
				database_name = database_name.trim();
				myConn = DriverManager.getConnection("jdbc:mysql://localhost:8889/"+database_name,"root","root");
				myStatement = myConn.createStatement();
			}
		}catch (SQLException e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
	}
	
	public void getQuery(String query)
	{
		try {
			myResult= myStatement.executeQuery(query);
			myResult.first();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("SELECT FAILED!");
		}
	}
	
	public void setQuery(String query)
	{
		try {
			myStatement.executeUpdate(query);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("INSERT FAILED!");
		}
	}
	
	public int getRows()
	{
		int rows=0;
		try {
			if(myResult.first()==true)
			{
				rows++;
				while(myResult.next())
				{
					rows++;
				}
			}
			return rows;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return -1;
		}
	}
	
	public void displayRes(String...columns)
	{
		try {
			if(myResult.first())
			{
				myResult.beforeFirst();
				while(myResult.next())
				{
					for(String s: columns)
					{
						System.out.print(myResult.getString(s)+"\t");
					}
					System.out.println();
				}
			}
		}catch (SQLException e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
}