package records;


public class Manager {
	private String managerID;
	private String city;
	
	public Manager(String managerID, String city) {
		this.managerID = managerID;
		this.city = city;
	}

	public String getManagerID() {
		return managerID;
	}

	public String getCity() {
		return city;
	}
	
 	
	public String getBookedFlightCount() {
		return "";
	}
	
	
}
