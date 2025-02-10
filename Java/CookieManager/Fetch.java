/**
 * 
 */
/**
 *
 * @author Raghunandan.Seshadri
 * @owner  Raghunandan.Seshadri
 */
import java.io.*;
import java.net.*;
import java.util.*;

public class Fetch 
{
  public static void main(String args[]) throws Exception 
  {
    URL urlX = new URL("http://adc2190940.us.oracle.com:5555/cgi-bin/session.pl");
    URL urlA = new URL("http://10.177.246.24:8080/OtdApp/HelloWorld.jsp");
    URL urlB = new URL("http://10.177.246.24:8080/OtdApp/HelloWorld.jsp");    
    
    CookieManager manager = new CookieManager ();
    manager.setCookiePolicy(CookiePolicy.ACCEPT_ALL);    
    CookieHandler.setDefault(manager);        
    
    readResponse (urlX, "Scenario 1 [Session Enabled]");
    readResponse (urlX, "Scenario 1 [Session Enabled]");

    ((CookieManager)manager.getDefault()).getCookieStore().removeAll();
    CookieHandler.setDefault(null);
    readResponse (urlA, "Scenario 2 [Session Disabled]");
    readResponse (urlB, "Scenario 2 [Session Disabled]"); 
    
    manager = new CookieManager ();
    manager.setCookiePolicy(CookiePolicy.ACCEPT_ALL);    
    CookieHandler.setDefault(manager);
    readResponse (urlA, "Scenario 3 [Session Enabled]");
    readResponse (urlB, "Scenario 3 [Session Enabled]");
  }
  
  public static void readResponse (URL url, String name)
  {
     try
     {  
        System.out.println();
        System.out.println("-----------------------------------------------");
        System.out.println(name);
        System.out.println("-----------------------------------------------");
        System.out.println();
        
        System.out.println("_____ Response Header _____");
        HttpURLConnection conn = (HttpURLConnection)url.openConnection();
        conn.connect();
        Map<String, List<String>> mapNameHeader = conn.getHeaderFields();
        System.out.println(mapNameHeader.get("Set-Cookie"));
        System.out.println();
        
        
        System.out.println("_____ Response Body _____");        
        InputStream in = conn.getInputStream();
        int currByte = -1;
        while ((currByte = in.read()) != -1)
           System.out.print(new Character((char)currByte));
        in.close();
        System.out.println();
        
        System.out.println("_____ Cookie Store _____");
        CookieManager manager = (CookieManager)CookieHandler.getDefault();
        if (manager != null)
        {           
           List<HttpCookie> cookies = manager.getCookieStore().getCookies();
           System.out.println(cookies);
        }
        System.out.println();
     }
     catch (Exception e)
     {
        e.printStackTrace();
     }
  }
}

