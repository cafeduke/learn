package core.object.finalize;

/**
 * Finalize is called with an object is garbage collected.
 * ------------------------------------------------------
 * In this case
 *
 */
public class TestFinalize
{
   public TestFinalize ()
   {
      System.out.println ("Constructor");
   }   
      
   @Override
   public void finalize()
   {
      System.out.println ("Finalize");
   }

   public static void main (String arg[]) throws Exception
   {
      TestFinalize f1 = new TestFinalize();      
      TestFinalize f2 = new TestFinalize();
      f1 = null;
      System.gc();
   }
}
