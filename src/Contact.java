
public class Contact {

	private String name;
	private String birthday;
	
	public Contact(String name, String birthday){
		this.name = name;
		this.birthday = birthday;
	}
	
	//Getters and setters for the Contact class for testing (not actually used)
	public String getName(){
		return name;
	}
	
	public void setName(String name){
		this.name = name;
	}
	
	public String getBirthday(){
		return birthday;
	}
	
	public void setBirthday(String birthday){
		this.birthday = birthday;
	}
	
	/*Override java.lang.Object's toString method so when dispalying info about a contact, 
	the actual info is shown instead of the memory address*/
	@Override
	 public String toString() {
		 return name+":"+ birthday;
	}
	
}
