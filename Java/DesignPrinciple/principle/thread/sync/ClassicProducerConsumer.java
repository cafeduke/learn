package principle.thread.sync;

import java.util.*;

import principle.Util;

class LegacyProducer implements Runnable
{
   private List<Integer> buffer = null;
   
   private int sizeLimit = 0;  
   
   private int sleepTime = 0;
   
   public LegacyProducer (List<Integer> buffer, int sizeLimit, int sleepTime)
   {
      this.buffer = buffer;
      this.sizeLimit = sizeLimit;
      this.sleepTime = sleepTime;
   }
   
   @Override
   public void run()
   {
      try
      {
         for (int i = 1; i <= 10; ++i)
         {
            synchronized (buffer)
            {
               if (buffer.size() == sizeLimit)
               {
                  Util.theadLog("[Waiting] Size limit reached");
                  buffer.wait();
                  Util.theadLog("[Notified] Out of wait");
               }
               
               if (buffer.size() < sizeLimit)
               {
                  int num = i * 5;
                  buffer.add(num);
                  Util.theadLog("[Produced] Produced Num=" + num);

                  Util.theadLog("[Notify] Notify all about production");
                  buffer.notifyAll();
                  
                  Util.sleep(sleepTime);
               }
            }
         }
         Util.theadLog("[Finished] Bye");
      }
      catch (InterruptedException e)
      {
         e.printStackTrace();
      }
   }
}

class LegacyConsumer implements Runnable
{
   private List<Integer> buffer = null;
   
   private int sleepTime = 0;
   
   public LegacyConsumer (List<Integer> buffer, int sleepTime)
   {
      this.buffer = buffer;
      this.sleepTime = sleepTime;
   }
   
   @Override
   public void run()
   {
      try
      {
         for (int i = 1; i <= 10; ++i)
         {
            synchronized (buffer)
            {
               if (buffer.isEmpty())
               {
                  Util.theadLog("[Waiting] List is empty");
                  buffer.wait();
                  Util.theadLog("[Notified] Out of wait");
               }
               
               if (!buffer.isEmpty())
               {
                  int num = buffer.remove(0);                  
                  Util.theadLog("[Consumed] Consumed Num=" + num);
                  
                  Util.theadLog("[Notify] Notify all about consumption");
                  buffer.notifyAll();
                  
                  Util.sleep(sleepTime);
               }
            }
         }
         Util.theadLog("[Finished] Bye");
      }
      catch (InterruptedException e)
      {
         e.printStackTrace();
      }
   }
}

public class ClassicProducerConsumer
{
   public static void main (String arg[])
   {
      List<Integer> buffer = new ArrayList<Integer> ();
      LegacyProducer producer = new LegacyProducer(buffer, 3, 1);
      LegacyConsumer consumer = new LegacyConsumer(buffer, 3);
      Thread tProducer = new Thread (producer, "tProducer");
      Thread tConsumer = new Thread (consumer, "tConsumer");
      tProducer.start ();
      tConsumer.start ();
   }
}













