package lambda.standard;

import java.util.function.*;
import java.util.logging.*;
import util.Person;
import util.Person.Gender;
import util.Util;

public class L06UsingStandardFI
{
   public static void main (String arg[]) throws Exception
   {
      /**
       * Using Consumer 
       */
      
      // Lambda
      Consumer<String> doNothing = (s) -> {};
      doNothing.accept("Nothing");
            
      Consumer<String> doPrint   = (s) -> System.out.println(s);
      doPrint.accept("Something");
      
      // Method reference - Static method (SM)
      Consumer<String> doLogA = Util::threadTimeStampLog;
      doLogA.accept("Log MessageA");
      
      // Method reference - Instance method of Particular Object (IMPO)
      Logger myLogger = Logger.getLogger(L06UsingStandardFI.class.getName());
      myLogger.addHandler (new FileHandler("log.txt"));
      myLogger.setUseParentHandlers(false);
      
      Consumer<String> doLogB = myLogger::info;
      doLogB.accept("Log MessageB");
      
      // Method reference - Instance method of Arbitrary Object (IMPO)
      Consumer<String> doLogC = String::notify; 
      
      //Lamda
      Supplier<String> supplier1 =  () -> "Hello World";
      System.out.println(supplier1.get());
      
      // Method reference - Static method (SM)
      Supplier<String> supplier2 = Util::getTimestamp;      
      doPrint.accept(supplier2.get());
      
      String str = new String("Hello");         
      Supplier<String> supplier3 = str::toLowerCase;
      doPrint.accept(supplier3.get());
      
      
      Person p = new Person(39,Gender.MALE);         
      Supplier<Integer> supplier4 = (p::getAge);
      doPrint.accept(supplier3.get());
      
      
      
      
      
      
      
      
      
      
      
      
      
      
      
      
   }
}
