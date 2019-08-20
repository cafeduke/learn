package principle.exception;

public class FinallyTest
{
   public static void fun ()
   {
      try
      {
    	 System.out.println("Before exception");
         int x = 5 / 0;
         System.out.println("After exception");
      }
      catch (ArithmeticException ae)
      {
         System.out.println("Divide by zero");
         return;
         // Finally cannot execute if you exit the JVM
    	 // System.exit(0);
      }
      catch (Exception e)
      {
    	  System.out.println("Generic exception");
      }
      finally
      {
         System.out.println("Finally");
      }
   }
   
   public static void main (String arg[])
   {
      FinallyTest.fun();
   }
   
}
