package pattern.structural.facade;

/**
 * Facade Pattern
 * ---------------
 * Provides a high-level interface to a set of classes ( each provided by 
 * a different subsystem ) making the subsystem easy to use.
 * 
 * Design Principle : Principle Of Least Knowledge  
 * -----------------------------------------------
 * Talk only to your immediate friends.
 * 
 * Following are immediate friends :
 * 1) this object
 * 2) Object received from a function's parameter
 * 3) Local Object created in current function
 * 4) Member object of the class 
 */
public class FacadePattern
{
   public static void main (String arg[])
   {
      ConcreteFacade concreteFacade = new ConcreteFacade();
      concreteFacade.operationA();
   }
}

/* ---------- Facade ---------- */

interface Facade
{
   public void operationA ();
}

class ConcreteFacade implements Facade
{
   private SubSystemA1 subSystemA1 = null;
   
   private SubSystemA2 subSystemA2 = null;
   
   private SubSystemA3 subSystemA3 = null;
   
   
   public ConcreteFacade()
   {
      subSystemA1 = new SubSystemA1();
      subSystemA2 = new SubSystemA2();
      subSystemA3 = new SubSystemA3();
   }
   
   public void operationA()
   {
      subSystemA1.operationA1();
      subSystemA2.operationA2();
      subSystemA3.operationA3();
   }
}

/* ---------- SubSystem ---------- */

class SubSystemA1
{
   public void operationA1 ()
   {
      System.out.println ("Perform operation A1");
   }
}

class SubSystemA2
{
   public void operationA2 ()
   {
      System.out.println ("Perform operation A2");
   }
}

class SubSystemA3
{
   public void operationA3 ()
   {
      System.out.println ("Perform operation A3");
   }
}
