/**
 * 
 */
package core.object;

import java.util.*;

/**
 * <pre>
 * When the object's equals() is overridden - hashCode() must also be overridden.
 * -----------------------------------------------------------------------------
 * If equals is overridden and hashCode is not then the using HashMap, HashSet etc will fail.
 * This is because of the violation of the Object's hashCode contract #2 that states.
 *    - If two objects are equal according to the equals(Object) method, 
 *      then calling the hashCode method on each of the two objects must produce the same integer result. 
 * 
 * Analogy
 * -------
 * Twins have similar mannerisms. 
 *  - Two people with similar mannerisms are not necessarily twins
 *  - Two people with different mannerisms are definitely not twins
 *  
 * Equal objects have same hashCode
 *  - Objects with same hashCode are not necessarily equal  
 *  - Objects with different hashCode are definitely unequal
 *  
 * Note
 * ----
 * When an iterator has to loop through a collection and compare a given object (x) with current collection object (curr)
 *  - if x.hashCode() != curr.hasCode() ==> These objects are unequal, move on to check the object in collection
 *  - The above comparison is inexpensive as x.equals(curr) is expensive. Thus used by collection packages like HashSet
 *  - Hence hashCode() needs to be overridden if equals is overridden  
 * </pre>
 */
public class WhyToOverrideEqualsHashCode
{
   public static void main (String arg[])
   {
     
      /**
       * Though objects are equal
       */
      System.out.println("----------------------------------------------");
      System.out.println("Override equals but NOT hashCode");
      System.out.println("----------------------------------------------");      
      BadName badNameA = new BadName("Terror");
      BadName badNameB = new BadName("Terror");
      System.out.println("Object badNameA HashCode :" + badNameA.hashCode());
      System.out.println("Object badNameB HashCode :" + badNameB.hashCode());
      
      HashSet<BadName> setBadName = new HashSet<BadName>();
      setBadName.add(badNameA);
      System.out.println("Object Equality  : " + badNameA.equals(badNameB));
      System.out.println("HastSet Contains : " + setBadName.contains(badNameB));

      System.out.println();
      System.out.println("----------------------------------------------");
      System.out.println("Override equals and hashCode");
      System.out.println("----------------------------------------------");      
      GoodName goodNameA = new GoodName("Peace");
      GoodName goodNameB = new GoodName("Peace");   
      System.out.println("Object goodNameA HashCode :" + goodNameA.hashCode());
      System.out.println("Object goodNameB HashCode :" + goodNameB.hashCode());
      
      HashSet<GoodName> setGoodName = new HashSet<GoodName>();
      setGoodName.add(goodNameA);
      System.out.println("Object Equality  : " + goodNameA.equals(goodNameB));
      System.out.println("HastSet Contains : " + setGoodName.contains(goodNameB));
   }
}


class NoOverride
{
   String name;
   
   public NoOverride (String name)
   {
      this.name = name;
   }
}

class BadName
{
   String name;
   
   public BadName (String name)
   {
      this.name = name;
   }
   
   @Override
   public boolean equals (Object obj)
   {
      if (obj == null)
         return false;
      
      if (this == obj)
         return true;
      
      if (this.getClass() != obj.getClass())
         return false;
      
      BadName badName = (BadName)obj;
      return this.name.equals(badName.name);
   }
}

class GoodName
{
   private static final int HASH_PRIME =17;
   
   String name;   
   
   public GoodName (String name)
   {
      this.name = name;
   }
   
   @Override
   public boolean equals (Object obj)
   {
      if (obj == null)
         return false;
      
      if (this == obj)
         return true;
      
      if (this.getClass() != obj.getClass())
         return false;
      
      GoodName goodName = (GoodName)obj;
      return this.name.equals(goodName.name);
   }
   
   @Override
   public int hashCode ()
   {
      return HASH_PRIME + name.hashCode();
   }
}
