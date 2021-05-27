package com.addressBook.AddressBookJDBC;

import java.util.ArrayList;
import java.util.List;

public class AddressBookService {
	 
	static List<Person> personContact = new ArrayList<>();
	 private  AddressBookDBService dbService;
	
	public AddressBookService() {
		dbService = AddressBookDBService.getInstance();
	}
	
	public void getAllPersonContactFromDB()
	{
		personContact = dbService.getAllAddressBookEntries();	
	}

	public void updateAddressBookContactInformation()
	{
		int result = dbService.updateContact("Assam","Sony");
		if(result == 1)
			System.out.println("updated Succesfully");
	}
	public long countEntries() {
		return personContact.size();
	}
}
