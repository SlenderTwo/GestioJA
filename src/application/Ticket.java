package application;

import java.sql.Date;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
/**
 * @author jonathantabaresalgaba && adriamartinez
 * @version 2.0 28/05/23
 * Represents a system ticket.
 */
public class Ticket {
	private IntegerProperty   id;
	private  SimpleObjectProperty<Date> date;
	private StringProperty   productList;
	private DoubleProperty   impor;
	private StringProperty   owner;
	/**
	 * Constructs a new Ticket object with the given parameters.
	 * 
	 * @param id           the ticket ID
	 * @param date         the ticket date
	 * @param productList the ticket product list
	 * @param impor        the ticket import
	 * @param owner        the ticket owner
	 */
	public Ticket(int id, Date date, String productList, double impor, String owner) {
		super();
		this.id = new SimpleIntegerProperty();;
		this.date = new SimpleObjectProperty<>();
		this.productList =  new SimpleStringProperty();
		this.impor = new SimpleDoubleProperty();
		this.owner =  new SimpleStringProperty();
		
		
		
		this.id.set(id);
		this.date.set(date);
		this.productList.set(productList);
		this.impor.set(impor);
		this.owner.set(owner) ;
	}
	/**
	 * Returns the ID property of the ticket.
	 * 
	 * @return the ID property
	 */
	public IntegerProperty getIdProperty() {
		return id;
	}
	/**
	 * Returns the ID of the ticket.
	 * 
	 * @return the ID
	 */
	public int getId() {
		return id.get();
	}
	/**
	 * Sets the ID property of the ticket.
	 * 
	 * @param id the ID property to set
	 */
	public void setId(IntegerProperty id) {
		this.id = id;
		
		
	}
	/**
	 * Returns the date property of the ticket.
	 * 
	 * @return the date property
	 */
	public SimpleObjectProperty<Date> getDateProperty() {
		return date;
	}
	/**
	 * Returns the date of the ticket.
	 * 
	 * @return the date
	 */
	public Date getDate() {
		return date.get();
	}

	/**
	 * Sets the date of the ticket.
	 * 
	 * @param date the date to set
	 */
	public void setDate(Date date) {
		this.date.set(date);
		
	}
	/**
	 * Returns the product list property of the ticket.
	 * 
	 * @return the product list property
	 */
	public StringProperty getProductListProperty() {
		return productList;
	}
	/**
	 * Returns the product list of the ticket.
	 * 
	 * @return the product list
	 */
	public String getProductList() {
		return productList.get();
	}
	/**
	 * Sets the product list of the ticket.
	 * 
	 * @param productList the product list to set
	 */
	public void setProductList(String productList) {
		this.productList.set(productList);
	}
	
	
	/**
	 * Returns the import property of the ticket.
	 * 
	 * @return the import property
	 */
	public DoubleProperty getImporProperty() {
		return impor;
	}
	/**
	 * Returns the import of the ticket.
	 * 
	 * @return the import
	 */
	public Double getImpor() {
		return impor.get();
	}
	
	public void setImpor(Double impor) {
		this.impor.set(impor);
	}
	
	
	
	/**
	 * Returns the owner property of the ticket.
	 * 
	 * @return the owner property
	 */
	public StringProperty getOwnerProperty() {
		return owner;
	}
	/**
	 * Returns the owner of the ticket.
	 * 
	 * @return the owner
	 */
	public String getOwner() {
		return owner.get();
	}
	/**
	 * Sets the owner of the ticket.
	 * 
	 * @param owner the owner to set
	 */
	public void setOwner(String owner) {
		this.owner.set(owner);
	}
	
	
	

}
