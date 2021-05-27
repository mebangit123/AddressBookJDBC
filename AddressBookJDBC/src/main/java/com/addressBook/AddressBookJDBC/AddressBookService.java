package com.addressBook.AddressBookJDBC;

import java.util.ArrayList;
import java.util.List;

public class AddressBookService {
	 static List<Person> personContact = new ArrayList<>();
	
//	public static void main(String[] args) {
//		getAllPersonContactFromDB();
//		personContact.forEach(System.out::println);
//	}
	
	public void getAllPersonContactFromDB()
	{
		AddressBookDBService dbService = new AddressBookDBService();
		personContact = dbService.getAllAddressBookEntries();	
	}

	public long countEntries() {
		return personContact.size();
	}
}
