package lambda.standard;

import java.util.*;
import java.util.function.*;
import util.Person;
import util.Util;
import util.Person.*;
/**
 * Predicate - To make a statement or assertion
 * --------------------------------------------
 * A predicate is a condition that is either true or false.
 * Parameters : Object
 * Returns boolean
 * 
 */
public class L04Predicate
{
   public static void main (String arg[])
   {
      /* Basic Usage */
      Util.printHeading ("Basic Usage");
      Predicate<String> predicateEmpty = (s) -> s.isEmpty();
      System.out.println("IsEmpty=" + predicateEmpty.test(""));
      
      Predicate<Person> predicateSenior = (p) -> p.getAge() >= 60;
      Person p = new Person (71, Gender.MALE);
      System.out.println("IsSenior=" + predicateSenior.test(p));
      
      int age [] = new int []  { 5, 18, 21, 30, 50 };
      Gender gender[] = new Gender [] { Gender.MALE, Gender.FEMALE, Gender.MALE, Gender.MALE, Gender.FEMALE };
      
      List<Person> listPerson = new ArrayList<Person> ();
      for (int i = 0; i < age.length; ++i)
         listPerson.add (new Person (age[i], gender[i]));
      
      Util.printHeading("Army Shortlist");
      printPersonEligibleForArmy(listPerson);
      
      Util.printHeading("Marriage Shortlist");
      printPersonEligibleForMarriage(listPerson);
      
      /* Method Reference */      
      
      Util.printHeading ("Method Reference - Static method");
      IntPredicate predicateOdd = Util::isOdd;
      System.out.println("5 is odd = " + predicateOdd.test(5));
      
      Util.printHeading ("Method Reference - Instance method of a particular object");      
      Predicate<String> predicateSubstr = "HelloWorld"::contains; 
      System.out.println("HelloWorld has Hello = " + predicateSubstr.test("Hello"));
      System.out.println("HelloWorld has World = " + predicateSubstr.test("World"));
      System.out.println("HelloWorld has Bye   = " + predicateSubstr.test("Bye"));
      
      Util.printHeading ("Method Reference - Instance Method of an Arbitrary Object of a Particular Type");
      Predicate<String> predicateIsEmpty = String::isEmpty;
      System.out.println("'' Is Empty     = " + predicateIsEmpty.test(""));
      System.out.println("'null' Is Empty = " + predicateIsEmpty.test("null"));
   }
   
   
   public static void printPersonEligibleForArmy (List<Person> listPerson)
   {
      printPersonsWithPredicate (listPerson, p -> p.getGender() == Gender.MALE && p.getAge() >= 18 && p.getAge() <= 25);
   }   
   
   public static void printPersonEligibleForMarriage (List<Person> listPerson)
   {
      printPersonsWithPredicate (listPerson, p -> (p.getGender() == Gender.MALE) ? p.getAge() >= 21 : p.getAge() >= 18);
   }
   
   

   /**
    * Navigate through the <b>listPerson</b> and display all persons passing the <b>tester</b>
    * 
    * @param listPerson List of persons
    * @param tester Predicate to select a person
    */
   private static void printPersonsWithPredicate (List<Person> listPerson, Predicate<Person> tester)
   {
      for (Person currPerson : listPerson)
      {
         if (tester.test(currPerson))
         {
            System.out.println(currPerson);
         }
      }
   }
   
}

