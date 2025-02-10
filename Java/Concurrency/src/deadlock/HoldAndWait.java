package deadlock;
import util.Util;

public class HoldAndWait 
{
   private static Account fromAccount = new Account ("Raghu");
   
   private static Account toAccount   = new Account ("Madhu");
   
   public static void main (String arg[])
   {
      new Thread (new Runnable () {  
                      public void run ()
                      {
                         Account.transfer(fromAccount, toAccount, 10);
                      }
                  }, "t1").start();
      
      new Thread (new Runnable () {  
         public void run ()
         {
            Account.transfer(toAccount, fromAccount, 10);
         }
     }, "t2").start();
   }
}

class Account
{
   private String name = null;
   
   public Account (String name)
   {
      this.name = name;
   }
   
   public static void transfer (Account fromAccount, Account toAccount, int amount)
   {
      synchronized (fromAccount)
      {
         System.out.println("Got lock on " + fromAccount + ". Waiting for " + toAccount);
         Util.sleepInMilli(300);
         synchronized (toAccount)
         {
            System.out.println("Transfered " + amount +" from " + fromAccount + " to " + toAccount);
         }
      }
   }
   
   public String toString ()
   {
      return "[Account :" + name + "]";
   }
}