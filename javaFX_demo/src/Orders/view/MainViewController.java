package Orders.view;

import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.io.IOException;
import java.sql.SQLException;

import Orders.Database;
import Orders.Main;
import Orders.Users;
import Orders.Validation;


public class MainViewController {
	public static Database db = new Database("javaproject");
	public static Users me = new Users();
	
	@FXML
	private TabPane homeTabs;
	@FXML
	private TextField l_username;
	@FXML
	private PasswordField l_pass;
	@FXML
	private TextField s_email;
	@FXML
	private TextField s_username;
	@FXML
	private PasswordField s_pass;
	@FXML
	private PasswordField s_pass_c;
	@FXML
	private Label l_err_msg;
	@FXML
	private Label s_err_msg;
	
	
	@FXML
	public void getLoginData()
	{
		me.setName(l_username.getText());
		String pass = l_pass.getText();
		
		if(!l_username.getText().isEmpty()&& !l_pass.getText().isEmpty())
		{
			db.getQuery("SELECT * FROM `users` WHERE `username` ='"+me.getName().trim()+"' AND `password` = '"+pass+"'");
			if(db.getRows()==0)
			{
				l_err_msg.setText("BAD CREDENTIALS");
			}
			else {
				l_err_msg.setText("LOGIN SUCCESSFUL!");
				
				try {
					db.myResult.first();
					me.setUser(Integer.parseInt(db.myResult.getString("id")), db.myResult.getString("username"), Integer.parseInt(db.myResult.getString("creditPoints")),Integer.parseInt(db.myResult.getString("totalSpent")));
				}
				catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
				if(l_username.getText().compareTo("admin")==0)
				{
					
					try {
						Main.showAdminView();
					}catch (IOException e) {
						// TODO: handle exception
						e.printStackTrace();
					}
				}
				else
				{
					try {
						Main.showUserView();
					}
					catch (IOException e) {
						// TODO: handle exception
						e.printStackTrace();
					}
				}
			}
		}
		else
		{
			l_err_msg.setText("BAD CREDENTIALS");
		}
	}
	
	@FXML
	public void getSignUpData()
	{	
		if(!s_email.getText().isEmpty())
		{
			boolean validEmail = Validation.validateEmail(s_email.getText().trim());
			if(validEmail)
			{
				db.getQuery("SELECT * FROM `users` WHERE `email` = '"+s_email.getText().trim()+"'");
				if(db.getRows()==0)
				{
					if(!s_username.getText().isEmpty())
					{
						db.getQuery("SELECT * FROM `users` WHERE `username` = '"+s_username.getText().trim()+"'");
						if(db.getRows()==0)
						{
							if(!s_pass.getText().isEmpty())
							{
								if(s_pass.getText().compareTo(s_pass_c.getText())==0)
								{
									db.setQuery("INSERT INTO `users`(`email`,`username`,`password`,`creditPoints`,`totalSpent`)VALUES('"+s_email.getText().trim()+"','"+s_username.getText().trim()+"','"+s_pass.getText()+"','10000','0')");
									s_err_msg.setText("SIGN UP SUCCESSFUL!");
									s_email.setText("");
									s_username.setText("");
									s_pass.setText("");
									s_pass_c.setText("");
								}
								else {
									s_pass.setText("");
									s_pass_c.setText("");
									s_pass.requestFocus();
									s_err_msg.setText("PASSWORDS DON'T MATCH!");
								}
							}
							else
							{
								s_pass.setText("");
								s_pass_c.setText("");
								s_pass.requestFocus();
								s_err_msg.setText("PASSWORD EMPTY!");
							}
						}
						else
						{
							s_username.setText("");
							s_username.requestFocus();
							s_err_msg.setText("USERNAME ALREADY TAKEN");
						}
					}
					else
					{
						s_username.setText("");
						s_username.requestFocus();
						s_err_msg.setText("USERNAME EMPTY!");
					}
				}
				else
				{
					s_email.setText("");
					s_email.requestFocus();
					s_err_msg.setText("ACCOUNT WITH SAME EMAIL ALREADY EXISTS! TRY A DIFFERENT EMAIL.");
				}
			}
			else {
				s_email.setText("");
				s_email.requestFocus();
				s_err_msg.setText("INVALID EMAIL!");
			}
		}
		else
		{
			s_email.setText("");
			s_email.requestFocus();
			s_err_msg.setText("EMAIL EMPTY!");
		}
	}
	
	@FXML
	public void switch_to_signup()
	{
		homeTabs.getSelectionModel().select(1);
		s_email.setText("");
		s_username.setText("");
		s_pass.setText("");
		s_pass_c.setText("");
		s_err_msg.setText("");
		s_email.requestFocus();
		
	}
	@FXML
	public void switch_to_login()
	{
		homeTabs.getSelectionModel().select(0);
		l_username.setText("");
		l_pass.setText("");
		l_err_msg.setText("");
		l_username.requestFocus();
	}
		
}
