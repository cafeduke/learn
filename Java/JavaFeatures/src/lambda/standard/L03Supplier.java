package lambda.standard;

import java.text.*;
import java.util.Arrays;
import java.util.Date;
import java.util.function.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import util.Person;
import util.Person.Gender;

/**
 * Supplier - A supplier provides without accepting anything. (FI: get)
 * --------------------------------------------------------------------
 * Parameters : void
 * Return : Object
 */
public class L03Supplier
{
   public static void main(String arg[])
   {
      String pattern = "EEE, dd-MMM-yyyy HH:mm:ssXXX";
      Supplier<SimpleDateFormat> formatDate = () -> new SimpleDateFormat(pattern);
      System.out.println("Date=" + formatDate.get().format(new Date()));

      // Getters are suppliers
      Person p = new Person(50, Gender.MALE);
      IntSupplier supplyAge = p::getAge;
      System.out.println("Age=" + supplyAge.getAsInt());

      Supplier<Gender> supplyGender = p::getGender;
      System.out.println("Gender=" + supplyGender.get());

      // Supplier with constructors
      // --------------------------
      Supplier<Person> s = null;
      s = () -> new Person();
      s = Person::getDefaultInstance;
      s = Person::new;

      // Supplier for array of objects
      Supplier<Person[]> sa = null;
      sa = () -> Person.getEligiblePersons();
      sa = Person::getEligiblePersons;
      sa = () -> Stream.of(Person.getEligiblePersons()).toArray((size) -> new Person[size]);
      sa = () -> Stream.of(Person.getEligiblePersons()).toArray(Person[]::new);

   }
}
