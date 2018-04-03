package Orders.user;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import Orders.Main;
import Orders.view.MainViewController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.*;

public class YourOrdersController implements Initializable{
	@FXML private Button ok_btn;
	@FXML private Label totalSpent;
	@FXML private ListView<String> ordersList;
	private ObservableList<String> items = FXCollections.observableArrayList();
	
	@FXML
	private void closeWindow()
	{	
		Stage stage = (Stage) ok_btn.getScene().getWindow();
	    stage.close();
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		ordersList.setItems(items);
		getRecords();
	}
	
	
	@FXML
	private void getRecords()
	{
		items.clear();
		MainViewController.db.getQuery("SELECT products.name,orders.qty,orders.price,orders.order_status FROM orders JOIN products ON orders.product_id = products.id JOIN users ON orders.user_id = users.id WHERE users.id = "+MainViewController.me.getUid());
		try {
			MainViewController.db.myResult.beforeFirst();
			while(MainViewController.db.myResult.next())
			{
				items.add("Product: "+MainViewController.db.myResult.getString("name")+" \nQty: " + MainViewController.db.myResult.getString("qty")+"\nPrice: "+MainViewController.db.myResult.getString("price")+"\nSTATUS: "+MainViewController.db.myResult.getString("order_status"));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		MainViewController.db.getQuery("SELECT SUM(`price`) FROM `orders` WHERE `user_id` = '"+MainViewController.me.getUid()+"' AND `order_status` != 'deleted'");
		try {
			MainViewController.db.myResult.first();
			totalSpent.setText(MainViewController.db.myResult.getString("SUM(`price`)"));
		}catch(SQLException e)
		{
			e.printStackTrace();
		}
	}
	
}