package antipattern.constantinterface;

/**
 * <pre>
 * This is an Anti-pattern - Do NOT do this!
 * ----------------------------------------
 * 
 * What is wrong ?
 * ---------------
 *    - When a Circle implements a Constant the IS-A relation is not satisfied.
 *       - A Circle IS NOT a Constant
 *    - An interface MUST be used as type that can be used for the instances of the class.
 *       - Constant const = new Circle (); does not make any sense.      
 *</pre>
 */
public class ConstantInterface
{
   
}

/**
 * The class implements the interface having constants
 * just to locally reference constants. (Ease of use) 
 */
class Circle implements Constant
{
   double radius;
   
   public Circle (double radius)
   {
      this.radius = radius;
   }
   
   public double getArea ()
   {
      return PI * radius * radius; 
   }
}

/**
 * The interface has only constants and no methods.
 */
interface Constant
{
   public static final double PI = 3.142857;
}
