package exec.schedule;

import java.util.concurrent.*;
import util.Util;
import exec.schedule.ScheduleUtil.*;

public class ScheduledExecutor_Basic
{
   public static void main (String arg[]) throws InterruptedException 
   {
      ScheduledExecutor_Basic executor = new ScheduledExecutor_Basic ();
      executor.demoScheduledExecWithExceptionTasks();
      executor.demoScheduledExecWithTaskPileUp ();
   }
   
   public void demoScheduledExecWithExceptionTasks () throws InterruptedException 
   {
      Util.printHeading ("ScheduledExecutor Works With Tasks Throwing Excpetion");
      ScheduledExecutorService service = Executors.newScheduledThreadPool (10);
      ScheduledFuture<?> taskResultA = service.schedule(new SuccessTask (), 1, TimeUnit.SECONDS);
      ScheduledFuture<?> taskResultB = service.schedule(new FailureTask (), 2, TimeUnit.SECONDS);
      ScheduledFuture<?> taskResultC = service.schedule(new SuccessTask (), 3, TimeUnit.SECONDS);
      service.awaitTermination(5, TimeUnit.SECONDS);
      service.shutdown();      
      
      displayTaskResult (taskResultA);
      displayTaskResult (taskResultB);
      displayTaskResult (taskResultC);
   }
   
   public void demoScheduledExecWithTaskPileUp () throws InterruptedException
   {
      Util.printHeading ("ScheduledExecutor Works With Lengthy Tasks");
      ScheduledExecutorService service = Executors.newScheduledThreadPool (10);
      ScheduledFuture<?> taskResultA = service.schedule (new DelayTask ("TaskA", 5000), 1, TimeUnit.SECONDS);
      ScheduledFuture<?> taskResultB = service.schedule (new DelayTask ("TaskB", 5000), 2, TimeUnit.SECONDS);
      ScheduledFuture<?> taskResultC = service.schedule (new DelayTask ("TaskC", 5000), 3, TimeUnit.SECONDS);
      service.awaitTermination(10, TimeUnit.SECONDS);
      service.shutdown(); 
      
      displayTaskResult (taskResultA);
      displayTaskResult (taskResultB);
      displayTaskResult (taskResultC);      
   }   
   
   private void displayTaskResult (ScheduledFuture<?> taskResult)
   {
      try
      {
         System.out.println("TaskResult=" + taskResult.get());
      }
      catch (ExecutionException e)
      {
         System.out.println("TaskResult=" + e);
      }
      catch (InterruptedException ie)
      {
         System.out.println("Interruped while getting task result!");
      }
   }
}
