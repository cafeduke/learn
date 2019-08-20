package core.thread.deadlock;

import util.Util;

class WorkerP implements Runnable
{
   @Override
   public void run ()
   {
      Util.threadLog("Need " + LegacyResourceManager.RESOURCE_A);
      synchronized (LegacyResourceManager.RESOURCE_A)
      {
         Util.threadLog("Acquired " + LegacyResourceManager.RESOURCE_A);
         
         Util.threadLog("Sleeping");
         Util.sleep(2);
         Util.threadLog("Woke up from sleep");
         
         Util.threadLog("Need " + LegacyResourceManager.RESOURCE_B);
         synchronized (LegacyResourceManager.RESOURCE_B)
         {
            Util.threadLog("Acquired " + LegacyResourceManager.RESOURCE_B);
         }
         Util.threadLog("Released " + LegacyResourceManager.RESOURCE_B);
         
      }
      Util.threadLog("Released " + LegacyResourceManager.RESOURCE_A);
   }
}

class WorkerQ implements Runnable
{
   @Override
   public void run ()
   {
      Util.threadLog("Need " + LegacyResourceManager.RESOURCE_B);
      synchronized (LegacyResourceManager.RESOURCE_B)
      {
         Util.threadLog("Acquired " + LegacyResourceManager.RESOURCE_B);
         
         Util.threadLog("Sleeping");
         Util.sleep(2);
         Util.threadLog("Woke up from sleep");
         
         Util.threadLog("Need " + LegacyResourceManager.RESOURCE_A);
         synchronized (LegacyResourceManager.RESOURCE_A)
         {
            Util.threadLog("Acquired " + LegacyResourceManager.RESOURCE_A);
         }
         Util.threadLog("Released " + LegacyResourceManager.RESOURCE_A);
      }
      Util.threadLog("Released " + LegacyResourceManager.RESOURCE_B);
   }
}

class LegacyResourceManager 
{
   public static final int RESOURCE_COUNT = 2;
   
   public static String RESOURCE_A = new String ("Resource-A");
   
   public static String RESOURCE_B = new String ("Resource-B");
}


public class Deadlock
{ 
   public static void main (String arg[])
   {
      WorkerP workerP = new WorkerP ();
      WorkerQ workerQ = new WorkerQ ();
      Thread tP = new Thread (workerP, "tWorkerP");
      Thread tQ = new Thread (workerQ, "tWorkerQ");
      tP.start();
      tQ.start();
   }
}



















