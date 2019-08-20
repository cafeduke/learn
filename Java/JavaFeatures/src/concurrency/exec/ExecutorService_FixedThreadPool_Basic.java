package concurrency.exec;

import java.util.concurrent.*;
import java.util.*;

import util.Util;

public class ExecutorService_FixedThreadPool_Basic
{
   private int poolSize = -1;
   
   public ExecutorService_FixedThreadPool_Basic (int poolSize)
   {
      this.poolSize = poolSize;
   }   
   
   public static void main (String arg[]) throws Exception
   {
      new ExecutorService_FixedThreadPool_Basic(10).demoFixedPoolExec();
   }
   
   public void demoFixedPoolExec () throws InterruptedException, ExecutionException
   {
      Util.threadTimeStampLog("Started Fixed Thread Pool Demo");
      ExecutorService execService = Executors.newFixedThreadPool(poolSize);
      
      int num[] = new int [] { 10, 3, 8, 5, 12};
      List<Callable<Integer>> listTask = new ArrayList<Callable<Integer>> ();
            
      for (int i = 0; i < num.length; ++i)
         listTask.add(new FactorialTask(num[i]));

      List<Future<Integer>> listResult = execService.invokeAll(listTask);
      
      Util.printHeading("Result");
      for (int i = 0; i < num.length; ++i)
         System.out.println(String.format("%d! = %d!", num[i], listResult.get(i).get()));
      
      execService.shutdown();
      Util.threadTimeStampLog("Finished Fixed Thread Pool Demo");
   }
   
   static class FactorialTask implements Callable<Integer>
   {
      private int n = -1;
      
      private static Map<Integer,Integer> mapNumFactorial = new ConcurrentHashMap<> ();
      
      public FactorialTask (int n)
      {
         this.n = n;
      }

      @Override
      public Integer call () throws Exception
      {
         if (n == 0 || n == 1)
            return 1;
         
         int prod = 1;
         for (int i = n; i >= 2; --i)
         {
            if (mapNumFactorial.containsKey(i))
            {
               Util.threadTimeStampLog(String.format("[n=%3d] Found %d! ", n, i));
               prod = prod * mapNumFactorial.get(i);
               break;
            }
            prod = prod * i;
            Util.sleepInMilli(100);
         }
         
         Util.threadTimeStampLog(String.format("[n=%3d] Adding %d! ", n, n));
         mapNumFactorial.putIfAbsent(n, prod);      
         return prod;
      }      
   }
}
