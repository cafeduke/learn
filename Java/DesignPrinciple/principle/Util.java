package principle;

public class Util
{
   /**
    * Log message <b>mesg</b> along with thread details.
    * @param mesg Message to be logged.
    */
   public static void theadLog (String mesg)
   {
      String tname = Thread.currentThread().getName();
      
      System.out.println(String.format("[%-5s] %s", tname, mesg));
   }
   
   public static void sleep (int sec)
   {
      try
      {
         Thread.sleep(sec * 1000);
      }
      catch (InterruptedException ie)
      {
         ie.printStackTrace();
      }
   }

}
