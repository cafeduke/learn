package principle.general;

/**
 * <pre>
 * How to create a class that holds only static members
 * -----------------------------------------------------
 * 
 * Class that holds static methods must enforce non-instantiability
 * ---------------------------------------------------------------
 * When creating a class that holds static methods.
 * Make sure the class cannot be instantiated using a private default constructor.
 *    
 * The private constructor ensures that 
 *    - Even a member function cannot instantiate (by mistake)
 *    - The class cannot be subclassed.
 *
 *</pre>
 */
public class StaticClassPrinciple
{
   /**
    * The constructor throws exception to make sure the class cannot
    * be instantiated by mistake even by the member functions. 
    */
   private StaticClassPrinciple ()
   {
      throw new AssertionError();
   }
   
   public static void funA ()
   {
      System.out.println("Fun A");
   }

   public static void funB ()
   {
      System.out.println("Fun B");
   }
   
   public static void main (String arg[])
   {
      StaticClassPrinciple.funA();
      StaticClassPrinciple.funB();
   }
}
