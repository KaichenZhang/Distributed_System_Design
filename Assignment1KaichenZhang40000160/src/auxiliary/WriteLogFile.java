package auxiliary;

import java.io.FileWriter;

public class WriteLogFile {
	private String time;
	private String operator;
	private String operation;
	
	
	public WriteLogFile(String time, String who, String operation) {
		this.operator = who;
		this.time = time;
		this.operation = operation;
	}
	
	
	public void writeToLog(String fileName) {
		 try {
	         	FileWriter fw = new FileWriter(fileName, true);
	         	fw.write(this + "\r\n");	         
	            fw.close();
	     } catch (Exception e) {
	    	 e.getMessage();
	     }
	}


	@Override
	public String toString() {
		return "[" + this.time + "] " + operator + " " + operation + ".";
	}
	
	
	
}
