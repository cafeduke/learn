package core.object.finalize;

import java.io.*;

/**
 * ResourceManager should not rely on finalize - An explicit terminator is a MUST. 
 * ----------------------------------------------------------------------------------
 *  - Finalize method is called when the object is garbage collected. 
 *  
 *  - There could be considerable delay b/w when the object is marked for 
 *    garbage collection (reference is made null) and when the object is garbage collected.
 *    
 *  - If the resource is crucial then the delay can in result in performance issues. 
 *    Furthermore, the behavior will be dependent on JVM implementation causing portability issues.
 *  
 *  - There is a severe performance penalty for using finalize(). So AVOID using it.   
 * 
 * ResourceManager MUST maintain the state of the object
 * -----------------------------------------------------
 * ResourceMananger must maintain the state of the object. This is to throw an exception if any other method
 * is called after calling the "explicit terminator"
 * 
 * 
 * What does finalize do ?
 * ------------------------
 * Finalize just acts as a backup in the user forgets to call the "explicit terminator" method.
 * However, the Resource Manager MUST log an error to indicate that not calling the explicit 
 * terminator is actually a bug in the client's code.
 */
public class ResourceManagePrinciple
{
   private PrintWriter out = null;
   
   /**
    * If true, the object is alive and explicit terminator is not yet called.
    */
   private boolean isAlive = true;
   
   public ResourceManagePrinciple () throws IOException
   {
      System.out.println("Acquire resource");
      out = new PrintWriter (new FileWriter ("CriticalResource.txt"));
   }
   
   public void action ()
   {
      if (!isAlive)
         throw new IllegalStateException ("Explicit terminator has been called. Object is now unusable.");
      System.out.println("Perform action");
   }
   
   /**
    * Explicit terminator method. 
    */
   public void close () throws IOException
   {
      System.out.println("Release resource");
      isAlive = false;
      out.close();
   }
   
   @Override
   /**
    * NOTE: Need to think if this additional protection is worth the performance degradation.
    */
   protected void finalize() throws Throwable
   {
      if (isAlive)
      {
         System.out.println("WARNING: Use explicit terminator 'close()' after using the object.");
         close ();
      }
   }

   public static void main (String arg[]) throws Exception
   {
      ResourceManagePrinciple manager = null;

      System.out.println();
      System.out.println("Generic Usage");
      System.out.println("--------------------------");
      manager = new ResourceManagePrinciple();
      manager.action();
      manager.close();
      
      try
      {
         System.out.println();
         System.out.println("Use object after close");
         System.out.println("--------------------------");
         manager = new ResourceManagePrinciple();
         manager.close ();
         manager.action ();
      }
      catch (Exception e) 
      { 
         System.out.println("ERROR: " + e.getMessage()); 
      }
      
      System.out.println();
      System.out.println("Don't close, call GC");
      System.out.println("--------------------------");
      manager = new ResourceManagePrinciple();      
      manager.action ();
      manager = null;
      System.gc();
      Thread.sleep(2000);
   }
}








