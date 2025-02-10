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

public class CustomStore 
{
  public static void main(String args[]) throws Exception 
  {
    URL urlA = new URL("http://10.177.246.24:8080/OtdApp/HelloWorld.jsp");
    URL urlB = new URL("http://10.177.246.24:8080/OtdApp/HelloWorld.jsp");    
    
    CookieManager manager = new CookieManager (new MyCookieStore(), CookiePolicy.ACCEPT_ALL);
    CookieHandler.setDefault(manager);        
    
    readResponse (urlA, "Scenario 1 [Session Enabled]");
    readResponse (urlB, "Scenario 1 [Session Enabled]");

    manager = null;
    CookieHandler.setDefault(null);
    readResponse (urlA, "Scenario 2 [Session Disabled]");
    readResponse (urlB, "Scenario 2 [Session Disabled]"); 
    
    // MyCookieStore myStore = new MyCookieStore();
    // CookieManager manager = new CookieManager(myStore, CookiePolicy.ACCEPT_ALL);
    
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

class MyCookieStore implements CookieStore
{
   private Map<URI,List<HttpCookie>> mapURICookie = new Hashtable<URI,List<HttpCookie>> ();
   
   public MyCookieStore ()
   {
      
   }
   
   @Override
   public void add (URI uri, HttpCookie cookie)
   {
      List<HttpCookie> listCookie = (mapURICookie.containsKey(uri)) ? mapURICookie.get(uri) : new ArrayList<HttpCookie> ();
      listCookie.add (cookie);
      System.out.println("[MyCookieStore] Add URI=" + uri + " Cookie=" + cookie);
      System.out.println("[MyCookieStore] Add Map=" + mapURICookie);
   }

   @Override
   public List<HttpCookie> get (URI uri)
   {
      if (!mapURICookie.containsKey(uri))
         mapURICookie.put(uri, new ArrayList<HttpCookie> ());
         
      System.out.println("[MyCookieStore] Get. URI=" + uri + " Value=" + mapURICookie.get(uri));
      return mapURICookie.get(uri);
   }

   @Override
   public List<HttpCookie> getCookies()
   {
      List<HttpCookie>  listMerge = new ArrayList<HttpCookie> ();
      for (List<HttpCookie> listCurr : mapURICookie.values())
            listMerge.addAll(listCurr);
            
      System.out.println("[MyCookieStore] Get Cookies . Map=" + listMerge);      
      return listMerge;
   }

   @Override
   public List<URI> getURIs()
   {
      List<URI> list = new ArrayList<URI> ();
      list.addAll(mapURICookie.keySet());
      
      System.out.println("[MyCookieStore] Get URI . Map=" + list);
      return list;
   }

   @Override
   public boolean remove(URI uri, HttpCookie cookie)
   {
      System.out.println("[MyCookieStore] Remove URI" + uri + " Cookie=" + cookie);
      return false;
   }

   @Override
   public boolean removeAll()
   {
      System.out.println("[MyCookieStore] Remove All");
      return false;
   }
   
}

