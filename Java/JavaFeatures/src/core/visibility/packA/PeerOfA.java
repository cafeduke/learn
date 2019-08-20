package core.visibility.packA;

/**
 * How is this related to class A
 * -------------------------------
 * Class belonging to same package as A
 */
public class PeerOfA
{
   public PeerOfA ()
   {
      
   }
   
   public void test ()
   {
      A a1 = new A ();
      System.out.println ("Class belonging to same package");
      System.out.println ("-------------------------------");
      a1.funDefault();
      a1.funProtected();
      a1.funPublic();
      System.out.println ();
   }
   
   public static void main (String arg[])
   {
      new PeerOfA ().test ();
   }
}
