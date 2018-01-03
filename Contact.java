
public class Contact {

	private String name;
	private String birthday;
	
	public Contact(String name, String birthday){
		this.name = name;
		this.birthday = birthday;
	}
	
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
	
	@Override
	 public String toString() {
		 return name+":"+ birthday;
	}
	
}
