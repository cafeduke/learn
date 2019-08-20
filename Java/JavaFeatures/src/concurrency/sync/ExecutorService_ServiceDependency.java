package concurrency.sync;

import java.util.*;
import java.util.concurrent.*;

import util.Util;

public class ExecutorService_ServiceDependency
{
   public static void main (String arg[]) throws InterruptedException, ExecutionException
   {
      demoLatchServiceDependency();
   }
   
   public static void demoLatchServiceDependency () throws InterruptedException, ExecutionException
   {
      ExecutorService executor = Executors.newFixedThreadPool(3);
      
      Service service[] = new Service[4];
      
      for (int i = 0; i < service.length; ++i)
         service[i] = new Service ("S" + i, executor);
      
      service[0].setDependent(new Service[] {service[1], service[2]});
      service[1].setDependent(new Service[] {service[3]});
      service[2].setDependent(new Service[] {service[3]});
      
      Future<Void> future = executor.submit(service[0]);
      future.get();
      
      executor.shutdown();
   }   
   
   static class Service implements Callable<Void>
   {
      String name;
      
      Service dependent[] = null;
      
      ExecutorService executor;
      
      public Service (String name, ExecutorService executor)
      {
         this.name = name;
         this.executor = executor;
      }
      
      public void setDependent (Service dependent[])
      {
         this.dependent = (dependent == null) ? new Service[0] : dependent;
      }
      
      @Override
      public String toString ()
      {
         return name;
      }

      @Override
      public Void call() throws Exception
      {
         Util.threadLog("Started execution", name);
         
         List<Service> listDependent = new ArrayList<> ();
         for (Service currDependent : dependent)
            listDependent.add(currDependent);
         
         Util.threadLog("Invoke dependents. Dependents=" + listDependent, name);
         List<Future<Void>> listResult = executor.invokeAll(listDependent);
         
         Util.threadLog("Wait for completion of dependents. Dependents=" + listDependent, name);
         for (Future<Void> currResult : listResult)
            currResult.get();
         Util.threadLog("All dependents have completed execution. Dependents=" + listDependent, name);
         
         Util.threadLog("Executing current service", name);
         Util.sleepInMilli(100);
         Util.threadLog("Finished executing current service", name);
         
         return null;
      }
   }
}
