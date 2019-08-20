package concurrency.sync;

import java.util.concurrent.*;
import util.Util;

/**
 * This example Demos the use of latch to achieve service dependency ( Similar to target dependency in ant ). 
 * 
 * Problem Addressed
 * -----------------
 * A given service depends on many (zero or more) services to start. If there is no dependency or if all the dependent 
 * services are UP then the service can start.  
 *
 */
public class Latch_ServiceDependency
{
   public static void main (String arg[])
   {
      new Latch_ServiceDependency().demoLatchServiceDependency();
   }
   
   public void demoLatchServiceDependency ()
   {
      Service service[] = new Service[4];
      
      for (int i = 0; i < service.length; ++i)
         service[i] = new Service ("S" + i);
      
      service[0].setDependent(new Service[] {service[1], service[2]});
      service[1].setDependent(new Service[] {service[3]});
      service[2].setDependent(new Service[] {service[3]});
      
      for (int i = 0; i < service.length; ++i)
      {
         new Thread (service[i], "S" + i).start ();
         Util.sleepInMilli(100);
      }
   }

   class Service implements Runnable
   {
      private String name = null;
      
      private Service dependent[] = null;
      
      private CountDownLatch latch = new CountDownLatch(1);
      
      public Service (String name)
      {
         this (name, new Service[0]);
      }
      
      public Service (String name, Service dependent[])
      {
         this.name = name;
         this.dependent = dependent;
      }
      
      public void setDependent (Service dependent[])
      {
         this.dependent = (dependent == null) ? new Service[0] : dependent;
      }
      
      public void waitTillStart () throws InterruptedException
      {
         Util.threadLog ("Started waiting for Service[" + name + "] to start.");
         latch.await();
         Util.threadLog ("Finished waiting for Service[" + name + "] to start.");
      }
      
      @Override
      public void run ()
      {
         try
         {
            Util.threadLog("Started execution");
            for (Service currService : dependent)
               currService.waitTillStart();
            Util.threadLog("Dependent services are started. Service[" + name + "] is now UP!");
            latch.countDown();
            Util.threadLog("Finished execution");
         }
         catch (InterruptedException e)
         {
            Util.threadLog("Got Interrupted!");
         }
      }
   }
   
}

