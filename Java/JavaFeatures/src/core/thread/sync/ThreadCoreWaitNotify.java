package core.thread.sync;

import java.text.SimpleDateFormat;
import java.util.Date;
import util.Util;

public class ThreadCoreWaitNotify implements Runnable
{
   public static final String THREAD_PREFIX = "t";
   
   private static final SimpleDateFormat TimeStampFormat = new SimpleDateFormat ("EEE, dd-MMM-yyyy HH:mm:ss.SSS z");
   
   private int chosenId = 1;
   
   private int threadCount = 3;
   
   public static void main (String arg[]) throws Exception
   {
      ThreadCoreWaitNotify obj = new ThreadCoreWaitNotify();
      Thread t1 = new Thread (obj, "t1");
      Thread t2 = new Thread (obj, "t2");
      Thread t3 = new Thread (obj, "t3");
      
      t1.start();
      Util.sleepInMilli(100);      
      t3.start();
      Util.sleepInMilli(10);
      t2.start();
      
   }
   
   @Override
   public void run () 
   {
      try
      {
         while (chosenId <= threadCount)
         {
            synchronized (getClass())
            {   
               // Check if the current thread's name matches the chosen thread
               if (Thread.currentThread().getName().equals(THREAD_PREFIX + chosenId))
               {
                  // Increment to next thread
                  Util.threadLog("Hello.  Id=" + chosenId);
                  chosenId = chosenId % threadCount + 1;
                  
                  // Notify all waiting threads.
                  Util.threadLog("Notify. Id=" + chosenId);
                  getClass().notifyAll();
                  break;
               }
               else
               {
                  Util.threadLog("Wait. Id=" + chosenId);
                  getClass().wait();
                  Util.threadLog("Awake. Id=" + chosenId);
               }
            }
         }
      }
      catch (InterruptedException e)
      {
         e.printStackTrace();
      }
   }

}
