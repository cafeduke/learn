package lambda.standard;

import java.text.*;
import java.util.Date;
import java.util.function.*;
import util.Person;
import util.Person.Gender;

/**
 * Supplier - A supplier provides without accepting anything. (FI: get)
 * --------------------------------------------------------------------
 * Parameters : void
 * Return     : Object
 */
public class L03Supplier
{
   public static void main (String arg[])
   {
      String pattern = "EEE, dd-MMM-yyyy HH:mm:ssXXX";
      Supplier<SimpleDateFormat> formatDate = () -> new SimpleDateFormat(pattern);      
      System.out.println("Date=" + formatDate.get().format(new Date()));      
      
      // Getters are suppliers
      
      Person p = new Person (50, Gender.MALE);
      IntSupplier supplyAge = p::getAge;
      System.out.println("Age=" + supplyAge.getAsInt());
      
      Supplier<Gender> supplyGender = p::getGender;
      System.out.println("Gender=" + supplyGender.get());
   }
}
