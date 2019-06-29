import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.*;

public class Util
{
   public static final String lineSep = System.getProperty ("line.separator");
   
   public static final SimpleDateFormat ThreadTimestampFormat = new SimpleDateFormat("EEE, dd-MMM-yyyy HH:mm:ss.SSS z");

   public static void sleep (int sec)
   {
      try
      {
         Thread.sleep(sec * 1000);
      }
      catch (InterruptedException e)
      {
         e.printStackTrace();
      }
   }
   
   public static void heading (String title)
   {
      System.out.println("-".repeat(80));
      System.out.println(" " + title);
      System.out.println("-".repeat(80));      
   }
   
   public static void title (String title)
   {
      int totalLength = 80;        
      int padding = 0;
      if (totalLength > title.length())
         padding = (totalLength - title.length()) / 2;
      
      StringBuffer buffer = new StringBuffer ();
      buffer.append(lineSep)
            .append (String.format("%" + totalLength + "s", "").replaceAll(" ", "-")).append(lineSep)
            .append (String.format("%" + padding + "s", ""))
            .append (title).append(lineSep)
            .append (String.format("%" + totalLength + "s", "").replaceAll(" ", "-"));
      
      System.out.println(buffer.toString());
   }
   
   private static void threadLog (String mesg, boolean logTimestamp)
   {
      StringBuilder buffer = new StringBuilder ();
      
      if (logTimestamp)     
         buffer.append ("[" + ThreadTimestampFormat.format(new Date ()) + "] ");         
      buffer.append ("[Thread=" + Thread.currentThread().getName() + "] ")
            .append (mesg);
      System.out.println(buffer);
   }  
   
   public static void threadTimeStampLog (String mesg)
   {
      threadLog(mesg, true);
   }
   
   public static void threadTimeStampLog (Object mesg)
   {
      threadTimeStampLog(mesg);
   }
   
   public static void threadLog (String mesg)
   {
      threadLog (mesg, false);
   }   
   
   public static void threadLog (Object object)
   {
      threadLog(object.toString());
   }
   
   public static void sleepInMilli (int sec)
   {
      try
      {
         Thread.sleep(sec);
      }
      catch (InterruptedException e)
      {
         e.printStackTrace();
      }
   }   
   
   public static class SimpleThreadFactory implements ThreadFactory
   {
      private int count = 0;
      
      public void reset ()
      {
         this.count = 0;
      }
      
      @Override
      public Thread newThread(Runnable r)
      {
         return new Thread(r, "t" + (++count));
      }
      
   }
}
