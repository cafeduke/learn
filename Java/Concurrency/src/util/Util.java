package util;

import java.text.SimpleDateFormat;
import java.util.*;

public class Util
{
   public static final String lineSep = System.getProperty ("line.separator");
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
   
   public static void printHeading (String title)
   {
      int totalLength = 60;        
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
      SimpleDateFormat format = new SimpleDateFormat("EEE, dd-MMM-yyyy HH:mm:ss.SSS z");
      buffer.append ("[" + format.format(new Date ()) + "] ")
            .append ("[Thread=" + Thread.currentThread().getName() + "] ")
            .append (mesg);
      System.out.println(buffer);
   }  
   
   public static void threadTimeStampLog (String mesg)
   {
      threadLog (mesg, true);
   }
   
   public static void threadLog (String mesg)
   {
      threadLog (mesg, false);
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
}
