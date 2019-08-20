package principle.finalize;

/**
 * Exception thrown in finalize is NOT caught
 * ------------------------------------------
 *
 */
public class TestFinalizeException
{
   public TestFinalizeException ()
   {
      System.out.println("Default Constructor");
   }
   
   @Override
   protected void finalize() 
   {
      System.out.println("Finalize shall divide by zero");
      int x = 1 / 0;
   }

   public static void main (String arg[]) 
   {
      try
      {
         TestFinalizeException f1 = new TestFinalizeException ();
         f1 = null;
         System.gc();
         Thread.sleep(2000);
         System.out.println ("Divide by zero NOT caught");
      }
      catch (Exception e)
      {
         System.out.println ("Divide by zero caught");
      }
   }
}

