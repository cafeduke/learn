package core.thread.sync;

import java.text.SimpleDateFormat;
import java.util.Date;
import util.Util;

public class ThreadCoreWaitNotify implements Runnable
{
   public static final String THREAD_PREFIX = "t";
   
   private static final SimpleDateFormat TimeStampFormat = new SimpleDateFormat ("EEE, dd-MMM-yyyy HH:mm:ss.SSS z");
   
   private int id = 1;
   
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
         while (id <= threadCount)
         {
            synchronized (getClass())
            {              
               if (Thread.currentThread().getName().equals(THREAD_PREFIX + id))
               {
                  Util.threadLog("Hello.  Id=" + id);
                  id = id % threadCount + 1;
                  Util.threadLog("Notify. Id=" + id);
                  getClass().notifyAll();
                  break;
               }
               else
               {
                  Util.threadLog("Wait. Id=" + id);
                  getClass().wait();
                  Util.threadLog("Awake. Id=" + id);
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
