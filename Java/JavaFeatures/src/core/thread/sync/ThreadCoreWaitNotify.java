package core.thread.sync;

import java.text.SimpleDateFormat;
import java.util.Date;
import util.Util;

public class ThreadCoreWaitNotify implements Runnable
{
   public static final String THREAD_PREFIX = "t";
   
   public static final int MAX_REPEAT_COUNT = 2;
   
   private int chosenId = 1;
   
   private int threadCount = 3;
   
   private int repeatCount = 0;
   
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
         while (repeatCount < MAX_REPEAT_COUNT)
         {
            synchronized (getClass())
            {   
               // Check if the current thread's name matches the chosen thread
               if (Thread.currentThread().getName().equals(THREAD_PREFIX + chosenId))
               {
                  // Increment to next thread
                  Util.threadLog("Hello", "Id=" + chosenId, "RepeatCount=" + repeatCount);
                  if (chosenId % threadCount == 0)
                     repeatCount++;
                  chosenId = chosenId % threadCount + 1;
                  
                  // Notify all waiting threads.
                  Util.threadLog("Notify", "Id=" + chosenId, "RepeatCount=" + repeatCount);
                  getClass().notifyAll();
               }
               else
               {
                  Util.threadLog("Wait", "Id=" + chosenId, "RepeatCount=" + repeatCount);
                  getClass().wait();
                  Util.threadLog("Awake", "Id=" + chosenId, "RepeatCount=" + repeatCount);
               }
            }
         }
         Util.threadLog("Done", "Id=" + chosenId, "RepeatCount=" + repeatCount);
      }
      catch (InterruptedException e)
      {
         e.printStackTrace();
      }
   }

}
