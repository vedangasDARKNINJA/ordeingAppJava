package Orders;

import java.sql.SQLException;
import java.util.ArrayList;

import Orders.view.MainViewController;
import javafx.beans.property.*;
import javafx.scene.control.*;

public class Users {
	private SimpleIntegerProperty uid;
	private SimpleStringProperty name;
	private SimpleIntegerProperty credit;
	private SimpleIntegerProperty totalSpent;

	public Users(int uid, String name, int credit,int totalSpent) {
		this.uid = new SimpleIntegerProperty(uid);
		this.name = new SimpleStringProperty(name);
		this.credit = new SimpleIntegerProperty(credit);
		this.totalSpent = new SimpleIntegerProperty(totalSpent); 
	}
	
	public Users() {
		this.uid = new SimpleIntegerProperty(0);
		this.name = new SimpleStringProperty("");
		this.credit = new SimpleIntegerProperty(0);
		this.totalSpent = new SimpleIntegerProperty(0);
	}

	public int getTotalSpent() {
		return totalSpent.get();
	}

	public void setTotalSpent(int totalSpent) {
		this.totalSpent = new SimpleIntegerProperty(totalSpent);
	}
	
	public int getUid() {
		return uid.get();
	}

	public void setUid(int uid) {
		this.uid = new SimpleIntegerProperty(uid);
	}

	public String getName() {
		return name.get();
	}

	public void setName(String name) {
		this.name = new SimpleStringProperty(name);
	}

	public int getCredit() {
		return credit.get();
	}

	public void setCredit(int credit) {
		this.credit = new SimpleIntegerProperty(credit);
	}
		
	public void setUser(int uid, String name, int credit, int totalSpent) {
		setUid(uid);
		setName(name);
		setCredit(credit);
		setTotalSpent(totalSpent);
	}
	
	public static ArrayList<Users> getUserData(Database d,String query)
	{
		ArrayList<Users> data = new ArrayList<Users>();
		d.getQuery(query);
		try {
			d.myResult.beforeFirst();
			while(d.myResult.next())
			{
				data.add(new Users(Integer.parseInt(d.myResult.getString("id")),d.myResult.getString("username"),Integer.parseInt( d.myResult.getString("creditPoints")),Integer.parseInt( d.myResult.getString("totalSpent"))));
			}
			return data;
		}
		catch (SQLException e) {
			// TODO: handle exception
			e.printStackTrace();
			return null;
		}
		
	}
	
}
