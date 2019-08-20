package lambda.optional;

import java.util.Optional;
import util.Util;
public class L02Basic
{
   public static void main (String arg[])
   {
      String strNull = null;
      
      /**
       * NPE
       */
      Util.printHeading("Traditional NPE");
      try
      {
         doSomethingA (strNull);
      }
      catch (Exception e) 
      { 
         System.out.println("Message=" + e.getMessage()); 
      }
      
      /**
       * null     : Get default value
       * non null : Use given value
       */
      Util.printHeading("Use default");
      doSomethingB (strNull);
      
      /**
       * null     : Throw meaningful exception
       * non null : Use given value
       */
      try
      {
         Util.printHeading("Meaningful Exception");
         doSomethingC (strNull);
      }
      catch (Exception e)
      {
         System.out.println("Message=" + e.getMessage());
      }
      
      /**
       * null     : noop
       * non null : Use given value
       */
      Util.printHeading("Noop");
      doSomethingD (strNull);
      
      /**
       * Enforce user to send a non null value
       */
      try
      {
         Util.printHeading("Enforce non-null");
         //doSomethingE (Optional.of(strNull));
      }
      catch (Exception e)
      {
         e.printStackTrace();
      }
      //doSomethingE(null);
      Optional<String> op = Optional.empty();
      System.out.println("value : "+op);
   }
   
   
   public static void doSomethingA (String str)
   {
      int length = str.length();
      System.out.println(length);
   }
   
   public static void doSomethingB (String str)
   {
      str = Optional.ofNullable(str).orElse("");
      int length = str.length();
      System.out.println(length);
   }
   
   public static void doSomethingC (String str)
   {
      str = Optional.ofNullable(str).orElseThrow(() -> new IllegalArgumentException ("Argument str cannot be null"));
      int length = str.length();
      System.out.println(length);
   }
   
   public static void doSomethingD (String str)
   {
      Optional.ofNullable(str).ifPresent((s) -> System.out.println(s.length()));
   }
   
   public static void doSomethingE (Optional<String> opStr)
   {
      System.out.println(opStr.get().length());
   }
}
