package pattern.behavioral.template;

/**
 * Template Pattern
 * ----------------
 * Template pattern defines a skeleton of an algorithm in a method, deferring some
 * of the steps to subclasses. Subclasses may optionally override the hooks provided
 * by the abstract class and have a controlled customization of the algorithm.  
 */
public class TemplatePattern
{
   public static void main (String arg[])
   {
      ConcreteA concreteA =  new ConcreteA();
      ConcreteB concreteB =  new ConcreteB();
      
      System.out.println("-------------------------");
      concreteA.templateMethod();
      
      System.out.println("-------------------------");
      concreteB.templateMethod();
   }
}

abstract class Template
{
   /**
    * NOTE:
    * The template method is declared final to prevent subclasses
    * for changing the algorithm. 
    */
   public final void templateMethod ()
   {
      doOperation1();
      doOperation2();
      doOperation3();
      if (hook())
         doOperation4();
   }
   
   /**
    * NOTE:
    * The implemented methods are declared final to prevent subclasses from overriding.
    */
   public final void doOperation1 ()
   {
      System.out.println("Operation 1");
   }
   
   public final void doOperation2 ()
   {
      System.out.println("Operation 2");
   }
   
   public abstract void doOperation3 ();
   
   public final void doOperation4 ()
   {
      System.out.println("Operation 4");
   }
   
   /**
    * NOTE:
    * A hook is not declared final because we want the subclasses to have the flexibility 
    * to override them thus customizing the algorithm.
    */
   public boolean hook ()
   {
      return true;
   }
}


class ConcreteA extends Template
{
   @Override
   public void doOperation3()
   {
      System.out.println("Operation 3 done in A-style");   
   }
}

class ConcreteB extends Template
{
   @Override
   public void doOperation3()
   {
      System.out.println("Operation 3 done in B-style");   
   }

   @Override
   public boolean hook()
   {
      return false;
   }
}




