package util;

import java.util.List;
import java.util.function.Predicate;

public class Person
{
   public enum Gender
   {
      MALE, FEMALE;
   }

   private int age = -1;

   private Gender gender = Gender.MALE;

   public Person (int age, Gender gender)
   {
      this.age = age;
      this.gender = gender;
   }
   
   /* Getters and Setters */
   
   /**
    * Get age 
    */
   public int getAge()
   {
      return age;
   }

   /**
    * Set age
    * @param age Age of the person
    */
   public void setAge(int age)
   {
      this.age = age;
   }

   /**
    * @return Gender
    */
   public Gender getGender()
   {
      return gender;
   }

   /**
    * Set gender
    * @param gender Gender of the person
    */
   public void setGender(Gender gender)
   {
      this.gender = gender;
   }   
   
   @Override
   public String toString ()
   {
      return "Age=" + age + " Gender=" + gender;
   }
}

