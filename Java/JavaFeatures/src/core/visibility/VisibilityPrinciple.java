package core.visibility;

import core.visibility.packA.*;
import core.visibility.packB.*;

/**
 * <pre>
 * Order Of Increasing Visibility
 * ------------------------------
 * Private   - Visible only to member functions of the class.
 * Default   - Visible to any member of the same package.
 * Protected - Visible to any member of the same package + Sub classes.
 * Public    - Visible to all.
 * 
 * Rule of Thumb
 * -------------
 * Protected is more visible than default.
 * 
 * 
 * Visibility Principle In Inheritance
 * -----------------------------------
 * Sub class can only increase/retain the visibility of overridden methods.
 * In other words sub class CANNOT decrease visibility.
 * 
 * </pre>
 */
public class VisibilityPrinciple
{
   public static void main (String arg[])
   {
      new A ().test();      
      new SubclassOfA().test();
      new PeerOfA().test();
      new B ().test();
   }
}
