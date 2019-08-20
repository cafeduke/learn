package lambda.standard;

import java.util.Date;
import java.util.function.*;
import util.Person;
import util.Person.Gender;
import util.Util;

/**
 * Consumer - A consumer accepts without returning anything. (FI: accept)
 * ----------------------------------------------------------------------
 * Parameters: Object
 * Return    : void
 */
public class L02Consumer
{
   public static void main (String arg[])
   {
      /* Basic Usage */
      
      Util.printHeading ("Consumer Greeting");
      Consumer<String> consumerGreeting = (s) -> System.out.println ("Hello, " + s);
      consumerGreeting.accept ("Lambda");

      Util.printHeading ("Consumer Log");
      Consumer<String> logMesg = (s) -> System.out.println("[" + new Date () + "] " + s);
      logMesg.accept("Finished");
      
      Util.printHeading ("Consumer Noop");
      Consumer<String> consumerNoop = (s) -> {};
      
      Util.printHeading ("IntConsumer");
      IntConsumer consumerCount = (num) -> System.out.println ("We have " + num + " votes.");
      consumerCount.accept(3245);
      
      /* Method Reference */
      
      Util.printHeading ("Method Reference - Instance method of a particular object");
      Person p = new Person (50, Gender.MALE);      
      IntConsumer personAgeSetter = p::setAge;
      personAgeSetter.accept(18);
      System.out.println("Person=" + p);
      
      Consumer<Gender> personGenderSetter = p::setGender;
      personGenderSetter.accept (Gender.FEMALE);  
      
      Util.printHeading ("Consumer Log");
      
      /* Basic Usage */
   }
}
