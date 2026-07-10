package util;

import java.util.stream.Stream;

public class Person
{
   public enum Gender
   {
      MALE, FEMALE;
   }

   private String name;

   private int age = -1;

   private Gender gender = Gender.MALE;

   private String state;

   private static Person p[] = new Person[]
   {
      new Person("Raghu", 44, Gender.MALE, "KA"),
      new Person("Madhu", 48, Gender.MALE, "KA"),
      new Person("Pavi", 40, Gender.FEMALE, "KA"),
      new Person("Sanjeev", 41, Gender.MALE, "KA"),
      new Person("Elavarasi", 19, Gender.FEMALE, "TN"),
      new Person("Alwar", 54, Gender.MALE, "TN")
   };

   public Person()
   {
      this(18, Gender.MALE);
   }

   public Person(int age, Gender gender)
   {
      this("Anonymous", age, gender, "KA");
   }

   public Person(String name, int age, Gender gender, String state)
   {
      this.name = name;
      this.age = age;
      this.gender = gender;
      this.state = state;
   }

   public static Person getDefaultInstance()
   {
      return new Person();
   }

   public static Person[] getPersons()
   {
      return p;
   }

   public static Person[] getEligiblePersons()
   {
      return Stream.of(p)
         .filter((p) -> p.getAge() >= 18)
         .toArray(Person[]::new);
   }

   /**
    * ---------------------------------------------------------------------------------------------------
    * Getters and Setters
    * ---------------------------------------------------------------------------------------------------
    */

   public String getName()
   {
      return name;
   }

   public void setName(String name)
   {
      this.name = name;
   }

   public String getState()
   {
      return state;
   }

   public void setState(String state)
   {
      this.state = state;
   }

   public int getAge()
   {
      return age;
   }

   public void setAge(int age)
   {
      this.age = age;
   }

   public Gender getGender()
   {
      return gender;
   }

   public void setGender(Gender gender)
   {
      this.gender = gender;
   }

   @Override
   public String toString()
   {
      return String.format("%-20s %3d %-6s %-2s", name, age, gender, state);
   }
}
