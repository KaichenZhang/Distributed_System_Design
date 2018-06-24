package Records;

import java.util.LinkedList;

public class PassengerList {
	private char key;
	private LinkedList<Passenger> value;
	
	public PassengerList(char k){
		key = k;
		value = new LinkedList<Passenger>();
	}
	
	public void addValueToList(Passenger i){
		value.add(new Passenger(i));
	}
	
	public char getKey() {
		return key;
	}
	public void setKey(char key) {
		this.key = key;
	}
	public LinkedList<Passenger> getValue() {
		return value;
	}
	public void setValue(LinkedList<Passenger> value) {
		this.value = value;
	}
}
