package principle.visibility.packB;

import principle.visibility.packA.A;

/**
 * How is this related to class A
 * -------------------------------
 * SubClass of A
 */
public class SubclassOfA extends A
{
   public SubclassOfA ()
   {
      super ();
   }
   
   public void test ()
   {
      A a1 = new A ();
      System.out.println ("Sub Class (Using object of type A)");
      System.out.println ("----------------------------------");
      a1.funPublic();
      System.out.println ();
      
      SubclassOfA a2 = new SubclassOfA();      
      System.out.println ("Sub Class (Using object of type SubclassofA)");
      System.out.println ("--------------------------------------------");
      a2.funProtected();
      a2.funPublic();  
      
      System.out.println ();
   }
   
   public static void main (String arg[])
   {
      new SubclassOfA ().test ();
   }
}

