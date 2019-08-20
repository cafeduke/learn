package principle.general;

import java.util.*;

public class UnmodifialbeCollectionPrinciple
{
   public static void main (String arg[])
   {
      List<String> list = null;
      
      /**
       * Security hack
       * -------------
       * Here we are able to modify the state of the class the private data
       * of the class ( List of names ) WITHOUT using its methods.
       * 
       * This might be highly unintended.
       */      
      UnsecureNameManager nameManager1 = new UnsecureNameManager();
      nameManager1.addName ("Raghu");
      nameManager1.addName ("Madhu");
      
      System.out.println("Unsecure Collection Operation");
      System.out.println("-----------------------------");
      System.out.println(nameManager1);
      list = nameManager1.getNames();
      if (list.size() > 0)
         list.remove(0);
      System.out.println(nameManager1);
      System.out.println();
      
      /**
       * Security fix
       * -------------
       * Here we are NOT able to modify the state of the class because
       * the class returns an unmodifiable collection :)
       */      
      SecureNameManager nameManager2 = new SecureNameManager();
      nameManager2.addName ("Raghu");
      nameManager2.addName ("Madhu");      
      
      System.out.println("Secure Collection Operation");
      System.out.println("---------------------------");      
      System.out.println(nameManager2);
      list = nameManager2.getNames();
      try
      {         
         if (list.size() > 0)
            list.remove(0);
      }
      catch (UnsupportedOperationException e)
      {
         System.out.println("UnsupportedOperationException");
      }      
      System.out.println(nameManager2);
   }
}

class UnsecureNameManager
{
   private List<String> listName = null;
   
   public UnsecureNameManager ()
   {
      listName = new ArrayList<String>();
   }
   
   public void addName (String name)
   {
      listName.add(name);
   }
   
   public List<String> getNames ()
   {
      return listName;
   }
   
   @Override
   public String toString ()
   {
      return listName.toString();
   }
}

class SecureNameManager
{
   private List<String> listName = null;
   
   public SecureNameManager ()
   {
      listName = new ArrayList<String>();
   }
   
   public void addName (String name)
   {
      listName.add(name);
   }
   
   public List<String> getNames ()
   {
      return Collections.unmodifiableList(listName);
   }
   
   @Override
   public String toString ()
   {
      return listName.toString();
   }
}
