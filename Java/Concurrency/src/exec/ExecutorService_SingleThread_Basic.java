package exec;

import java.math.BigInteger;
import java.util.concurrent.*;
import util.Util;

public class ExecutorService_SingleThread_Basic implements Callable<String>, Runnable
{
   private BigInteger prime = new BigInteger ("2");
   
   public static void main (String arg[]) 
   {
      ExecutorService_SingleThread_Basic singleThreadExec = new ExecutorService_SingleThread_Basic();
      singleThreadExec.demoExecutorUsingCallable();
      singleThreadExec.demoExecutorUsingRunnable();
   }
   
   public void demoExecutorUsingCallable () 
   {
      Util.printHeading("Executor Using Callable");
      
      // Create an executor service
      ExecutorService service = Executors.newSingleThreadExecutor();
      
      // Create a Callable task
      Callable<String> task = new ExecutorService_SingleThread_Basic();
      
      try
      {
         for (int i = 0; i < 5; ++i)
         {
            // Submit task to get the result (Future type object)
            Future<String> taskResult = service.submit(task);
            
            // Wait for the task to complete
            System.out.println("Task completion result = " + taskResult.get());
         }
      }
      catch (Exception e)
      {
         System.out.println("Exception during task execution " + e);
      }
      
      // Shutdown the service
      service.shutdown();
   }
   
   public void demoExecutorUsingRunnable ()
   {
      Util.printHeading("Executor Using Runnable");
      
      // Create an executor service
      ExecutorService service = Executors.newSingleThreadExecutor();
      
      // Create a Runnable task
      Runnable task = new ExecutorService_SingleThread_Basic();
      
      for (int i = 0; i < 5; ++i)
      {
         // Execute the task
         service.execute(task);
      }
      
      // Shutdown the service
      service.shutdown();
   }

   @Override
   public String call() throws Exception
   {
      synchronized (prime)
      {
         prime = prime.nextProbablePrime();
      }
      return prime.toString();
   }

   @Override
   public void run()
   {
      String strPrime = null;
      synchronized (prime)
      {
         prime = prime.nextProbablePrime();
         strPrime = prime.toString();
      }
      System.out.println("Task completion result = " + strPrime);
   }
}
