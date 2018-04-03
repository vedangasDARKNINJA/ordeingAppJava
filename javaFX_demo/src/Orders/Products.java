package Orders;
import java.sql.SQLException;
import java.util.*;



import Orders.user.*;
import javafx.beans.binding.Binding;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.NumberBinding;
import javafx.beans.property.*;
import javafx.beans.value.ObservableNumberValue;
import javafx.scene.control.*;


public class Products {
	private SimpleIntegerProperty id;
	private SimpleStringProperty product;
	private SimpleIntegerProperty price;
	private SimpleIntegerProperty cart_price;
	private SimpleIntegerProperty stock;
	private CheckBox cb;
	private Button b;
	private Spinner<Integer> quantity;
	
	
	public Products(Integer id, String product, Integer price,Integer stock) {
		super();
		this.id = new SimpleIntegerProperty(id);
		this.product = new SimpleStringProperty(product);
		this.price = new SimpleIntegerProperty(price);
		this.cart_price = new SimpleIntegerProperty(price);
		this.stock = new SimpleIntegerProperty(stock);
		this.cb = new CheckBox();
		this.b = new Button("Remove Item");
		this.quantity = new Spinner<Integer>(new SpinnerValueFactory.IntegerSpinnerValueFactory(1,stock,1));
		
		this.quantity.valueProperty().addListener((obs,oldValue,newValue)->{
			this.cart_price.set(Integer.parseInt(quantity.getValueFactory().getValue().toString())*this.price.get());	
		});
	}

	public Button getB() {
		return b;
	}
	
	public static ArrayList<Products> getProductData(Database d,String query)
	{
		ArrayList<Products> data = new ArrayList<Products>();
		d.getQuery(query);
		try {
			d.myResult.beforeFirst();
			while(d.myResult.next())
			{
				data.add(new Products(Integer.parseInt(d.myResult.getString("id")),d.myResult.getString("name"),Integer.parseInt( d.myResult.getString("price")),Integer.parseInt( d.myResult.getString("stock"))));
			}
			return data;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	
	
	
	public CheckBox getCb() {
		return cb;
	}

	public Integer getId() {
		return id.get();
	}

	public String getProduct() {
		return product.get();
	}

	public Spinner<Integer> getQuantity() {
		return quantity;
	}

	public Integer getPrice() {
		return price.get();
	}
	
	
	public Integer getCart_price() {
		return cart_price.get();
	}

	public Integer getStock() {
		return stock.get();
	}
	
	
	
}
