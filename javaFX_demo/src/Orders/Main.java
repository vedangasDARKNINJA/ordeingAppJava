package Orders;

import java.io.IOException;

import Orders.view.MainViewController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class Main extends Application {

	 private static Stage cartStage; 
	 private static Stage primaryStage;
	 private static BorderPane mainLayout;
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		Main.primaryStage = primaryStage;
		Main.primaryStage.setTitle("Log in/Sign Up");
		showMainView();

	}

	public static void showMainView() {
		// TODO Auto-generated method stub
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Main.class.getResource("view/MainView.fxml"));
			mainLayout = loader.load();
			
			Scene scene = new Scene(mainLayout);
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public static void showAdminView() throws IOException {
		FXMLLoader loader = new FXMLLoader(); 
		loader.setLocation(Main.class.getResource("admin/Admin.fxml"));
		mainLayout = loader.load();
				
		Scene scene = new Scene(mainLayout);
		primaryStage.setScene(scene);
		primaryStage.show();
	}
	
	public static void showUserView() throws IOException {
		FXMLLoader loader = new FXMLLoader(); 
		loader.setLocation(Main.class.getResource("user/User.fxml"));
		mainLayout = loader.load();
				
		Scene scene = new Scene(mainLayout);
		primaryStage.setTitle("My Account");
		primaryStage.setScene(scene);
		primaryStage.show();
	}
	
	public static void showCart() throws Exception
	{
		FXMLLoader loader = new FXMLLoader(); 
		loader.setLocation(Main.class.getResource("user/Cart.fxml"));
		BorderPane cart = loader.load();
		
		cartStage = new Stage();
		cartStage.setTitle("MY CART");
		cartStage.initModality(Modality.WINDOW_MODAL);
		cartStage.initOwner(primaryStage);
		
		Scene myCart = new Scene(cart);
		cartStage.setScene(myCart);
		cartStage.show();
		
	}
	
	public static void showPlaceOrders() throws Exception
	{
		FXMLLoader loader = new FXMLLoader(); 
		loader.setLocation(Main.class.getResource("user/PlaceOrder.fxml"));
		BorderPane cart = loader.load();
		
		Stage orderStage = new Stage();
		orderStage.setTitle("CURRENT ORDER");
		orderStage.initModality(Modality.WINDOW_MODAL);
		orderStage.initOwner(cartStage);
		
		Scene orders= new Scene(cart);
		orderStage.setScene(orders);
		orderStage.show();
		
	}
	
	public static void showYourOrders() throws Exception
	{
		FXMLLoader loader = new FXMLLoader(); 
		loader.setLocation(Main.class.getResource("user/YourOrders.fxml"));
		BorderPane cart = loader.load();
		
		Stage orderStage = new Stage();
		orderStage.setTitle("YOUR ORDERS");
		orderStage.initModality(Modality.WINDOW_MODAL);
		orderStage.initOwner(cartStage);
		
		Scene orders= new Scene(cart);
		orderStage.setScene(orders);
		orderStage.show();
		
	}
		
	public static void main(String[] args) {
		launch(args);
	}
		
}
