package util;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.function.Consumer;

public class Util
{
   public static final String lineSep = System.getProperty ("line.separator");
   
   private static final SimpleDateFormat TimeStampFormat = new SimpleDateFormat ("EEE, dd-MMM-yyyy HH:mm:ss.SSS z");

   
   
   public static void main (String arg[])
   {
	   
	   class Inner 
	   {
		   
	   }
   }
   
   
   
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
   
   public static boolean isOdd (int num)
   {
      return (num % 2 == 1);
   }
   
   public static String getTimestamp ()
   {
      return TimeStampFormat.format(new Date());
   }

   public static void print (String mesg)
   {
      System.out.println("[" + getTimestamp () + "] " + mesg);
   }
   
   public static void printHR ()
   {
      System.out.println("------------------------------------------------------------");
   }
   
   public static void printHeading (String title)
   {
      int totalLength = 60;        
      int padding = 1;
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
      
      buffer.append ("[" + getTimestamp () + "] ")
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
   
   public static void threadLog (String mesg, Object... object)
   {
      StringBuilder buffer = new StringBuilder ();
      for (Object currObject : object)
         buffer.append("[" + currObject + "] ");
      buffer.append(mesg);
      threadLog (buffer.toString(), false);
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
