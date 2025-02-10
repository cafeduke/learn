package exec;

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
      
      int taskCount = 10;
      List<Callable<Integer>> listTask = new ArrayList<Callable<Integer>> ();
            
      for (int i = 0; i < taskCount; ++i)
         listTask.add(new FactorialTask(i+1));

      List<Future<Integer>> listResult = execService.invokeAll(listTask);
      for (Future<Integer> result : listResult)
         System.out.println(result.get());
      
      execService.shutdown();
      Util.threadTimeStampLog("Finished Fixed Thread Pool Demo");
   }
   
   class FactorialTask implements Callable<Integer>
   {
      private int n = -1;
      
      public FactorialTask (int n)
      {
         this.n = n;
      }

      @Override
      public Integer call () throws Exception
      {
         int prod = 1;
         for (int i = 2; i <= n; ++i)
            prod = prod * i;
         
         Util.sleep(2);
         return prod;
      }      
   }
}
