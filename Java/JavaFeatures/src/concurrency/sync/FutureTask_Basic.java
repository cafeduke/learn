package concurrency.sync;

import java.math.BigInteger;
import java.util.concurrent.*;
import util.Util;

/**
 * Start a time-consuming task, but do not wait for it to complete.
 * Do some other work in the meantime.
 * Wait for the time-consuming task to complete and get the result.
 */
public class FutureTask_Basic
{
   public static void main (String arg[]) throws InterruptedException, ExecutionException
   {
      new FutureTask_Basic().demoFutureTask();
      
   }
   
   public void demoFutureTask () throws InterruptedException, ExecutionException
   {
      Callable<BigInteger> taskPrimeCalc = new PrimeCalculator ("7"); 
      FutureTask<BigInteger> taskResult = new FutureTask<BigInteger> (taskPrimeCalc);
      
      // Start laborious task
      // new Thread (taskResult, "tPrime").start ();
      Executors.newSingleThreadExecutor().submit(taskResult);
      
      // Do other stuff in the mean time
      Util.threadLog("First  Prime - 2");
      Util.threadLog("Second Prime - 5");
      Util.threadLog("Third  Prime - 7");
      
      // Wait for laborious task to complete
      while (!taskResult.isDone ()) 
      {
         String currTime = "[" + Util.getTimestamp() + "] ";
         Util.threadLog(currTime + "Blocking wait for laborious task to complete");
         Util.sleep(1);
      }
      
      // Get result of laborious task
      Util.threadLog("Forth  Prime - " + taskResult.get());
   }

   public class PrimeCalculator implements Callable<BigInteger>
   {
      BigInteger baseNum = null;
      
      public PrimeCalculator (String strNum)
      {
         baseNum = new BigInteger(strNum);
      }
      
      @Override
      public BigInteger call () throws Exception
      {
         BigInteger nextPrime = null;
         
         Util.threadLog("Starting laborious task");
         nextPrime = baseNum.nextProbablePrime();
         Util.sleep (5);
         Util.threadLog("Finished laborious task");
         
         return nextPrime;
      }
   }
}
