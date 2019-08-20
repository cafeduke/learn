package lambda.standard;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.function.*;
import util.Util;

public class L05Function
{
   public static void main (String arg[])
   {
      Util.printHeading ("Integer value of String");
      Function<String,Integer> convertStrToInt = (s) -> Integer.valueOf (s);
      System.out.println(convertStrToInt.apply("100"));

      Util.printHeading ("Hasher Bracer Boxer");
      Function<String,String> fHasher = (s) -> "#" + s + "#";
      Function<String,String> fBracer = (s) -> "{" + s + "}";
      Function<String,String> fBoxer  = (s) -> "[" + s + "]"; 
      
      Function<String,String> fDecorateA = fHasher.andThen(fBracer).andThen(fBoxer);
      System.out.println(fDecorateA.apply ("HelloWorld"));
      
      /* Method Reference */     
      Util.printHeading ("Method Reference - Static method");
      Function<String,Integer> convertStringToInt = Integer::valueOf;
      System.out.println(convertStringToInt.apply("200"));
      
      Util.printHeading ("Method Reference - Instance method of a particular object");
      SimpleDateFormat dateFormat = new SimpleDateFormat ("EEE, dd-MMM-yyyy HH:mm:ss.SSS z");
      Function<Date,String> formatter = dateFormat::format;
      System.out.println(formatter.apply(new Date()));
      
      IntFunction <String> helloSubstr = "HelloWorld"::substring;
      System.out.println(helloSubstr.apply(5));
      
      Util.printHeading ("Method Reference - Instance Method of an Arbitrary Object of a Particular Type");
      Function<String,Integer> sizer = String::length;
      System.out.println(sizer.apply("HelloWorld"));
      
   }
}
