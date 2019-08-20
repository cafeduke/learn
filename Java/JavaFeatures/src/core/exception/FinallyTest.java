package core.exception;

/**
 * Finally is executed after exception handling.
 *
 */
public class FinallyTest
{
   public static void fun ()
   {
      try
      {
         System.out.println("Before Exception");
         int x = 5 / 0; 
         x = x + 1;
         System.out.println("After Exception");
      }
      catch (ArithmeticException ae)
      {
         System.out.println("Divide by zero");
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
