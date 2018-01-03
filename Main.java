import java.util.List;
import java.util.Scanner;

import com.db4o.*;
import com.db4o.foundation.Iterators;

public class Main {

	final static String DB4OFILENAME =
			System.getProperty("user.home")
			+"\\Documents\\Computer Programming and Science\\Databases for db4o\\test.txt";
	
	public static Scanner scan = new Scanner(System.in);
	
	public static void main(String[] args) throws Throwable{
		
		ObjectContainer db = Db4oEmbedded.openFile(Db4oEmbedded.newConfiguration(), DB4OFILENAME);
		
		printMenu();
		
		boolean quit = false;
		while(!quit) {
			
			int toDo = Integer.parseInt(scan.nextLine());
			
			switch(toDo) {
			
				case 0:
					quit = true;
					break;
					
				case 1:
					System.out.println("Type in contact's name first, then birthday (MM/DD/YYYY) separated by a comma...");
					String userTyped = scan.nextLine();
					String[] contactInfo = userTyped.split(",");
					contactInfo[1].replaceAll(" ", ".");
					
					Contact newContact = new Contact(contactInfo[0], contactInfo[1]);
					db.store(newContact);
					System.out.println("Successfully stored " + newContact);
			
					System.out.println("----------------------------");
					break;
					
				case 2:
					System.out.println("Enter the name of the contact to be deleted");
					String toDelete = scan.nextLine();
					Contact rmvContact = new Contact(toDelete, null);
					ObjectSet result = db.queryByExample(rmvContact);
					db.delete(result.next());
					break;
					
				case 3:
					List <Contact> contacts = db.query(Contact.class);
					System.out.println(contacts);
					System.out.println("----------------------------");
					break;
					
				case 4:
					System.out.println("Are you SURE you would like to delete ALL contacts? This is irreverisble (Enter yes or no)");
					String confirmation = scan.nextLine();
					if(confirmation.toLowerCase() == "yes") {
						ObjectSet fullResult = db.queryByExample(Contact.class);
						while(fullResult.hasNext()) {
							Contact toFullyDelete = (Contact)fullResult.next();
							db.delete(toFullyDelete);
						}
					}else if(confirmation.toLowerCase() == "no") {
						System.out.println("No contacts deleted");
					}else {
						System.out.println("You entered an invalid input. Make sure to type either \"yes\" or \"no\"");
					}
					System.out.println("----------------------------");
					
				case 5:
					printMenu();
					break;
					
				default:
					System.out.println("An error occurred. You most likely inputted a number not available. Please try again...");
			}
			
		}
		
	}

	public static void printMenu() {
		System.out.println("Choose:"
						 + "\n 0: to quit "
						 + "\n 1: to add a contact and birthday "
						 + "\n 2: to remove a contact and birthday"
						 + "\n 3: print list of contact names and birthdays"
						 + "\n 4: to delete ALL contacts"
						 + "\n 5: to print menu again");
	}
	
}
