package principle.object;

/**
 * <pre>
 * Overriding Equals Method
 * ------------------------
 * Make sure of the following while overriding the equals method
 *    - Add @Overide annotation to ensure overriding.  
 *    - Equality check using == to equate references to same object.
 *    - If object is not instance of given class
 *       - Return false
 *    - Compare member variables of 'this' with the argument 'object'
 *    - Return false if any comparison fails
 *    - Return true if all the comparisons succeed.
 *    
 * Overriding HashCode Method
 * --------------------------
 *    - Add @Overide annotation to ensure overriding.
 *    - Initialize result to a prime number (say, result = 17).
 *    - For each member (m) involved in equals method
 *        result = HASH_PRIME * result + m.hashCode ();  
 *    - Return result 
 * 
 *  Why add HASH_PRIME * result to itself in hash computation?:
 *  ----------------------------------------------------------
 *    - Ensure that same values for different members results in different hashCode
 *       - Assume the member consists of 3 integers m1, m2, m3;
 *       - Case A: m1 = 1, m2 = 2, m3 = 3, HASH_PRIME = 2 and result = 1;
 *          Step 1) result = 2 * 1 + 1; result = 3 
 *          Step 2) result = 2 * 3 + 2; result = 8
 *          Step 3) result = 2 * 8 + 3; result = 19
 *           
 *       - Case B: m1 = 2, m2 = 3, m3 = 1, HASH_PRIME = 2 and result = 1;
 *          Step 1) result = 2 *  1 + 2; result = 4 
 *          Step 2) result = 2 *  4 + 3; result = 11
 *          Step 3) result = 2 * 11 + 1; result = 23
 *          
 *       - From Case A, B 
 *          - Swapping the values across members results in different hashCode
 *          - Had the formula been as simple as result = result + m.hashCode () both cases would result
 *            in same hashCode = 7. This is bad as it increases collision.
 * </pre>
 */
public class HowToOverrideEqualsHashCode
{
   public static void main(String arg[])
   {
      PhoneNumber p1 = new PhoneNumber(1, 2, 3);
      PhoneNumber p2 = new PhoneNumber(3, 1, 2);

      System.out.println("HashCode of p1=" + p1.hashCode());
      System.out.println("HashCode of p2=" + p2.hashCode());
      System.out.println("HashCode of p2=" + p1);
   }
}

class PhoneNumber
{
   private final short areaCode;

   private final short prefix;

   private final short lineNumber;

   private final int HASH_PRIME = 31;

   public PhoneNumber(int areaCode, int prefix, int lineNumber)
   {
      rangeCheck(areaCode, 999, "area code");
      rangeCheck(prefix, 999, "prefix");
      rangeCheck(lineNumber, 9999, "line number");
      this.areaCode = (short) areaCode;
      this.prefix = (short) prefix;
      this.lineNumber = (short) lineNumber;
   }

   private static void rangeCheck(int arg, int max, String name)
   {
      if (arg < 0 || arg > max)
         throw new IllegalArgumentException(name + ": " + arg);
   }

   @Override
   public boolean equals(Object object)
   {
      if (object == this)
         return true;

      if (!(object instanceof PhoneNumber))
         return false;

      PhoneNumber pn = (PhoneNumber) object;
      return pn.lineNumber == lineNumber && pn.prefix == prefix && pn.areaCode == areaCode;
   }

   @Override
   public int hashCode()
   {
      int result = 0;
      result += HASH_PRIME * areaCode;
      result += HASH_PRIME * prefix;
      result += HASH_PRIME * lineNumber;
      return result;
   }

   @Override
   public String toString()
   {
      return String.format("(%03d)%03d-%04d", areaCode, prefix, lineNumber);
   }
}
