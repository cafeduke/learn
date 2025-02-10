package pattern.creational.immutable;

/**
 * Immutable Class
 * ---------------
 * A class is immutable if the state of the object cannot be changed 
 * after it is created.
 * 
 * This can be done as follows
 * 
 *    - Class MUST not allow extension
 *       - Otherwise the subclass can be made mutable.
 *       - This can be done in two ways:
 *          - Declare the class as final
 *          - Make the constructor private and use static factory methods.
 *    - All fields MUST be private and references PREFERABLY final.
 *       - An uninitialized final reference can be assigned only once.
 *    - The constructor must accept mandatory arguments and set private fields.
 *    - The class does NOT provide any setter methods.
 *    - The class MAY provide requisite getter methods.
 *    
 * Advantages
 * ----------
 *    - Immutable objects are simple
 *    - Immutable classes don't require explicit synchronization and are thread safe.    
 *    - Immutable objects can be shared freely.
 *    
 * Disadvantages
 * -------------
 *    - If the object is huge then the operation is costly.    
 *       - A small change to the existing object results in two huge objects. 
 *    
 */
public class ImmutablePattern
{
    public static void main (String arg[])
    {
       Name name = Name.getInstance ("Raghu", "Nandan");
       System.out.println(name);
    }
}

/* Note: Class is final */
final class Name
{
   /* Note: Instance variables are private and final */
   
   private final String firstName;
   
   private final String lastName;
   
   /* Note: Constructor is private */
   
   private Name (String firstName, String lastName)
   {
      this.firstName = firstName;
      this.lastName = lastName;
   }
   
   /* Note: Static factory creates new instance. */
   
   public static Name getInstance (String firstName, String lastName)
   {
      return new Name (firstName, lastName);
   }
   
   /* Note: Only getters and NO setters */

   public String getFirstName()
   {
      return firstName;
   }

   public String getLastName()
   {
      return lastName;
   }
   
   @Override
   public String toString ()
   {
      return firstName + " " + lastName;
   }
}
