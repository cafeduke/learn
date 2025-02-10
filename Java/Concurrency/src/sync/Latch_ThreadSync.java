package sync;
import java.util.concurrent.*;
import util.Util;

/**
 *<pre>
 * Latch 
 *    - Allows one or more threads to wait for an event.
 *    - A latch is like a gate which is closed until requisite threads assemble, after which the gate is open. 
 *    - Once open the latch (gate) remains open ( CANNOT be closed again ).
 *    - Essentially, The counter cannot be reset ( See CyclicBarrier for a counter that can be reset ).
 *    
 * Working   
 *    - Count down latch is initialized to a given count
 *    - An <latch>.await() call blocks the thread until the latch count decrements to zero.
 *    - The decrementing is done by other threads by invoking <latch>.countDown(). 
 *        
 * Latch Versus Barrier
 *    - Latches are for waiting for an event. 
 *    - Barriers are for waiting for all threads to reach a point.
 *    
 *    - Once the event has occurred the latch is opened for ever indicating that the event/operation is complete.
 *    - Barriers can be reset.
 *</pre>
 */

public class Latch_ThreadSync
{
   private int workerCount = -1;
   
   private CountDownLatch latchSync = null;
   
   private CountDownLatch latchCounter = null;
   
   public Latch_ThreadSync (int workerCount)
   {
      this.workerCount = workerCount;
   }
   
   public static void main (String arg[])
   {
      new Latch_ThreadSync (5).syncWorkers();
   }   
   
   public void syncWorkers ()
   {
      try
      {
         latchSync = new CountDownLatch(1);
         latchCounter = new CountDownLatch(workerCount);
         Worker worker [] = new Worker [workerCount];
         
         for (int i = 0; i < worker.length; ++i)
            new Thread (new Worker (), "tWorker" + i).start ();
         
         // Wait for all threads to reach waiting state.
         Util.sleepInMilli(100);
         
         latchSync.countDown();
         latchCounter.await ();
      }
      catch (Exception e)
      {
         Util.threadLog("Interrupted!");
      }     
   }
   
   class Worker implements Runnable
   {
      @Override
      public void run ()
      {
         try
         {
            Util.threadLog ("Preparing to fire");
            latchSync.await ();
            Util.threadLog ("Fire!");
            Util.sleepInMilli(20);
            latchCounter.countDown();
            Util.threadLog ("Finished");
         }
         catch (InterruptedException e)
         {
            Util.threadLog("Interrupted!");
         }
      }
   }   
}


