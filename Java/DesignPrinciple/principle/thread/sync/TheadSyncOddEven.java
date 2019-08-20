package principle.thread.sync;

import principle.Util;
/**
 * Demonstrates synchronization using synchronizer - SyncObject 
 */
class OddPrinter implements Runnable
{
   Object syncObject = null;
   
   public OddPrinter (Object syncObject)
   {
      this.syncObject = syncObject;
   }

   @Override
   public void run() 
   {
      for (int i = 1; i <= 10; i += 2)
      {
         synchronized (syncObject)
         {
            try
            {
               Util.theadLog ("Num = " + i);   
               
               Util.theadLog("Notify All");
               syncObject.notifyAll();  
               
               Util.theadLog("Waiting");
               syncObject.wait();
               Util.theadLog("Out of wait");         
            }
            catch (InterruptedException e)
            {
               e.printStackTrace();
            }            
         }
      }
      
      synchronized (syncObject)
      {
         Util.theadLog("Final Notify");
         syncObject.notifyAll();
      }
      
   }
}

class EvenPrinter implements Runnable
{
   Object syncObject = null;
   
   public EvenPrinter (Object syncObject)
   {
      this.syncObject = syncObject;
   }

   @Override
   public void run()
   {
      for (int i = 2; i <= 10; i += 2)
      {
         synchronized (syncObject)
         {            
            try
            {
               Util.theadLog ("Num = " + i);
               
               Util.theadLog ("Notify All");
               syncObject.notifyAll();
               
               Util.theadLog("Waiting");               
               syncObject.wait();
               Util.theadLog("Out of wait");
               
               
            }
            catch (InterruptedException e)
            {
               e.printStackTrace();
            }
         }
      }
      
      synchronized (syncObject)
      {
         Util.theadLog("Final Notify");
         syncObject.notifyAll();
      }      
   }
}

public class TheadSyncOddEven
{
   public static void main (String arg[])
   {
      String syncObject = new String ("Sync Object");
      OddPrinter oddPrinter = new OddPrinter(syncObject);
      EvenPrinter evenPrinter = new EvenPrinter (syncObject);
      Thread tOdd  = new Thread (oddPrinter, "tOdd");
      Thread tEven = new Thread (evenPrinter, "tEven");
      tOdd.start();
      tEven.start();
   }
}
