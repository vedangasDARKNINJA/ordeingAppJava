package Orders.admin;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.*;

import java.net.URL;
import java.util.ResourceBundle;

import Orders.AdminOrders;
import Orders.Main;
import Orders.Users;
import Orders.view.MainViewController;

public class AdminController implements Initializable{
	@FXML private Button logout;
	//USER TABLE
	@FXML private TableView<Users> userTable;
	@FXML private TableColumn<Users, Integer> uid;
	@FXML private TableColumn<Users, String> uName;
	@FXML private TableColumn<Users, Integer> uCreditPoints;
	@FXML private TableColumn<Users, Integer> uTotalSpent;
	@FXML private TabPane adminTabPane;
	
	
	//ORDERS TABLE
	@FXML private TableView<AdminOrders> ordersTable;
	@FXML private TableColumn<AdminOrders, Integer> oID;
	@FXML private TableColumn<AdminOrders, String> oName;
	@FXML private TableColumn<AdminOrders, Integer> oQty;
	@FXML private TableColumn<AdminOrders, Integer> oCartPrice;
	@FXML private TableColumn<AdminOrders, String> oUser;
	@FXML private TableColumn<AdminOrders, Button> oCNF;
	@FXML private TableColumn<AdminOrders, Button> oDEL;
	@FXML private TableColumn<AdminOrders, Button> oDLV;
	
	
	
	
	@FXML
	public void logout() {
		Main.showMainView();
	}
	
	public ObservableList<Users> userList = FXCollections.observableArrayList(Users.getUserData(MainViewController.db,"SELECT * FROM `users` WHERE `username` != 'admin'")); 
	public ObservableList<AdminOrders> orderList;	
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// USER TABLE
		uid.setStyle( "-fx-alignment: CENTER-RIGHT;");
		uName.setStyle( "-fx-alignment: CENTER;");
		uCreditPoints.setStyle( "-fx-alignment: CENTER;");
		uTotalSpent.setStyle( "-fx-alignment: CENTER;");
		
		uid.setCellValueFactory(new PropertyValueFactory<Users,Integer>("uid"));
		uName.setCellValueFactory(new PropertyValueFactory<Users,String>("name"));
		uCreditPoints.setCellValueFactory(new PropertyValueFactory<Users,Integer>("credit"));
		uTotalSpent.setCellValueFactory(new PropertyValueFactory<Users,Integer>("totalSpent"));
		userTable.setItems(userList);
		
		
		//ORDER TABLE
		oID.setStyle( "-fx-alignment: CENTER-RIGHT;");
		oQty.setStyle( "-fx-alignment: CENTER-RIGHT;");
		oName.setStyle( "-fx-alignment: CENTER;");
		oCartPrice.setStyle( "-fx-alignment: CENTER;");
		oUser.setStyle( "-fx-alignment: CENTER;");
		oCNF.setStyle( "-fx-alignment: CENTER;");
		oDEL.setStyle( "-fx-alignment: CENTER;");
		oDLV.setStyle( "-fx-alignment: CENTER;");
		
		
		oID.setCellValueFactory(new PropertyValueFactory<AdminOrders,Integer>("id"));
		oName.setCellValueFactory(new PropertyValueFactory<AdminOrders,String>("name"));
		oQty.setCellValueFactory(new PropertyValueFactory<AdminOrders,Integer>("qty"));
		oCartPrice.setCellValueFactory(new PropertyValueFactory<AdminOrders,Integer>("cartPrice"));
		oUser.setCellValueFactory(new PropertyValueFactory<AdminOrders,String>("username"));
		oCNF.setCellValueFactory(new PropertyValueFactory<AdminOrders,Button>("b_CNF"));
		oDEL.setCellValueFactory(new PropertyValueFactory<AdminOrders,Button>("b_DEL"));
		oDLV.setCellValueFactory(new PropertyValueFactory<AdminOrders,Button>("b_DLV"));
		updateTable();
		ordersTable.setItems(orderList);
		
	}
	@FXML 
	private void updateUsers()
	{
		userList = FXCollections.observableArrayList(Users.getUserData(MainViewController.db,"SELECT * FROM `users` WHERE `username` != 'admin'"));
		userTable.refresh();
	}
	
	
	
	@FXML 
	private void updateTable()
	{
		orderList = FXCollections.observableArrayList(AdminOrders.getAdminOrdersData(MainViewController.db,"SELECT orders.id,products.name,orders.qty,orders.price,users.username,orders.order_status FROM `orders` JOIN `products` ON orders.product_id = products.id JOIN `users` ON orders.user_id = users.id WHERE order_status != 'delivered' AND order_status != 'deleted' ORDER BY orders.id"));			
		for(AdminOrders ao: orderList)
		{
			if(ao.getStatus().compareTo("confirmed") == 0)
			{
				ao.getB_CNF().setDisable(true);
				ao.getB_DEL().setDisable(true);
				ao.getB_DLV().setDisable(false);
			}
			else
			{
				ao.getB_CNF().setDisable(false);
				ao.getB_DEL().setDisable(false);
				ao.getB_DLV().setDisable(true);
			}
			
			ao.getB_DLV().setOnAction(e->{
				orderList.remove(ao);
				MainViewController.db.setQuery("UPDATE `orders` SET `order_status`='delivered' WHERE `id` = "+ao.getId());
			});
			
			ao.getB_DEL().setOnAction(e->{
				orderList.remove(ao);
				MainViewController.db.setQuery("UPDATE `orders` SET `order_status`='deleted' WHERE `id` = "+ao.getId());
				MainViewController.db.setQuery("UPDATE `users` SET creditPoints = creditPoints + "+ao.getCartPrice()+",totalSpent = totalSpent -"+ao.getCartPrice()+" WHERE username = '"+ao.getUsername()+"'");
				System.out.println("UPDATE `users` SET creditPoints = creditPoints + "+ao.getCartPrice()+",totalSpent = totalSpent -"+ao.getCartPrice()+" WHERE username = '"+ao.getUsername()+"'");
			});
			
			ao.getB_CNF().setOnAction(e->{
				ao.getB_CNF().setDisable(true);
				ao.getB_DEL().setDisable(true);
				ao.getB_DLV().setDisable(false);
				MainViewController.db.setQuery("UPDATE `orders` SET `order_status`='confirmed' WHERE `id` = "+ao.getId());
			});
			ordersTable.setItems(orderList);
			ordersTable.refresh();
			userList = FXCollections.observableArrayList(Users.getUserData(MainViewController.db,"SELECT * FROM `users` WHERE `username` != 'admin'"));
			userTable.setItems(userList);
			userTable.refresh();
		}
		
	}

}
