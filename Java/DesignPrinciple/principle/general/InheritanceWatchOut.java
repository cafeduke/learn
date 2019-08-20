package principle.general;

/**
 * There are several disadvantages of inheritance
 *    - Inheritance is implementation dependent
 *    - Inheritance propagates flaws (if any) to the subclass
 *    - Changes to super class in further releases MAY break the working of subclass 
 *       - A subclass is free to add new methods to the extension
 *       - If the future release of super class has same signature
 *          - There is a an unintended overriding
 *       - If the future release of super class has same arguments but different return type
 *          - The sub class is broken and fails to compile.          
 */
public class InheritanceWatchOut
{
   public static void main (String arg[])
   {
      B b = new B ();
      b.fun2 ();
   }
}

/* Inheritance is implementation dependent */

class A
{
   public void fun1 ()
   {
      System.out.println("A fun1");
   }
   
   public void fun2 ()
   {
      this.fun1 ();
      System.out.println("A fun2");
   }
}


class B extends A
{
   public void fun1 ()
   {
      System.out.println("B fun1");
   }
   
   /**
    * The subclass calls super.fun2 ()
    *    - It might be unexpected for class B to expect that this in turn calls B.fun1()
    *    - This is because A.fun2 () in turn calls A.fun1 () since A.fun1() is overridden
    *      B.fun1 () is called instead.
    *      
    * This might result in unexpected behavior because class B cannot expect the above
    * behavior unless it knows the working of A.fun2 ().
    */
   public void fun2 ()
   {
      System.out.println("B fun2");
      super.fun2();
   }   
}



