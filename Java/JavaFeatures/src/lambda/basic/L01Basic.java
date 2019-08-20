package lambda.basic;

import java.util.Arrays;
import java.util.Comparator;
import lambda.basic.myfunc.Stylist;
import util.Util;

public class L01Basic
{   
   public static void main (String arg[])
   {
      Stylist stylist = null;
      
      /**
       * Conventional Way
       */
      Util.printHeading ("Conventional Way");
      MyStylist myStylist = new MyStylist();
      myStylist.doStyle("Hello World");
      
      /**
       * Anonymous Inner Class
       */
      Util.printHeading ("Anonymous Inner Class");
      stylist = new Stylist() 
      {
         @Override
         public String doStyle(String mesg)
         {
               return "#" + mesg + "#";
         }
      };
      stylist.doStyle("Hello World");
      
      /**
       * Lambda - Multiple lines
       */
      Util.printHeading ("Lambda - Multiple Lines");
      stylist = (s) -> 
      { 
         return "#" + s + "#"; 
      };
      stylist.doStyle("Hello World");

      /**
       * Lambda - Single Line
       */
      Util.printHeading ("Lambda - Single Line");
      stylist = (s) -> "#" + s + "#";
      stylist.doStyle("Hello World");
      
      /**
       * Runnable
       */
      Util.printHeading("Lambda - Runnable");
      Runnable workerA = () -> System.out.println("Hello World"); 
      new Thread (workerA).start();
      
      new Thread (() -> System.out.println("Hello World")).start();
      
      new Thread (() -> {}).start();
      Util.sleep(2);

      /**
       * Comparator
       */
      Util.printHeading("Lambda - Comparator");      
      String names[] = new String[] {"apple", "zebra", "cat", "elephant", "dog", "ball"};
      Comparator <String> comparatorLen = (s1, s2) -> s1.length () - s2.length ();
      Arrays.sort(names, comparatorLen);
      System.out.println(Arrays.asList(names));
      
      Comparator <String> comparatorAsc = (s1, s2)  -> s1.compareTo(s2);
      Arrays.sort(names, comparatorAsc);
      System.out.println(Arrays.asList(names));
      
      Comparator <String> comparatorDesc = (s1, s2) -> -1 * s1.compareTo(s2);
      Arrays.sort(names, comparatorDesc);
      System.out.println(Arrays.asList(names));
   }
   
   static class MyStylist implements Stylist
   {
      @Override
      public String doStyle (String mesg)
      {
         return "#" + mesg + "#";
      }
      
   }
   
   public static void displayStyle (String mesg, Stylist stylist)
   {
      System.out.println(stylist.doStyle(mesg));
   }
}

