package application;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
/**
 * 
 * @author jonathantabaresalgaba && adriamartinez
 * @version 5.0 31/05/23 
 * Represents a system user.
 */
public class User {
	private StringProperty userName;
	private StringProperty password;
	private StringProperty email;
	
	/**
     * Returns the name from user.
     * 
     * @return the user name
     */
	public String getUserName() {
		return userName.get();
	}
	/**
     * Set username.
     * 
     * @param userName the name of the user to set
     */
	public void setUserName(String userName) {
		this.userName.set(userName);
	}
	/**
     * Returns the username property.
     * 
     * @return the username property
     */
	public StringProperty getUserNameProperty() {
		return userName;
	}
	
	
	
	
	
	/**
     * Returns the user's password.
     * 
     * @return the user's password
     */
	public String getPassword() {
		return password.get();
	}
	/**
     * Set the user's password.
     * 
     * @param password the password to set
     */
	public void setPassword(String password) {
		this.password.set(password);
	}
	/**
     * Returns the user's password property
     * 
     * @return the user's password property
     */
	public StringProperty getPasswordProperty() {
		return password;
	}
	
	/**
     * Returns the user's email
     * 
     * @return the user's email
     */
	public String getEmail() {
		return email.get();
	}
	/**
     * Set the user's email
     * 
     * @param e-mail to be established
     */
	public void setEmail(String email) {
		this.email.set(email);
	}
	/**
     * Returns the property of the user's email
     * 
     * @return the ownership of the user's email
     */
	public StringProperty getEmailProperty() {
		return email;
	}
	
	
	
	/**
     * Creates a new User object with the specified username, password, and email
     * 
     * @param userName the name of the user
     * @param password the password
     * @param email email
     */
	public User(String userName, String password, String email) {
		super();
		
		this.userName = new SimpleStringProperty();
		this.password = new SimpleStringProperty();;
		this.email = new SimpleStringProperty();;
		
		
		
		this.userName.set(userName);
		this.password.set(password);
		this.password.set(email);
		
		
	}
	

}
