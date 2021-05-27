package com.addressBook.AddressBookJDBC;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class AddressBookDBService {
	private static  AddressBookDBService addressBookDBService;
	Person person;
	
	public AddressBookDBService() {}

	public void addContact(String firstName, String lastName, String address, String city, String state, int zip, int phoneNo, String email) 
	{
		String sql = "insert into contact (firstName, lastName, address, city, state, zip, phoneNo, email) values(?,?,?,?,?,?,?,?)";
		try(Connection conn = this.getConnection())
		{
			PreparedStatement pstm = conn.prepareStatement(sql);
			pstm.setString(1, firstName);
			pstm.setString(2, lastName);
			pstm.setString(3, address);
			pstm.setString(4, city);
			pstm.setString(5, state);
			pstm.setInt(6, zip);
			pstm.setInt(7, phoneNo);
			pstm.setString(8, email);
			
			pstm.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
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
	
	public int countNumberOfContact(String city) {
		int count = 0;
		String sql = String.format( "select count(*) from contact where city = '%s';", city );
		try(Connection conn = this.getConnection())
		{
			Statement statement = conn.createStatement();
			ResultSet resultSet = statement.executeQuery(sql);
			while(resultSet.next())
			{
				count = resultSet.getInt("count(*)");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return count;
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
