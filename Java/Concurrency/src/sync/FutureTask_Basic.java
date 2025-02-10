package sync;

import java.math.BigInteger;
import java.util.concurrent.*;
import util.Util;

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
      new Thread (taskResult).start ();
      
      Util.sleepInMilli (1000);
      Util.threadLog("First  Prime - 2");
      
      Util.sleepInMilli (1000);
      Util.threadLog("Second Prime - 5");
      
      Util.sleepInMilli (1000);
      Util.threadLog("Third  Prime - 7");
      
      Util.threadLog("Blocking wait for leborious task to complete");
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
         Util.threadLog("Starting laborious task");
         Util.sleepInMilli(5000);
         Util.threadLog("Finished laborious task");
         return baseNum.nextProbablePrime();
      }
   }
}
