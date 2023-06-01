package test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SQLTest {

	public static void main(String[] args) 
		{
		Connection con = null;
		String sURL = "jdbc:mariadb://sql7.freesqldatabase.com:3306/sql7620856";
		try
			{
			con = DriverManager.getConnection(sURL, "sql7620856", "T8j3DyGscG");
			try (PreparedStatement stmt = con.prepareStatement("SELECT * FROM Ticket")) 
				{
				// Ejecutamos Query
				ResultSet rs = stmt.executeQuery();
			// Recorremos el resultado
				while (rs.next())
				System.out.println(rs.getString("Owner") + rs.getString("Products"));
				} catch (SQLException sqle) 
				{
					System.out.println("Error en la ejecución:" + sqle.getErrorCode() + " " + sqle.getMessage());
				}
			}	catch	(Exception e)
			{
				System.out.println("Error en la conexión:" + e.toString());
			} finally
			{
				try 
					{
					// Cerramos posibles conexiones abiertas
					if (con!=null) con.close();    
					} catch (Exception e) 
					{
						System.out.println("Error cerrando conexiones: "+ e.toString());
					} 
			}
	 }
}