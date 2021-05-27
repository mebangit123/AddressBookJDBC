package com.addressBook.AddressBookJDBC;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AddressBookService {
	 
	static List<Person> personContact = new ArrayList<>();
	 private  AddressBookDBService dbService;
	
	public AddressBookService() {
		dbService = AddressBookDBService.getInstance();
	}
	
	public static void main(String[] args) {
		AddressBookService service = new AddressBookService();
		service.addMultipleContact();
	}
	
	public void addNewContact()
	{
		dbService.addContact("Evan", "Sten", "Sung", "Guwahati", "Assam", 2343, 353463434, "evan@abc.x");
	}
	
	public void addMultipleContact()
	{
		
		List<Person> contactPerson = new ArrayList<>();
			contactPerson.add(new Person(0,"John", "Billy", "Sonali", "Tura", "Meghalaya", 12, 34567080, "john@123"));
			contactPerson.add(new Person(0, "Sony", "Clark", "Mesan", "Shillong", "Assam", 13, 123347890, "sony@gmail.com"));
			contactPerson.add(new Person(0, "Tony", "Sana", "Lynzam", "Shillong", "Meghalaya", 14, 123367890, "tony@outlook.com"));
			contactPerson.add(new Person(0, "Evan", "Sten", "Sung", "Guwahati", "Assam", 2343, 353463434, "evan@abc.x"));
			
			Map<Integer, Boolean> contactAdditionStatus = new HashMap<Integer, Boolean>();
			
			contactPerson.forEach(contact ->
			{
				Runnable task = () ->
				{
					contactAdditionStatus.put(contact.hashCode(), false);
					System.out.println("Contact being Added: "+Thread.currentThread().getName());
					dbService.addContact(contact.getF_name(), contact.getL_name(), contact.getAddress(), contact.getCity(), contact.getState(), contact.getZip(), contact.getPhone_no(),contact.getEmail());
					contactAdditionStatus.put(contact.hashCode(), true);
					System.out.println("Contact Added: "+Thread.currentThread().getName());
				};
				Thread thread = new Thread(task, contact.getF_name());
				thread.start();
			});
			while(contactAdditionStatus.containsValue(false))
			{
				try 
				{ 
					Thread.sleep(10);
				}catch(InterruptedException e)
				{
					e.printStackTrace();
				}
			}
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
	
	public int countNumberOfContactByCity(String city) {
		return dbService.countNumberOfContact(city);
	}
	
	public long countEntries() {
		return personContact.size();
	}
}
