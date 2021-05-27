package com.addressBook.AddressBookJDBC;

import org.junit.Assert;
import org.junit.Test;

public class AddressbookTest {
	
	@Test
	public void whenAllPersonContactRetrievedFromDB_ShouldMatchEntries()
	{
		AddressBookService service = new AddressBookService();
		service.getAllPersonContactFromDB();
		long entries = service.countEntries();
		Assert.assertEquals(3, entries);
	}
	
	@Test
	public void countNumberOfContactByCityOrState()
	{
		AddressBookService service = new AddressBookService();
		int numOfContact = service.countNumberOfContactByCity("Shillong");
		Assert.assertEquals(2, numOfContact);
	}
}
