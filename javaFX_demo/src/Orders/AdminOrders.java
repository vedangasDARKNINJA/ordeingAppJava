package Orders;

import java.sql.SQLException;
import java.util.ArrayList;

import Orders.view.MainViewController;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.scene.control.*;

public class AdminOrders {
	private SimpleIntegerProperty id;
	private SimpleStringProperty name;
	private SimpleIntegerProperty qty;
	private SimpleIntegerProperty cartPrice;
	private SimpleStringProperty username;
	private SimpleStringProperty status;
	private Button b_CNF;
	private Button b_DEL;
	private Button b_DLV;
	
	
	public AdminOrders(int id, String name, Integer qty, Integer cartPrice, String username, String status) {
		this.id = new SimpleIntegerProperty(id);
		this.name = new SimpleStringProperty(name);
		this.qty = new SimpleIntegerProperty(qty);
		this.cartPrice = new SimpleIntegerProperty(cartPrice);
		this.username = new SimpleStringProperty(username);
		this.status = new SimpleStringProperty(status);
		this.b_CNF = new Button("Confirm");
		this.b_DEL = new Button("Delete");
		this.b_DLV = new Button("Delivered");
	}


	public int getId() {
		return id.get();
	}


	public void setId(int id) {
		this.id = new SimpleIntegerProperty(id);
	}


	public String getName() {
		return name.get();
	}


	public void setName(String name) {
		this.name = new SimpleStringProperty(name);
	}


	public Integer getQty() {
		return qty.get();
	}


	public void setQty(Integer qty) {
		this.qty = new SimpleIntegerProperty(qty);
	}


	public Integer getCartPrice() {
		return cartPrice.get();
	}


	public void setCartPrice(Integer cartPrice) {
		this.cartPrice = new SimpleIntegerProperty(cartPrice);
	}


	public String getUsername() {
		return username.get();
	}


	public void setUsername(String username) {
		this.username = new SimpleStringProperty(username);
	}


	public String getStatus() {
		return status.get();
	}


	public void setStatus(String status) {
		this.status = new SimpleStringProperty(status);
	}

	
	public Button getB_CNF() {
		return b_CNF;
	}


	public void setB_CNF(Button b_CNF) {
		this.b_CNF = b_CNF;
	}


	public Button getB_DEL() {
		return b_DEL;
	}


	public void setB_DEL(Button b_DEL) {
		this.b_DEL = b_DEL;
	}


	public Button getB_DLV() {
		return b_DLV;
	}


	public void setB_DLV(Button b_DLV) {
		this.b_DLV = b_DLV;
	}
	
	
	public static ArrayList<AdminOrders> getAdminOrdersData(Database d,String query)
	{
		ArrayList<AdminOrders> data = new ArrayList<AdminOrders>();
		MainViewController.db.getQuery(query);
		try {
			MainViewController.db.myResult.beforeFirst();
			while(MainViewController.db.myResult.next())
			{
				int i = Integer.parseInt(MainViewController.db.myResult.getString("id"));
				int cP = Integer.parseInt(MainViewController.db.myResult.getString("price"));
				int qty = Integer.parseInt(MainViewController.db.myResult.getString("qty"));
				String n = MainViewController.db.myResult.getString("name");
				String uN = MainViewController.db.myResult.getString("username");
				String oS = MainViewController.db.myResult.getString("order_status");
				data.add(new AdminOrders(i, n, qty, cP, uN, oS));
			}
		} catch (SQLException e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
		
		return data;
	}
	
}
