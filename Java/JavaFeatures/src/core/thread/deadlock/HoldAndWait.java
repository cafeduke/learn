package core.thread.deadlock;

import util.Util;

public class HoldAndWait
{
   private static Account accRaghu = new Account("Raghu");

   private static Account accMadhu = new Account("Madhu");

   public static void main(String arg[])
   {
      new Thread(new Runnable()
         {
            public void run()
            {
               Account.transfer(accRaghu, accMadhu, 10);
            }
         }, "t1").start();

      new Thread(new Runnable()
         {
            public void run()
            {
               Account.transfer(accMadhu, accRaghu, 10);
            }
         }, "t2").start();
   }
}

class Account
{
   private String name = null;

   public Account(String name)
   {
      this.name = name;
   }

   public static void transfer(Account fromAccount, Account toAccount, int amount)
   {
      synchronized (fromAccount)
      {
         System.out.println("Got lock on " + fromAccount + ". Waiting for " + toAccount);
         Util.sleepInMilli(300);
         synchronized (toAccount)
         {
            System.out.println("Transfered " + amount + " from " + fromAccount + " to " + toAccount);
         }
      }
   }

   public String toString()
   {
      return "[Account :" + name + "]";
   }
}
