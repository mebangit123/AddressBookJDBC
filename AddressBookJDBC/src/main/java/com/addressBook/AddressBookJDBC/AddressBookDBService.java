package com.addressBook.AddressBookJDBC;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class AddressBookDBService {
	private static  AddressBookDBService addressBookDBService;
	Person person;
	
	public AddressBookDBService() {}

	public List<Person> getAllAddressBookEntries()
	{
		List<Person> personContact = new ArrayList<>();
		String sql = "Select * from contact";
		
		try(Connection conn = this.getConnection()) 
		{	
			Statement statement = conn.createStatement();
			
			ResultSet resultSet = statement.executeQuery(sql);
			
			while(resultSet.next())
			{
				int id = resultSet.getInt(1);
				String firstName = resultSet.getString(2);
				String lastName = resultSet.getString(3);
				String address = resultSet.getString(4);
				String city = resultSet.getString(5);
				String state = resultSet.getString(6);
				int zip = resultSet.getInt(7);
				int phoneNo = resultSet.getInt(8);
				String email = resultSet.getString(9);
				
				personContact.add(new Person(id, firstName, lastName, address, city, state, zip, phoneNo, email));
			}
		}  catch (SQLException e) {
			e.printStackTrace();
		}
		return personContact;
	}
	
	public int updateContact(String state, String name) {
		String sql = String.format("update contact set state = '%s' where firstName = '%s';",state, name);
		try(Connection conn = this.getConnection())
		{
			Statement statement = conn.createStatement();
			return statement.executeUpdate(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}
	
	private Connection getConnection()
	{
		String jdbcURL = "jdbc:mysql://localhost:3306/addressBook";
		String userName = "root";
		String password = "root";
		Connection conn = null;
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			conn = DriverManager.getConnection(jdbcURL, userName, password);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return conn;
	}
	
	public static AddressBookDBService getInstance() 
	{
		if(addressBookDBService == null)
			addressBookDBService = new AddressBookDBService();
		return addressBookDBService;
	}
}
