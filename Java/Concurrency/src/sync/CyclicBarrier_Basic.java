package sync;

import java.util.concurrent.CyclicBarrier;

import util.Util;

/**
 * CyclicBarrier
 *    - Barriers are used to enable ALL threads to reach a point before proceeding further.
 *    - A cyclic barrier is called so because it can be reset. So, there can be several such all-hands-thread meetings.  
 * 
 * Working
 *    - Lets say a cyclic barrier is created for N threads.
 *    - An <barrier>.await() blocks until all threads reach this point.
 *    - Once the last thread (Nth thread) executes <barrier>.await() all threads are released.
 *    - <barrier>.await() returns the arrival index ( once all threads are released ). 
 *       - The first thread to execute <barrier>.await() returns (N - 1);
 *       - The second thread to execute <barrier>.await() returns (N - 2);
 *       - The last thread to execute  <barrier>.await() returns 0;
 * 
 * Latch Versus Barrier
 *    - Latches are for waiting for an event. 
 *    - Barriers are for waiting for all threads to reach a point.
 *    
 *    - Once the event has occurred the latch is opened for ever indicating that the event/operation is complete.
 *    - Barriers can be reset.
 */
public class CyclicBarrier_Basic
{
   private CyclicBarrier barrier = null;
   
   private int workerCount = -1;
   
   public CyclicBarrier_Basic (int count)
   {
      workerCount = count;
      barrier = new CyclicBarrier(workerCount, new BarrierActionImpl());
   }
   
   public void syncWorkers ()
   {
      for (int i = 0; i < workerCount; ++i)
         new Thread (new Worker(), "t" + i).start ();
   }
   
   public static void main (String arg[])
   {
      CyclicBarrier_Basic barrierThreadSync = new CyclicBarrier_Basic (5);
      barrierThreadSync.syncWorkers();
   }
   
   class Worker implements Runnable
   {
      public void run ()
      {
         try
         {
            Util.threadLog("Before sync");
            int arrivalIndex = barrier.await();
            
            if (arrivalIndex == workerCount - 1)
               Util.threadLog("This is the first thread");
            else if (arrivalIndex == 0)
               Util.threadLog("This is the last thread");
            
         }
         catch (Exception e)
         {
            Util.threadLog("Interuptted!");
         }
      }
   }
   
   class BarrierActionImpl implements Runnable
   {
      @Override
      public void run()
      {
         Util.threadLog("Barrier Action");
      }
   }
}



