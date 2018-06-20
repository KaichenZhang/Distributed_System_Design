/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package webservice;

import javax.xml.ws.Endpoint;
import servers.NDLServer;
import servers.MTLServer;
import servers.WDCServer;

/**
 *
 * @author Administrator
 */
public class PublishWebService {
    
    public static void main(String[] args){
        Endpoint endPoint1 = Endpoint.publish("http://localhost:1050/mtlservice", new MTLServer());
        System.out.println( "MTL Server: " + endPoint1.isPublished());
        
        Endpoint endPoint2 = Endpoint.publish("http://localhost:1050/delservice", new NDLServer());
        System.out.println( "NDL Server: " + endPoint2.isPublished());
        
        Endpoint endPoint3 = Endpoint.publish("http://localhost:1050/wstservice", new WDCServer());
        System.out.println( "WDC Server: " + endPoint3.isPublished());
    }
    
}
