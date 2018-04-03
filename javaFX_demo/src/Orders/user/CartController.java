package Orders.user;

import java.net.URL;
import java.util.ResourceBundle;

import Orders.*;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.*;

public class CartController implements Initializable{
	@FXML private TableView<Products> table_cart;
	@FXML private TableColumn<Products,Integer> id;
	@FXML private TableColumn<Products,String> product;
	@FXML private TableColumn<Products,Integer> price;
	@FXML private TableColumn<Products,Spinner<Integer>> quantity;
	@FXML private TableColumn<Products,Button> action;
	@FXML private Label amount;
	@FXML private Button closeButton;
	@FXML private Button plcOrder_btn;
	
	private int sum=0;
	public static int totalAmt=0;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		id.setStyle( "-fx-alignment: CENTER-RIGHT;");
		product.setStyle( "-fx-alignment: CENTER;");
		price.setStyle( "-fx-alignment: CENTER;");
		
		
		id.setCellValueFactory(new PropertyValueFactory<Products,Integer>("id"));
		product.setCellValueFactory(new PropertyValueFactory<Products,String>("product"));
		price.setCellValueFactory(new PropertyValueFactory<Products,Integer>("cart_price"));
		quantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));
		action.setCellValueFactory(new PropertyValueFactory<Products,Button>("b"));
		amount.setText("hello");
		id.setSortable(true);
		table_cart.setItems(UserController.addToCartList);
		finalAmount();
		for(Products p: UserController.addToCartList)
		{
			p.getQuantity().valueProperty().addListener(new ChangeListener<Integer>() {

				@Override
				public void changed(ObservableValue<? extends Integer> observable, Integer oldValue, Integer newValue) {
					// TODO Auto-generated method stub
					finalAmount();
				}	
			});
			
			
			p.getB().setOnAction(e->{
				UserController.addToCartList.remove(p);
				finalAmount();
			});
		}
		
	}	
	
	public void finalAmount()
	{
		sum = 0;
		for(Products x : UserController.addToCartList)
		{
			
			sum+= Integer.parseInt(x.getQuantity().getValueFactory().getValue().toString())*x.getPrice().intValue();
		}
		if(sum!= 0)
		{
			plcOrder_btn.setDisable(false);
		}
		else
		{
			plcOrder_btn.setDisable(true);
		}
		amount.setText(Integer.toString(sum));
	}
		
	@FXML
	private void placeOrder()
	{
		try {
			totalAmt = sum;
			if(UserController.addToCartList.isEmpty()==false)
			{
				Main.showPlaceOrders();
			}
			else
			{
				plcOrder_btn.setDisable(true);
			}
						
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@FXML
	private void YourOrders()
	{
		try {
			Main.showYourOrders();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@FXML
	private void closeBtn()
	{
		Stage stage = (Stage) closeButton.getScene().getWindow();
	    stage.close();
	    
	}
		
}


