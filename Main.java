import java.util.List;
import java.util.Scanner;

import com.db4o.*;

public class Main {

	//Creates a final variable FILENAME with path to file 
	final static String FILENAME =
			System.getProperty("user.home")
			+"\\[Path here]\\test.txt";
	//Uses double slashes because one is an escape sequence to indicate the following slash is part of the path
	
	public static Scanner scan = new Scanner(System.in);
	
	public static void main(String[] args) throws Throwable{
		
		ObjectContainer db = Db4oEmbedded.openFile(Db4oEmbedded.newConfiguration(), FILENAME);
		//Creates database using db4o with the name of db
		
		printMenu();
		
		boolean quit = false;
		//While loop will run until quit is set to true
		while(!quit) {
			
			int toDo = Integer.parseInt(scan.nextLine());
			//toDo refers to the user's choice selection from the menu
			
			switch(toDo) {
			
				case 0:
					//User has chosen to quit, stop the loop
					quit = true;
					break;
					
				case 1:
					//User has chosen to add a contact
					System.out.println("Type in contact's name first, then birthday (MM/DD/YYYY) separated by a comma...");
					String userTyped = scan.nextLine();
					String[] contactInfo = userTyped.split(",");
					/*Creates an array of strings based on user input. Two elements in the array are populated:
					 *String[0] will hold the the name of the contact
					 *String[1] will hold the birthday of the contact
					 *The comma entered by the user is ignored (the String was split at the comma)
					 */
					
					contactInfo[1] = contactInfo[1].replaceAll("\\s", "");
					//Takes away all spaces from birthday so formatting is correct when displaying info
					
					Contact newContact = new Contact(contactInfo[0], contactInfo[1]);
					//Creates new contact with info inputted by user and adds it to the database
					db.store(newContact);
					System.out.println("Successfully stored " + newContact);
			
					System.out.println("----------------------------");
					break;
					
				case 2:
					//User has chosen to remove a contact
					System.out.println("Enter the name of the contact to be deleted");
					String toDelete = scan.nextLine();
					Contact rmvContact = new Contact(toDelete, null); //Sets a contact with name inputted by user (ignoring the actual birthday)
					//rmvContact isn't added into the database. It's just a reference.
					ObjectSet result = db.queryByExample(rmvContact); //Finds contact in the database
					db.delete(result.next()); //Removes contact from the database with name inputted
					break;
					
				case 3:
					//User has chosen to display all contacts in the database
					List <Contact> contacts = db.query(Contact.class); //Creates list of all elements in database
					if(!contacts.isEmpty()) {
						//Prints all elements in database if elements have been found
						System.out.println(contacts);
						System.out.println("----------------------------");
					}else {
						System.out.println("No contacts have been found...");
					}
					break;
					
				case 4:
					//User has chosen to delete all the data
					System.out.println("Are you SURE you would like to delete ALL contacts? This is irreverisble (Enter yes or no)");
					String confirmation = scan.nextLine();
					if(confirmation.toLowerCase() == "yes") {
						ObjectSet fullResult = db.queryByExample(Contact.class); //Retrieves all elements in database
						while(fullResult.hasNext()) {
							Contact toFullyDelete = (Contact)fullResult.next();
							db.delete(toFullyDelete);
							//Deletes each element
						}
					}else if(confirmation.toLowerCase() == "no") {
						System.out.println("No contacts deleted");
					}else {
						System.out.println("You entered an invalid input. Make sure to type either \"yes\" or \"no\"");
					}
					System.out.println("----------------------------");
					
				case 5:
					//User has chosen to re-print menu
					printMenu();
					break;
					
				default:
					System.out.println("An error occurred. You most likely inputted a number not available. Please try again...");
			}
			
		}
		
	}

	//Menu with options for user
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
