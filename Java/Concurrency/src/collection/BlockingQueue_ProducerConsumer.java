package collection;
import java.util.concurrent.*;

import util.Util;

/**
 * Producer Consumer problem easily addressed using BlockingQueue 
 */
public class BlockingQueue_ProducerConsumer
{
   public static void main (String arg[])
   {
      BlockingQueue<Integer> queue = new ArrayBlockingQueue<Integer>(3);      
      Thread tProducer = new Thread (new Producer(queue, 100), "tProducer");
      Thread tConsumer = new Thread (new Consumer(queue, 500), "tConsumer");
      tProducer.start ();
      tConsumer.start();
   }
}

class Producer implements Runnable
{
   private BlockingQueue<Integer> queue = null;
   
   private int delay = -1;
   
   public Producer (BlockingQueue<Integer> queue, int delay)
   {
      this.queue = queue;
      this.delay = delay;
   }

   @Override
   public void run()
   {
      try
      {
         for (int i = 1; i <= 10; ++i)
         {
            queue.put (i);
            Util.threadLog("Added " + i*i);
            Util.sleepInMilli(delay);
         }
      }
      catch (InterruptedException e)
      {
         e.printStackTrace();
      }
   }
}

class Consumer implements Runnable
{
   private BlockingQueue<Integer> queue = null;
   
   private int delay = -1;
   
   public Consumer (BlockingQueue<Integer> queue, int delay)
   {
      this.queue = queue;
      this.delay = delay;
   }

   @Override
   public void run()
   {
      try
      {
         for (int i = 1; i <= 10; ++i)
         {
            int value = queue.take ();
            Util.threadLog ("Removed " + value);            
            Util.sleepInMilli(delay);
         }
      }
      catch (InterruptedException e)
      {
         e.printStackTrace();
      }
   }
}


