package Orders.user;

import java.net.URL;
import java.util.ResourceBundle;

import Orders.Main;
import Orders.Products;
import Orders.view.MainViewController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.*;

public class PlaceOrderController implements Initializable{
	@FXML private Button pay_btn;
	@FXML private Button close_btn;
	@FXML private ListView<String> currentOrder;
	
	private ObservableList<String> items = FXCollections.observableArrayList();
	
	@FXML
	private void closeWindow()
	{
		//if payment successful!
		Stage stage = (Stage) close_btn.getScene().getWindow();
		stage.close();	    
	}
	
	@FXML
	private void payWindow()
	{
		int i=0;
		for(Products p: UserController.addToCartList)
		{
			MainViewController.db.getQuery("SELECT * FROM `orders` WHERE `product_id` = '"+p.getId().toString()+"' AND `qty` = '"+p.getQuantity().getValueFactory().getValue().toString()+"' AND `price` = '"+p.getCart_price().toString()+"' AND `user_id` = '"+MainViewController.me.getUid()+"' AND `order_status` != 'delivered' AND `order_status` != 'deleted'");
			if(MainViewController.db.getRows()>0)
			{
				i++;
			}
		}
			
		if(i != UserController.addToCartList.size())
		{
			for(Products p: UserController.addToCartList)
			{
				MainViewController.db.setQuery("INSERT INTO `orders`(`product_id`,`qty`,`price`,`user_id`,`order_status`)VALUES('"+p.getId().toString()+"','"+p.getQuantity().getValueFactory().getValue().toString()+"','"+p.getCart_price().toString()+"','"+MainViewController.me.getUid()+"','not confirmed')");
			}
			
			items.add("PAYMENT SUCCESSFUL!");
			//System.out.println("UPDATE `users` SET `creditPoints` = '"+(MainViewController.me.getCredit()-CartController.totalAmt)+"' WHERE `username` = '"+MainViewController.me.getName()+"'");
			MainViewController.db.setQuery("UPDATE `users` SET creditPoints = creditPoints - "+CartController.totalAmt+", totalSpent = totalSpent + "+CartController.totalAmt+ " WHERE `username` = '"+MainViewController.me.getName()+"'");
			
			items.add("TRACK YOUR ORDER IN YOUR ORDERS");
			pay_btn.setDisable(true);
			close_btn.setVisible(true);
		}
		else
		{
			items.add("YOU HAVE ALREADY ORDERED EXACTLY THE SAME PRODUCTS! TO CHECK STATUS GO TO YOUR ORDERS.");
			try {
				Main.showYourOrders();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}		
	}


	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		close_btn.setVisible(false);
		currentOrder.setItems(items);
		items.add("ACCOUNT DETAILS: " + MainViewController.me.getName());
		
		for(Products p: UserController.addToCartList)
		{
			items.add("Name: "+p.getProduct()+" Qty: "+p.getQuantity().getValueFactory().getValue().toString()+" Price: "+p.getCart_price().toString());
		}
		
		items.add("TOTAL: "+CartController.totalAmt);
	
		if(MainViewController.me.getCredit()< CartController.totalAmt)
		{
			pay_btn.setDisable(true);
			items.add("CREDIT LIMIT EXCEEDED! CAN'T PLACE THE ORDER");
		}
		else
		{
			pay_btn.setDisable(false);
			items.add("ORDER READY TO GO! PAY TO PROCEED");
		}
	
	}
	
	
	
	
}
