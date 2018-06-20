package clients;

import java.util.HashMap;

public class ServerInfo {
	
	
	private static HashMap<String, Integer> serverMaps;
    static {
    	serverMaps = new HashMap<>();
        serverMaps.put("Montreal", 2000);
        serverMaps.put("Washington", 2001);
        serverMaps.put("NewDelhi", 2002);
    }
    

	public static HashMap<String, Integer> getServerMaps() {
		// TODO Auto-generated method stub
		return serverMaps;
	}
    
    
	
}
