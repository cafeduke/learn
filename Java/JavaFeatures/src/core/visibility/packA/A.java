package core.visibility.packA;

public class A
{
   private void funPrivate ()
   {
      System.out.println("Private function");
   }
   
   void funDefault ()
   {
      System.out.println("Default function");
   }   
   
   protected void funProtected ()
   {
      System.out.println("Protected function");
   }

   public void funPublic ()
   {
      System.out.println("Public function");
   }
   
   public void test ()
   {
      System.out.println ("Class A");
      System.out.println ("-------");      
      A a1 = new A ();
      a1.funPrivate();
      a1.funDefault();
      a1.funProtected();
      a1.funPublic();
      System.out.println ();
   }
}
