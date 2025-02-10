import java.io.InputStream;
import java.net.CookieHandler;
import java.net.CookieManager;
import java.net.CookiePolicy;
import java.net.HttpCookie;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Map;

/**
 * @author Raghunandan.Seshadri
 */
public class MyCookieTest
{
   
   public static void main (String args[]) throws Exception 
   {
     System.out.println("Java = " + System.getProperty("java.version"));     
     
     URL url = new URL ("http://rbseshad.idc.oracle.com:8888/OtdApp/HelloWorld.jsp");    
    
     /* Set Cookie Manager */
     CookieManager manager = new CookieManager ();
     manager.setCookiePolicy(CookiePolicy.ACCEPT_ALL);    
     CookieHandler.setDefault(manager);        

     /* First request with cookie manager set */   
     readResponse (url, "[Cookie Manager Set] Request-A");
     
     /* Second request with cookie manager set */
     readResponse (url, "[Cookie Manager Set] Request-B");
   }   

   /**
    * Read from <b>url</b> and print the response headers and body.
    * Also print the contents of cookie store.
    *  
    * @param url  URL to read from.
    * @param title Title to be printed.
    */
   public static void readResponse (URL url, String title)
   {
      try
      {  
         System.out.println();
         System.out.println("-----------------------------------------------");
         System.out.println(title);
         System.out.println("-----------------------------------------------");
         System.out.println();
         
         System.out.println("_____ Response Headers _____");
         HttpURLConnection conn = (HttpURLConnection)url.openConnection();
         conn.connect();
         
         Map<String, List<String>> mapNameHeader = conn.getHeaderFields();
         for (String currHeader : mapNameHeader.keySet())
         System.out.println(currHeader + ": " + mapNameHeader.get(currHeader));
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
