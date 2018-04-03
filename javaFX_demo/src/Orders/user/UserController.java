package Orders.user;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import Orders.Main;
import Orders.Products;
import Orders.view.MainViewController;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;

public class UserController implements Initializable{
	@FXML private TableView<Products> productsTable;
	@FXML private TableColumn<Products,Integer>id;
	@FXML private TableColumn<Products,String>product;
	@FXML private TableColumn<Products,Integer>price;
	@FXML private TableColumn<Products,CheckBox>addToCart;
	@FXML private Text credit;
	@FXML private TabPane tabPane;
	
	
	@FXML private PasswordField pass_curr;
	@FXML private PasswordField pass_new;
	@FXML private PasswordField pass_new_c;
	@FXML private Label errMsg;
	
	@FXML private Label cartMsg;
		
	String query= "SELECT * FROM `products`";
	ObservableList<Products> list = FXCollections.observableArrayList(Products.getProductData(MainViewController.db,query));
	public static ObservableList<Products> addToCartList = FXCollections.observableArrayList();
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		cartMsg.setText("");
		
		id.setStyle( "-fx-alignment: CENTER-RIGHT;");
		product.setStyle( "-fx-alignment: CENTER;");
		price.setStyle( "-fx-alignment: CENTER;");
		addToCart.setStyle( "-fx-alignment: CENTER;");
		
		
		id.setCellValueFactory(new PropertyValueFactory<Products,Integer>("id"));
		product.setCellValueFactory(new PropertyValueFactory<Products,String>("product"));
		price.setCellValueFactory(new PropertyValueFactory<Products,Integer>("price"));
		addToCart.setCellValueFactory(new PropertyValueFactory<Products,CheckBox>("cb"));
		productsTable.setItems(list);
			
		checkCredit();	
		
		for(Products p:list)
		{
			p.getCb().selectedProperty().addListener(new ChangeListener<Boolean>() {

				@Override
				public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
					// TODO Auto-generated method stub
					if(newValue == true)
					{
						if(checkCart() != 0)
						{
							cartMsg.setText(checkCart()+" items in the cart");
						}
						else
						{
							cartMsg.setText("Cart empty!");
						}
					}
				}
				
			});
		}
		
	}
	
	public static int checkCart()
	{
		int items=0;
		for(Products p:addToCartList)
		{
			if(p.getCb().isSelected())
			{
				items++;
			}
		}
		return items;
	}
	
	public int checkList()
	{
		int items=0;
		for(Products p:list)
		{
			if(p.getCb().isSelected())
			{
				items++;
			}
		}
		return items;
	}
	
	
	
	@FXML
	public void updatePass() throws SQLException
	{
		MainViewController.db.getQuery("SELECT * FROM `users` WHERE `username` = '"+MainViewController.me.getName()+"'");
		if(MainViewController.db.myResult.getString("password").compareTo(pass_curr.getText())==0)
		{
			if(pass_curr.getText().compareTo(pass_new.getText()) != 0)
			{
				if(pass_new.getText().compareTo(pass_new_c.getText())==0)
				{
					MainViewController.db.setQuery("UPDATE `users` SET `password` = '"+pass_new.getText()+"' WHERE `username`='"+MainViewController.me.getName()+"'");
					errMsg.setText("Password successfully changed!");
				}
				else
				{
					errMsg.setText("PASSWORD DON'T MATCH");
					pass_new.setText("");
					pass_new_c.setText("");
					pass_new.requestFocus();
				}
			}
			else
			{
				errMsg.setText("CURRENT PASSWORD AND NEW PASSWORDS ARE SAME");
				pass_new.setText("");
				pass_new_c.setText("");
				pass_new.requestFocus();
			}
		}
		else {
			errMsg.setText("WRONG PASSWORD");
			pass_curr.setText("");
			pass_curr.requestFocus();
		}
	}
	
	@FXML
	public void addItemsToCart()
	{
		int itemsChecked = checkList();
		checkCredit();
		if(itemsChecked!=0)
		{
			for(Products p:list)
			{
				if(p.getCb().isSelected())
				{
					if(!addToCartList.contains(p))
					{
						p.getQuantity().getValueFactory().setValue(1);
						addToCartList.add(p);
					}
				}
			}
			cartMsg.setText(checkCart()+" Items added Successfully!");
		}
		else
		{
			cartMsg.setText("No items selected!");
		}
		
	}
	
	
	
	@FXML
	public void checkCredit()
	{
		MainViewController.db.getQuery("SELECT `creditPoints` FROM `users` WHERE `username` = '"+ MainViewController.me.getName()+"'");
		try {
			credit.setText(MainViewController.db.myResult.getString("creditPoints"));	
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@FXML
	private void goToCart()
	{
		cartMsg.setText("");
		try {
			Main.showCart();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	
	@FXML
	public void selectAll()
	{
		for(Products p: productsTable.getItems())
		{
			p.getCb().setSelected(true);
		}
	}
	
	@FXML
	public void selectNone()
	{
		for(Products p: productsTable.getItems())
		{
			p.getCb().setSelected(false);
		}
	}
		
	@FXML
	public void logout() {
		selectNone();
		addToCartList.clear();
		Main.showMainView();
	}
	
}