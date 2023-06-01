package application;  

import java.awt.Desktop;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import com.itextpdf.text.Anchor;
import com.itextpdf.text.BadElementException;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chapter;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.List;
import com.itextpdf.text.ListItem;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Section;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import javax.imageio.ImageIO;

import com.itextpdf.text.Document;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;  
import javafx.event.EventHandler;  
import javafx.scene.Scene;  
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.effect.BoxBlur;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
/**
 * @author jonathantabaresalgaba && adriamartinez
 * @version 5.0 31/05/23
 */
public class Main extends Application {

	
    private static String FILE = "FirstPdf.pdf";
    private static Font catFont = new Font(Font.FontFamily.TIMES_ROMAN, 18, Font.BOLD); 
    private static Font redFont = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.NORMAL, BaseColor.RED);
    private static Font subFont = new Font(Font.FontFamily.TIMES_ROMAN, 16, Font.BOLD);
    private static Font smallBold = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.BOLD);
    
    
	// We define the data to connect to the database:
	static String user = "sql7620848";
	static String password = "7azSfXqnI4";
	static String queryUser = "SELECT  j.password FROM users j where j.name = ?";
	static String queryUsers = "SELECT  * FROM users";
	static String addUser = "INSERT INTO users (name, password, email) VALUES(?, ?, ?)";
	static String queryTikcets = "SELECT `date`, productsList, impor, owner, id FROM tickets where owner = ?";
	static String queryTotal = "SELECT SUM(impor) FROM tickets where owner = ?";
	static String queryTikcetsA = "SELECT `date`, productsList, impor, owner, id FROM tickets";
	static String queryTotalA = "SELECT SUM(impor) FROM tickets";
	static String queryTotalUsers = "SELECT count(*) FROM users";
	static String deleteUser = "DELETE FROM users WHERE name= ? ";
	
	static String server = "sql7.freesqldatabase.com";
	static String db = "sql7620848";
	
	//variables where we will save the connection to the database and the queries
	  static Connection con;
	  static PreparedStatement stmt;
	  static ResultSet rs ;

	
	//Define the constants and parts of the program that do not change, and must be accessible from any part of the program
	  
	//Define the containing variable and display the screens
	private Stage stage;
	
	//Define the Login screen: first the buttons and labels and then put them in a panel 
    Label userL=new Label("User name");  
    Label passwordL=new Label("Password");  
    TextField tf1=new TextField();  
    TextField tf2=new TextField();  
    Button Submit=new Button ("Submit");
    Button createUSer=new Button ("Register new user");
    Label information=new Label(""); 
    Group loginPanel=new Group();
    //GridPane loginPanel=new GridPane();
    Scene sceneLogin = new Scene(loginPanel,700,550);
    
  //Define the sign up screen: first the buttons and labels and then put them inside a panel
    Label usernameR=new Label("User name");  
    Label passwordRR=new Label("Password");  
    Label emailR=new Label("Email");
    TextField tf1R=new TextField();  
    TextField tf2R=new TextField();  
    TextField tf3R=new TextField();
    Button SubmitR=new Button ("Submit");
    Label informationR=new Label(""); 
    Button Cancel=new Button ("Cancel");
    Group registerPanel=new Group();
    Scene sceneRegister = new Scene(registerPanel,700,550);
    
    
  //Define The ticket screen
    Label userT=new Label("User:");
    TextField tf1T=new TextField(); 
    Label listT=new Label("Tickets list:");
    TableView table = new TableView();
    Button exitR=new Button ("Exit");
    Group ticketsPanel=new Group();  
    Scene sceneTickets = new Scene(ticketsPanel,700,700);
    Label totalL=new Label("Total:");
    TextField tf2T=new TextField();
    Button printR=new Button ("Print ticket");
    Label informationT=new Label(""); 
    
    
  //Define The ticket administration screen
    Label userA=new Label("Total users::");
    TextField tf1A=new TextField(); 
    Label listA=new Label("Tickets list:");
    TableView tableA = new TableView();
    Button exitA=new Button ("Exit");
    Group ticketsPanelA=new Group();  
    Scene sceneTicketsA = new Scene(ticketsPanelA,700,700);
    Label totalA=new Label("Total income:");
    TextField tf2A=new TextField();
    Label informationA=new Label(""); 
    Button deleteUsers=new Button ("DeleteUsers");
    
    
    
    
  //Define the user management screen
    Label userG=new Label("Gestio d'usauris");
    Label listG=new Label("Users list:");
    TableView tableG = new TableView();
    Button exitG=new Button ("Exit");
    Group ticketsPanelG=new Group();  
    Scene sceneTicketsG = new Scene(ticketsPanelG,700,700); 
    Button deleteUsersG=new Button ("Delete selected user");
    Button goBackG=new Button ("Go back");
	  
    /*
     * Users i tickets són llistes observables en JavaFX que permeten rastrejar canvis en els
     * objects they contain and automatically notify the graphics components when they occur
     * changes to the list. These lists are used to store and manage collections 
     * of users (User) and tickets (Ticket), and can be linked to interface components
     * user to keep data and display in sync
     */

    private final ObservableList<User> users = FXCollections.observableArrayList();
    private final ObservableList<Ticket> tickets = FXCollections.observableArrayList();
    
    
    @Override  
    public void start(Stage primaryStage) throws Exception {  
 
        InputStream stream = new FileInputStream("resources/logo.png");
        Image image = new Image(stream);
        //Creating the image view
        ImageView imageView = new ImageView();
        ImageView imageView2 = new ImageView();
        imageView2.setImage(image);
        
        //Setting image to the image view
        imageView.setImage(image);
       
        //Setting the image view parameters
        imageView.setX(200);
        imageView.setY(50);
        imageView.setFitWidth(300);
        imageView.setPreserveRatio(true);
        imageView2.setX(200);
        imageView2.setY(50);
        imageView2.setFitWidth(300);
        imageView2.setPreserveRatio(true);
    	
    	
     // We get the main screen, which is given to us by the operating system
    	stage = primaryStage;
    	stage.setTitle("Gestió J&A");
    	userL.setLayoutX(150);
        userL.setLayoutY(300);
        userL.setPrefWidth(100);        
        tf1.setLayoutX(300);
        tf1.setLayoutY(300);
        tf1.setPrefWidth(180);
        passwordL.setLayoutX(150);
        passwordL.setLayoutY(330);
        passwordL.setPrefWidth(100);        
        tf2.setLayoutX(300);
        tf2.setLayoutY(330);
        tf2.setPrefWidth(180);
        Submit.setLayoutX(250);
        Submit.setLayoutY(400);
        Submit.setPrefWidth(180);
        createUSer.setLayoutX(250);
        createUSer.setLayoutY(440);
        createUSer.setPrefWidth(180);
        information.setLayoutX(250);
        information.setLayoutY(490);
        information.setPrefWidth(4000);

     // We assemble the graphic elements of the Login panel
        loginPanel.getChildren().add(userL);
        loginPanel.getChildren().add(passwordL);
        loginPanel.getChildren().add(tf1);
        loginPanel.getChildren().add(tf2);
        loginPanel.getChildren().add(Submit);
        loginPanel.getChildren().add(createUSer);
        loginPanel.getChildren().add(information);
        loginPanel.getChildren().add(imageView);
        
        
        primaryStage.setScene(sceneLogin);  
        primaryStage.show();  
        Submit.setOnAction(this::ActionLogin); // Here, we say that when the Submute button is clicked, it is done
        createUSer.setOnAction(this::showRegister);

        

        usernameR.setLayoutX(150);
        usernameR.setLayoutY(300);
        usernameR.setPrefWidth(100);        
        tf1R.setLayoutX(300);
        tf1R.setLayoutY(300);
        tf1R.setPrefWidth(180);
        passwordRR.setLayoutX(150);
        passwordRR.setLayoutY(330);
        passwordRR.setPrefWidth(100);        
        tf2R.setLayoutX(300);
        tf2R.setLayoutY(330);
        tf2R.setPrefWidth(180);
        emailR.setLayoutX(150);
        emailR.setLayoutY(360);
        emailR.setPrefWidth(100);        
        tf3R.setLayoutX(300);
        tf3R.setLayoutY(360);
        tf3R.setPrefWidth(180);
        SubmitR.setLayoutX(250);
        SubmitR.setLayoutY(430);
        SubmitR.setPrefWidth(180);
        Cancel.setLayoutX(250);
        Cancel.setLayoutY(470);
        Cancel.setPrefWidth(180);
        informationR.setLayoutX(250);
        informationR.setLayoutY(490);
        informationR.setPrefWidth(180);
        
        registerPanel.getChildren().add(usernameR);
        registerPanel.getChildren().add(tf1R);
        registerPanel.getChildren().add(passwordRR);
        registerPanel.getChildren().add(tf2R);
        registerPanel.getChildren().add(emailR);
        registerPanel.getChildren().add(tf3R);
        registerPanel.getChildren().add(SubmitR);
        registerPanel.getChildren().add(informationR);
        registerPanel.getChildren().add(Cancel);
        registerPanel.getChildren().add(imageView2);
       
        SubmitR.setOnAction(this::ActionRegister); // Here, we say that when the Submute button is clicked, it is done
        Cancel.setOnAction(this::showLoginPanel);


        
       
        
        
        
       /*
        * tf1T and tf2T components are set to disabled mode,
        * which means it will not be able to edit or receive user input events.
        */
        tf1T.setDisable(true);
        tf2T.setDisable(true);
       /*
        * This code configures the columns of a table in JavaFX and sets how they are obtained
        * the values ​​of each cell based on the attributes of the associated objects. The columns
        * represent different ticket properties such as number, date, list of
        * products and the amount. The table is set to display a list of tickets and is added
        * the list in the table for viewing.
        */
        TableColumn idCol = new TableColumn("Numero");
        idCol.setMinWidth(50);
        idCol.setCellValueFactory(new PropertyValueFactory("id"));
        TableColumn<Ticket, Date>  dateCol = new TableColumn("Date");
        dateCol.setCellValueFactory(new PropertyValueFactory("date"));
        TableColumn productsCol = new TableColumn("Products list");
        productsCol.setMinWidth(350);
        productsCol.setCellValueFactory(new PropertyValueFactory("productList"));
        TableColumn importCol = new TableColumn("Import");
        importCol.setCellValueFactory(new PropertyValueFactory("impor"));
        table.getColumns().addAll(idCol, dateCol, productsCol,importCol );
        table.setItems(tickets);
        exitR.setLayoutX(500);
        exitR.setLayoutY(50);
        exitR.setPrefWidth(80);
        userT.setLayoutX(50);
        userT.setLayoutY(50);
        userT.setPrefWidth(80);
        tf1T.setLayoutX(150);
        tf1T.setLayoutY(50);
        tf1T.setPrefWidth(50);
        totalL.setLayoutX(50);
        totalL.setLayoutY(70);
        totalL.setPrefWidth(80);
        tf2T.setLayoutX(150);
        tf2T.setLayoutY(70);
        tf2T.setPrefWidth(80);
        table.setLayoutX(50);
        table.setLayoutY(120);
        table.setPrefWidth(600);
        printR.setLayoutX(50);
        printR.setLayoutY(600);
        printR.setPrefWidth(80);
        listT.setLayoutX(50);
        listT.setLayoutY(100);
        listT.setPrefWidth(80);
        printR.setOnAction(this::ActionPrint);
        informationT.setLayoutX(200);
        informationT.setLayoutY(600);
        informationT.setPrefWidth(2000);
        
        
        ticketsPanel.getChildren().add(userT);
        ticketsPanel.getChildren().add(tf1T);
        ticketsPanel.getChildren().add(listT);
        ticketsPanel.getChildren().add(table);
        ticketsPanel.getChildren().add(totalL);
        ticketsPanel.getChildren().add(tf2T);
        ticketsPanel.getChildren().add(exitR);
        ticketsPanel.getChildren().add(printR);
        ticketsPanel.getChildren().add(informationT);
        exitR.setOnAction(this::showLoginPanel);
        
        
       /*
        * This code configures the columns of another table in JavaFX and sets the com
        * the values ​​of each cell in each column are obtained from the attributes of the
        * associated objects. The list of items is also added to the table for yours visualization
        */
        tf1A.setDisable(true);
        tf2A.setDisable(true);
        TableColumn idCol2 = new TableColumn("Numero");
        idCol2.setMinWidth(50);
        idCol2.setCellValueFactory(new PropertyValueFactory("id"));
        TableColumn<Ticket, Date>  dateCol2 = new TableColumn("Date");
        dateCol2.setCellValueFactory(new PropertyValueFactory("date"));
        TableColumn productsCol2 = new TableColumn("Products list");
        productsCol2.setMinWidth(250);
        productsCol2.setCellValueFactory(new PropertyValueFactory("productList"));
        TableColumn importCol2 = new TableColumn("Import");
        importCol2.setCellValueFactory(new PropertyValueFactory("impor"));
        TableColumn ownerCol2 = new TableColumn("Client");
        ownerCol2.setCellValueFactory(new PropertyValueFactory("owner"));
        tableA.getColumns().addAll(idCol2, dateCol2, productsCol2,importCol2,ownerCol2 );
        tableA.setItems(tickets);
        exitA.setLayoutX(500);
        exitA.setLayoutY(50);
        exitA.setPrefWidth(80);
        userA.setLayoutX(50);
        userA.setLayoutY(50);
        userA.setPrefWidth(80);
        tf1A.setLayoutX(150);
        tf1A.setLayoutY(50);
        tf1A.setPrefWidth(50);
        totalA.setLayoutX(50);
        totalA.setLayoutY(70);
        totalA.setPrefWidth(80);
        tf2A.setLayoutX(150);
        tf2A.setLayoutY(70);
        tf2A.setPrefWidth(80);
        tableA.setLayoutX(50);
        tableA.setLayoutY(120);
        tableA.setPrefWidth(600);
        printR.setLayoutX(50);
        printR.setLayoutY(600);
        printR.setPrefWidth(80);
        listA.setLayoutX(50);
        listA.setLayoutY(100);
        listA.setPrefWidth(80);
        printR.setOnAction(this::ActionPrint);
        informationA.setLayoutX(200);
        informationA.setLayoutY(600);
        informationA.setPrefWidth(2000);
        
        deleteUsers.setLayoutX(300);
        deleteUsers.setLayoutY(50);
        deleteUsers.setPrefWidth(80);
        
        
        ticketsPanelA.getChildren().add(userA);
        ticketsPanelA.getChildren().add(tf1A);
        ticketsPanelA.getChildren().add(listA);
        ticketsPanelA.getChildren().add(tableA);
        ticketsPanelA.getChildren().add(totalA);
        ticketsPanelA.getChildren().add(tf2A);
        ticketsPanelA.getChildren().add(exitA);
        ticketsPanelA.getChildren().add(informationA);
        ticketsPanelA.getChildren().add(deleteUsers);
        exitA.setOnAction(this::showLoginPanel);
        deleteUsers.setOnAction(this::showDeleteUsers);

        
        
         
        
        TableColumn idCol3 = new TableColumn("Usuari");
        idCol3.setMinWidth(50);
        idCol3.setCellValueFactory(new PropertyValueFactory("userName"));
        tableG.getColumns().addAll(idCol3);
        tableG.setItems(users);
        exitG.setLayoutX(500);
        exitG.setLayoutY(50);
        exitG.setPrefWidth(80);
        userG.setLayoutX(50);
        userG.setLayoutY(50);
        userG.setPrefWidth(200);
        tableG.setLayoutX(50);
        tableG.setLayoutY(120);
        tableG.setPrefWidth(100);
        listG.setLayoutX(50);
        listG.setLayoutY(100);
        listG.setPrefWidth(80);

        
        deleteUsersG.setLayoutX(300);
        deleteUsersG.setLayoutY(50);
        deleteUsersG.setPrefWidth(80);
        
        goBackG.setLayoutX(410);
        goBackG.setLayoutY(50);
        goBackG.setPrefWidth(80);
        
        
        ticketsPanelG.getChildren().add(userG);
        ticketsPanelG.getChildren().add(listG);
        ticketsPanelG.getChildren().add(exitG);
        ticketsPanelG.getChildren().add(deleteUsersG);
        ticketsPanelG.getChildren().add(tableG);
        ticketsPanelG.getChildren().add(goBackG);
        exitG.setOnAction(this::showLoginPanel);
        deleteUsersG.setOnAction(this::deleteUsers);
        goBackG.setOnAction(this::showEnterAdmin);
       
       
    }  
    
    /*
     * Here we have done the main start of the database, the launch starts the first "stage" and
     * "close DB" closes the database
     */
    public static void main(String[] args) { 
    	iniciarDB();
        launch(args);  
		  closeDB(); 

    }  
    
    /*
     * This method establishes a connection to the database using the configuration
     * of server, database, user and password specified.
     * The connection is established using the MariaDB JDBC driver.
     * If an error occurs during the connection process, the exception trace is printed.
     */
	private static void iniciarDB() {
		con = null;
		String sURL = "jdbc:mariadb://" + server + ":3306/" + db;
		  try {
			con = DriverManager.getConnection(sURL,user,password);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	/*
	 * This code closes the connection to the database. Check
	 * if the connection is non-null, and if so, closes it.
	 * If an exception occurs during the shutdown process, a message is displayed
	 * error message indicating that there was an error closing the connections, along with
	 * the description of the exception.
	 */
	private static void closeDB() {
		try {
		    if (con!=null) con.close();    
		  } catch (Exception e) {
		    System.out.println("Error tancant les connexions: "
		      + e.toString());
		  }
	}

		
	 
	/*
	 * The showEnterShop method is a function that runs in response to an ActionEvent event.
	 * First, it hides the current window and sets a new scene to show one
	 * store interface It then deletes the existing elements from a table. After,
	 * realitza consultes a una base de dades utilitzant sentències preparades i valors obtinguts 
	 * of text fields. Query results are processed in a loop, where they are printed
	 * some values ​​in the console and Ticket objects are created and added to the table. It is also
	 * performs an additional query to get a total value, which is displayed in another field
	 * of text If an SQLException occurs, error information is printed.
	 */
	private void showEnterShop(ActionEvent event) {
	    Ticket singleTicket;
        stage.hide();
        stage.setScene(sceneTickets);  
        stage.show();
        table.getItems().clear();
        try 
        {			
		    stmt = con.prepareStatement(queryTikcets); 
	        stmt.setString(1, tf1T.getText());
		    rs = stmt.executeQuery(); 
		    while (rs.next())
		    {
		    	System.out.println(rs.getString("id") + rs.getString("date") + rs.getString("productsList") + rs.getString("impor") + rs.getString("owner"));
		    	singleTicket = new Ticket(rs.getInt("id"),rs.getDate("date"), rs.getString("productsList"), rs.getDouble("impor"),  rs.getString("owner"));
		    	table.getItems().add(singleTicket);
		    }
		    stmt = con.prepareStatement(queryTotal); 
	        stmt.setString(1, tf1T.getText());
		    rs = stmt.executeQuery();
		    rs.next();
		    System.out.println(rs.getFloat(1));
		    tf2T.setText(rs.getString(1));
		    	
		    } catch (SQLException sqle) { 
		      System.out.println("Error" 
		    + sqle.getErrorCode() + " " + sqle.getMessage());    
		    }
    	
        }
	
	
	
	/*
	 * The showEnterAdmin function is fired in response to an ActionEvent 
	 * first hide the current window and set a new scene for 
	 * show an admin interface. Then delete the items
	 * existing ones from the application table, then perform a query a 
	 * the database to get a list of tickets and query results
	 * are processed in a loop, creating Ticket objects that are added to the application table
	 * then performs two additional queries to obtain related total values
	 * with users and tickets. These values ​​are assigned to text fields in the interface. 
	 * If an SQLException is thrown, error information is displayed
	 */
	private void showEnterAdmin(ActionEvent actionevent1) {
	    Ticket singleTicket;
        stage.hide();
        stage.setScene(sceneTicketsA);  
        stage.show();
        tableA.getItems().clear();
        try 
        {			
		    stmt = con.prepareStatement(queryTikcetsA); 
		    rs = stmt.executeQuery(); 
		    while (rs.next())
		    {
		    	singleTicket = new Ticket(rs.getInt("id"),rs.getDate("date"), rs.getString("productsList"), rs.getDouble("impor"),  rs.getString("owner"));
		    	tableA.getItems().add(singleTicket);
		    }
		    
		    
		    stmt = con.prepareStatement(queryTotalUsers); 
	        
		    rs = stmt.executeQuery();
		    rs.next();
		    
		    tf1A.setText(rs.getString(1));
		    
		    
		    stmt = con.prepareStatement(queryTotalA); 
	      
		    rs = stmt.executeQuery();
		    rs.next();
		    
		    tf2A.setText(rs.getString(1));
		    	
		    } catch (SQLException sqle) { 
		      System.out.println("Error" 
		    + sqle.getErrorCode() + " " + sqle.getMessage());    
		    }
    	
        }
	
	
	
	/*
	 * The showLoginPanel function is fired in response to an ActionEvent. first,
	 * set the contents of text fields tf1 and tf2 to an empty string then,
	 * hide the current window and set a new scene to show the login panel and
	 * finally, show the window
	 */
	private void showLoginPanel(ActionEvent actionevent1) {
		tf1.setText("");
		tf2.setText("");
        stage.hide();
        stage.setScene(sceneLogin);  
        stage.show();
 
	}
	
	
	/*
	 * The showDeleteUsers function is executed in response to an ActionEvent event. 
	 * First, hide the current window and set a new scene to show a table
	 * of users Then delete the existing elements in the table. Then it is performed
	 * a database query to get user information. To 
	 * for each record fetched, a User object is created and added to the table. in case 
	 * If an error occurs during query execution, an error message is printed
	 */
	private void showDeleteUsers(ActionEvent actionevent1) {
		User singleUser;
        stage.hide();
        stage.setScene(sceneTicketsG);  
        stage.show();
        tableG.getItems().clear();
        try 
        {			
		    stmt = con.prepareStatement(queryUsers); 
		    rs = stmt.executeQuery(); 
		    while (rs.next())
		    {
		    	
		    	singleUser = new User(rs.getString("name"), rs.getString("password") , rs.getString("email"));
		    	tableG.getItems().add(singleUser);
		    }
		    	
		    } catch (SQLException sqle) { 
		      System.out.println("Error" 
		    + sqle.getErrorCode() + " " + sqle.getMessage());    
		    }
 
	}
	/*
	* The deleteUsers function is executed in response to an ActionEvent event.
	* First, it gets the selected user from the table tableG. If no user is selected,
	* the message "No user selected" is printed. Otherwise, a query is performed
	* to delete the user from the database using the deleteUser statement. The username
	* is obtained from the selected user object. After the query is executed, a is called
	* the showDeleteUsers function to update the users table
	*/
	private void deleteUsers(ActionEvent actionevent1) {
		
		User user = (User) tableG.getSelectionModel().getSelectedItem();
		if (user == null)
		{
			System.out.println("Cap usuari seleccionat");
		}
		else
		{
			
		    try {
				stmt = con.prepareStatement(deleteUser);
		        stmt.setString(1, user.getUserName());
			    rs = stmt.executeQuery();
			} catch (SQLException e) {
				
				e.printStackTrace();
			} 

			
			showDeleteUsers(null);
		}
		
		
	
 
	}
	/* 
	 * The ActionLogin function is executed in response to an ActionEvent that is
	 * occurs when a button is pressed or an input event is triggered. In this function,
	 * several variables are initialized to store results and passwords.
	 * A query is made to the database using the username entered in the
	 * tf1 field to get the password associated with this user using the statement
	 * queryUser. If the query succeeds, the password is saved in the savedPassword variable.
	 * Otherwise, the message "User does not exist" is displayed.
	 * The password entered in the tf2 field is then compared to the password
	 * saved in savedPassword. If the passwords match, it checks if the user is
	 * "admin". If yes, the showEnterAdmin function is called to display the interface
	 * of administrator If not "admin", the tf1T field is set to the entered username
	 * and the showEnterShop function is called to display the shopping interface.
	 * If the passwords do not match, the message "Incorrect password" is displayed.
	 * Finally, the contents of fields tf1 and tf2 are deleted.
	 */
	
   	private void ActionLogin(ActionEvent event) {
   	  String result;
   	  String savedPassword = null;
	  try 
	  {			
	    stmt = con.prepareStatement(queryUser); 
        stmt.setString(1, tf1.getText());
	    rs = stmt.executeQuery(); 
	    while (rs.next())
	    	savedPassword =  rs.getString("password");
	    } catch (SQLException sqle) { 
	      System.out.println("Error" 
	    + sqle.getErrorCode() + " " + sqle.getMessage());    
	    }
	  
	  if (savedPassword == null)
	  {
	 information.setText("L'usuari no existeix");  
	  }
	  else if (savedPassword.compareTo(tf2.getText()) == 0)
	  {
		  if( tf1.getText().compareTo("admin")==0)
		  {
			  showEnterAdmin(null);	  
		  }
		  else
		  {
			  tf1T.setText(tf1.getText());
			  showEnterShop(null);  
		  }
		    
	  } else
	  {
		  information.setText("Password incorrecte");
	  }
	  tf1.setText(""); 
	  tf2.setText(""); 

	}
    	
   /*
    * The showRegister function is executed in response to an ActionEvent event
    * which occurs when a button is pressed or an input event is triggered.
    * In this function, the current scene is hidden using the object's hide method 
    * stage and the registration scene is set using the object's setScene method
    * stage, using the previously defined sceneRegister scene. Finally,
    * the stage is added to the window using the stage object's show method,
    * so the registration scene is displayed to the user.
    */
	private void showRegister(ActionEvent actionevent1) {
	      stage.hide();
	        stage.setScene(sceneRegister);  
	        stage.show();
	}
	/*
	 *  In this function, you get the selected element from the table using the
	 *  getSelectedItem() method and checks if it is null or not. If element is null,
	 *  the informationT label text is updated to indicate that no ticket has
	 *  been selected Otherwise, if a ticket has been selected, the text of
	 *  the informationT tag is updated by emptying it for any previous messages.
	 *  The printTicket() function is then called with the ticket
	 *  selected as an argument to print the ticket. This function is
	 *  responsible for processing and managing ticket printing.
	 */
	private void ActionPrint(ActionEvent actionevent1) {
		Ticket ticket = (Ticket) table.getSelectionModel().getSelectedItem();
		if (ticket == null)
		{
			informationT.setText("cap ticket seleccionat"); 
		}
		else
		{
			informationT.setText(""); 
			
			printTicket(ticket);
		}
		
		
		
	}
	

	/*
	 * The printTicket function takes a Ticket object as a parameter and is responsible for generating i
	 * print the contents of the ticket in a PDF document. It starts by creating an instance of the
	 * document and specifying the output file using PdfWriter.getInstance().
	 * The document is then opened and different paragraphs are added to the content.
	 * The opening paragraph contains the heading "Purchase Ticket". Then, information is provided
	 * detailed information about the ticket, such as the ticket number (ticket.getId()), the associated customer
	 * (ticket.getOwner()), the ticket date (ticket.getDate()), the product detail
	 * (ticket.getProductList()) and the ticket amount (ticket.getImpor()).
	 * All these details are added as paragraphs to the document using the method
	 * preface.add(new Paragraph(...)).preface.add(new Paragraph(...)).
	 * Once all content has been added to the document, the document is closed using document.close().
	 * A file object is then created with the generated PDF file and attempted
	 * open using the Desktop class. If the operation is successful, the PDF file will open
	 * with the default application for viewing PDF files. If the operation is not successful,
	 * the exception is displayed.
	 */
	private void printTicket(Ticket ticket) {
        try {
            Document document = new Document();
            PdfWriter.getInstance(document, new FileOutputStream(FILE));
            document.open();
            Paragraph preface = new Paragraph();
            

          
            preface.add(new Paragraph("Ticket de compra", catFont));

            // Will create: Report generated by: _name, _date
            preface.add(new Paragraph( "Ticket número: " + ticket.getId(),smallBold));
            preface.add(new Paragraph( "Client: " + ticket.getOwner(),smallBold));
            preface.add(new Paragraph( "Data: " + ticket.getDate(),smallBold));
            preface.add(new Paragraph(""));
            preface.add(new Paragraph(""));
            preface.add(new Paragraph(""));
            preface.add(new Paragraph(""));
            preface.add(new Paragraph( "Detall: " + ticket.getProductList(),smallBold));
            preface.add(new Paragraph( "Import: " + ticket.getImpor(),smallBold));
            document.add(preface);
            // Start a new page
                        document.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        
      //text file, should be opening in default text editor
        File file = new File(FILE);
        
        //first check if Desktop is supported by Platform or not
        if(!Desktop.isDesktopSupported()){
            System.out.println("Desktop is not supported");
            return;
        }
        
        Desktop desktop = Desktop.getDesktop();
        if(file.exists())
			try {
				desktop.open(file);
		        if(file.exists()) desktop.open(file);
			} catch (IOException e) {
			
				e.printStackTrace();
			}
        
        //let's try to open PDF file

		
	}
	/*
	 * The ActionRegister function is called when the register button is clicked and
	 * is responsible for managing the registration process of a new user in the system.
	 * It starts by reading the username entered (tf1R.getText()) and checks if already
	 * a user with that name exists in the database. If no password was found
	 * stored by this username (savedPassword == null), checks if the password field
	 * (tf2R.getText()) is empty. If so, it displays an error message indicating that the
	 * password cannot be empty. Otherwise, a query is executed to add the
	 * new user to the database using addUser. Username, password are saved
	 * and the email address provided in the query parameters. if it is
	 * throws an exception during query execution, the exception plot is displayed.
	 * After successful execution of the user's insert query, the text fields
	 * s'esborren i es mostra un missatge indicant que l'usuari s'ha creat correctament i es 
	 * prompts the user to enter credentials to start the session. Next, it is
	 * call the showLoginPanel function to display the login panel. If found
	 * a saved password for this username (savedPassword != null), is displayed
	 * an error message stating that the user already exists.
	 */
	private void ActionRegister(ActionEvent actionevent1) {
	  	  String result;
	   	  String savedPassword = null;
		  try 
		  {			
		    stmt = con.prepareStatement(queryUser); 
	        stmt.setString(1, tf1R.getText());
		    rs = stmt.executeQuery(); 
		    while (rs.next())
		    	savedPassword =  rs.getString("password");
		    } catch (SQLException sqle) { 
		      System.out.println("Error" 
		    + sqle.getErrorCode() + " " + sqle.getMessage());    
		    }
		  
		  if (savedPassword == null)
		  	{
			if (tf2R.getText().isEmpty() )  
				{
				informationR.setText("El password no pot estar buit"); 
				}
				else
				{
				informationR.setText(""); 
				  try 
				  {			
				    stmt = con.prepareStatement(addUser); 
			        stmt.setString(1, tf1R.getText());
			        stmt.setString(2, tf2R.getText());
			        stmt.setString(3, tf3R.getText());
				    rs = stmt.executeQuery(); 
				    } catch (SQLException sqle) { 
				      System.out.println("Error" 
				    + sqle.getErrorCode() + " " + sqle.getMessage());    
				    }
				  tf1R.setText(""); 
				  tf2R.setText(""); 
				  tf3R.setText("");
				  information.setText("usuari creat correctament. Introdueix les credencials");
				  showLoginPanel(null);
				}
		  	} 
		  else
		  	{
			  informationR.setText("L'usuari ja existeix"); 
		  	}

		}
    
  
    
      
}  


