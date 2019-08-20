package core.visibility.packB;

import core.visibility.packA.A;

/**
 * How is this related to class A
 * -------------------------------
 * Class in a different package
 */
public class B
{
   public B ()
   {
      
   }
   
   public void test ()
   {
      A a1 = new A ();
      System.out.println ("Class belonging to different package");
      System.out.println ("------------------------------------");
      a1.funPublic();
      System.out.println ();
   }
   
   public static void main (String arg[])
   {
      new B ().test ();
   }
}
