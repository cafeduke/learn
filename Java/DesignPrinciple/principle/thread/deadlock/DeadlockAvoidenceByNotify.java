package principle.thread.deadlock;

import principle.*;
import java.util.*;

class WorkerM implements Runnable
{
   @Override
   public void run ()
   {
      Resource.acquire(null, EnumSet.of(Resource.RESOURCE_A));      
      
      Util.theadLog("Sleeping");
      Util.sleep(2);
      Util.theadLog("Woke up from sleep");
      
      Resource.acquire(EnumSet.of(Resource.RESOURCE_A), EnumSet.of(Resource.RESOURCE_B));
      Resource.release(EnumSet.of(Resource.RESOURCE_A, Resource.RESOURCE_B));
      
   }
}

class WorkerN implements Runnable
{
   @Override
   public void run ()
   {
      Resource.acquire(null, EnumSet.of(Resource.RESOURCE_B));      
      
      Util.theadLog("Sleeping");
      Util.sleep(2);
      Util.theadLog("Woke up from sleep");
      
      Resource.acquire(EnumSet.of(Resource.RESOURCE_B), EnumSet.of(Resource.RESOURCE_A));
      Resource.release(EnumSet.of(Resource.RESOURCE_B, Resource.RESOURCE_A));
   }
}
   
enum Resource
{
   RESOURCE_A, RESOURCE_B;
   
   public boolean isFree = true;
   
   Resource ()
   {
      isFree = true;
   }
   
   public synchronized static void acquire (Set<Resource> setHas, Set<Resource> setNeed)
   {
      Util.theadLog("[Begin]  Acquired " + ((setHas!=null)?setHas:"None ") + " Waiting for " + setNeed);
      while (!tryAcquire(setHas, setNeed))
      {
         try
         {
            Util.theadLog("[Waiting] Freed my resources " + setHas);
            Resource.class.wait ();
            Util.theadLog("[Notified] Got Notification!");
         }
         catch (InterruptedException e)
         {
            Util.theadLog("Interruptted !");
         }
      }
      
      Set<Resource> setSum = new HashSet<Resource>();
      if (setHas != null)
         setSum.addAll(setHas);
      setSum.addAll(setNeed);      
      Util.theadLog("[Acquired] Perform operation with resources " + setSum);
   }
   
   public synchronized static void release (Set<Resource> setHas)
   {
      for (Resource currResource : setHas)
      {
         if (currResource.isFree)
         {
            Util.theadLog("Don't have " + currResource + " to release");
            System.exit (1);
         }
         currResource.isFree = true;
      }
      Util.theadLog("[Released] Released resources " + setHas);
      Util.theadLog("[Released] Will notify all ");
      Resource.class.notifyAll();
   }
   
   private static boolean tryAcquire (Set<Resource> setHas, Set<Resource> setNeed)
   {
      boolean success = true;
      
      for (Resource currResource : setNeed)
      {
         if (!currResource.isFree)
         {
            success = false;
            break;
         }
      }
      
      if (success)
      {
         for (Resource currResource : setNeed)
            currResource.isFree = false;
      }
      else
      {
         if (setHas != null)
         {
            for (Resource currResource : setHas)
               currResource.isFree = true;
         }
      }
      
      return success;
   }
   
   public String toString ()
   {
      switch (this)
      {
         case RESOURCE_A : return "Resource-A [" + ((isFree) ? "Free" : "Taken") + "]";
         case RESOURCE_B : return "Resource-B [" + ((isFree) ? "Free" : "Taken") + "]";
         default         : throw new IllegalStateException ("Unknown Resource " + this);
      }
   }
}  

public class DeadlockAvoidenceByNotify
{ 
   public static void main (String arg[])
   {
      WorkerM workerA = new WorkerM ();
      WorkerN workerB = new WorkerN ();
      Thread tM = new Thread (workerA, "tWorkerM");
      Thread tN = new Thread (workerB, "tWorkerN");
      tM.start();
      tN.start();
   }
}
