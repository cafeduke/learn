package sync;

import java.util.concurrent.*;

import util.Util;

public class Semaphore_ResourceManagement
{
   public static void main (String arg[])
   {
      demoReleaseBeforeWaitPattern ();
   }
   
   /**
    * <pre>
    *    Resource  Availability
    *    ----------------------
    *    resourceA      5
    *    resourceB      5
    *    ----------------------
    * </pre>
    * 
    * <ul>
    *    <li> TaskA starts first followed by TaskB and TaskC
    *    <li> TaskA acquires ResourceA=1 and ResourceB=3
    *    <li> TaskB can acquire ResourceA=4 but cannot have ResourceB=3
    *    <li> TaskB does not hold and wait. It releases ResourceA, goes to sleep and tries competing again.
    *    <li> Meanwhile, TaskC begins and acquires ResourceA=4 and ResourceB=2
    *    <li> Had TaskB not released its acquired resources, neither would not be able to execute
    *    <li> TaskC completes and releases resources
    *    <li> TaskA completes and releases resources
    *    <li> TaskB can now execute
    * </ul> 
    */
   public static void demoReleaseBeforeWaitPattern ()
   {
      ResourceAManager resourceA = new ResourceAManager (5);
      ResourceBManager resourceB = new ResourceBManager (5);
      
      Task taskA = new Task ("TaskA", new ResourceManager[] {resourceA, resourceB}, new int[] {1, 3});
      Task taskB = new Task ("TaskB", new ResourceManager[] {resourceA, resourceB}, new int[] {4, 3});
      Task taskC = new Task ("TaskC", new ResourceManager[] {resourceA, resourceB}, new int[] {4, 2});
      
      Task   task [] = new Task []  {taskA, taskB, taskC};
      String name [] = new String[] {"A", "B", "C"};
      
      Thread t[] = new Thread [task.length];
      for (int i = 0; i < task.length; ++i)
         t[i] = new Thread (task[i], "t" + name[i]);
      
      t[0].start ();
      Util.sleepInMilli(20);
      t[1].start ();
      Util.sleepInMilli(20);
      t[2].start ();   
   }
}

class Task implements Runnable
{
   private String name = null;
   
   private ResourceManager manager[] = null;
   
   private int count[] = null;
         
   public Task (String name, ResourceManager manager[], int count[])
   {
      this.name = name;
      this.manager = manager;
      this.count = count;
      if (manager.length != count.length)
         throw new IllegalArgumentException("manager.length != count.length");
   }
   
   @Override
   public void run ()
   {
      ResourceManager.waitForAllResources (manager, count);
      Util.threadLog ("Started  Task " + name);
      Util.sleepInMilli (250);
      Util.threadLog ("Finished Task " + name);
      ResourceManager.releaseResources(manager, count);
   }
}

/**
 * Resources
 */

abstract class ResourceManager
{
   private Semaphore sem = null; 
   
   public ResourceManager (int totalCount)
   {
      this.sem = new Semaphore (totalCount);
   }
   
   public static void waitForAllResources (ResourceManager manager[], int count[])
   {
      boolean acquiredAllResources = false;
      while (!acquiredAllResources)
      {
         acquiredAllResources = true;
         Util.threadLog("Started acquiring ALL resources.");
         for (int i = 0; i < manager.length; ++i)
         {
            boolean isAvail = manager[i].sem.tryAcquire(count[i]);
            if (!isAvail)
            {
               acquiredAllResources = false;
               Util.threadLog(count[i] + " number of resource " + manager[i] + " not available!");
               releaseResources (manager, count , i);
               Util.threadLog ("Sleeping");
               Util.sleepInMilli(50);
               break;
            }
            Util.threadLog("Allocated. Resource=" + manager[i] + " Allocation=" + count[i] + " Remaining=" + manager[i].sem.availablePermits());
         }
      }
      Util.threadLog("Finished acquiring ALL resources.");
   }
   
   public static void releaseResources (ResourceManager manager[], int count[])
   {
      releaseResources(manager, count, manager.length);
   }
   
   private static void releaseResources (ResourceManager manager[], int count[], int size)
   {
      for (int i = 0; i < size; ++i)
      {
         Util.threadLog("Released. Resource=" + manager[i] + " ReleaseCount=" + count[i]);
         manager[i].sem.release(count[i]);
      }
   }
}

class ResourceAManager extends ResourceManager
{
   public ResourceAManager (int totalCount)
   {
      super (totalCount);
   }
   
   @Override
   public String toString ()
   {
      return "ResourceA";
   }
}

class ResourceBManager extends ResourceManager
{
   public ResourceBManager (int totalCount)
   {
      super (totalCount);
   }
   
   @Override
   public String toString ()
   {
      return "ResourceB";
   }
}
