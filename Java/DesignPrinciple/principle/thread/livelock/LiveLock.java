package principle.thread.livelock;

import java.util.*;

import principle.Util;

class TapFill implements Runnable
{
   List<Integer> bucket = null;
   
   int maxSize = 0;
   
   int sleepTime = 0;
   
   public TapFill (List<Integer> bucket, int sleepTime, int maxSize)
   {
      this.bucket = bucket;
      this.sleepTime = sleepTime;
      this.maxSize = maxSize;
   }
   
   @Override
   public void run ()
   {
      while (true)
      {
         synchronized (bucket)
         {
            if (bucket.size() >= maxSize)
            {
               Util.theadLog("[Bye] Bucket filled");
               break;
            }
            
            int num = (int)(Math.random()*100);
            bucket.add(new Integer(num));
            Util.theadLog(" Bucket " + bucket + " Add=" + num);
         }
         
         Util.sleep(sleepTime);
      }
   }
}

class TapEmpty implements Runnable
{
   List<Integer> bucket = null;
   
   int sleepTime = 0;
   
   public TapEmpty (List<Integer> bucket, int sleepTime)
   {
      this.bucket = bucket;
      this.sleepTime = sleepTime;
   }
   
   @Override
   public void run ()
   {      
      while (true)
      {
         synchronized (bucket)
         {
            if (bucket.isEmpty())
            {
               Util.theadLog("[Bye] Bucket emptied");
               break;
            }
            
            int num = bucket.remove(0);
            Util.theadLog(" Bucket " + bucket + " Removed=" + num);
         }
         Util.sleep(sleepTime);
      }
   }   
}

public class LiveLock
{
   public static void main (String arg[])
   {
      List<Integer> bucket = new ArrayList<Integer>();
      
      /* Fill half of the bucket */
      for (int i = 0; i < 5; ++i)
         bucket.add ((i+1));
      
      TapFill  tapFill  = new TapFill  (bucket, 1, 10);
      TapEmpty tapEmpty = new TapEmpty (bucket, 1);
      Thread tFill  = new Thread (tapFill , "TapFill ");
      Thread tEmpty = new Thread (tapEmpty, "TapEmpty");
      tFill.start();
      tEmpty.start();
   }
}





