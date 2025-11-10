package lambda.optional;

import java.util.Optional;
import util.Util;

public class L01Basic
{
   public static void main (String arg[])
   {
      String strContent = "Hello";
      String strNull = null;
      
      // Optional.of : Accepts non-null
      Util.printHeading("Optional.of (non-null)");
      Optional<String> opA = Optional.of(strContent);
      System.out.println(opA);      
      
      // Optional.of : Assigning null shall throw NPE
      Util.printHeading("Optional.of (null)");
      try
      {
         Optional<String> opB = Optional.of(strNull);
         System.out.println(opB);
      }
      catch (NullPointerException e)
      {
         System.out.println("Cannot assign null to optional");
      }
      
      // Optional.ofNullable : Accepts non-null, returns  Optional
      Util.printHeading("Optional.ofNullable (non-null)");
      Optional<String> opC = Optional.ofNullable(strContent);
      System.out.println(opC);
      
      // Optional.ofNullable : Accepts null, returns empty Optional
      Util.printHeading("Optional.ofNullable (null)");
      Optional<String> opD = Optional.ofNullable(strNull);
      System.out.println(opD);
      
      // Optional flatMap : Converting one Optional to another
      Util.printHeading("flatMap : Converting One Optional to another");
      Optional<String>  opStr = Optional.of("123");
//      Optional<Double>  opDouble = opStr.flatMap( (s) -> Optional.of(Double.valueOf(s)));      
      Optional<Integer> opInt = opStr.flatMap((s) -> Optional.of(Integer.valueOf(s))); 
     
   }
}
