package lambda.basic;

import lambda.basic.myfunc.*;

/**
 * Extension Method
 * ---------------- 
 * Java 8 enables us to add non-abstract method implementations to interfaces by utilizing the "default" keyword.
 */
public class L04ExtensionMethodVsAbstractClass
{
   public static void main (String arg[])
   {
      /**
       * Works: The default method in interface is visible
       */
      MyFormulaA fA = new MyFormulaA ();
      System.out.println(fA.sqrt(5));
      
      /**
       * Error: The default method in abstract class is not visible
       */
      @SuppressWarnings("unused")
      MyFormulaB fB = new MyFormulaB ();
      // System.out.println(fB.sqrt (5));
   }
}


class MyFormulaA implements Formula
{
   @Override
   public int random (int a)
   {
      return (int) (Math.random() * a);
   }
}

class MyFormulaB extends AbstractFormula
{
   @Override
   public double calculate(int a)
   {
      return (int) (Math.random() * a);
   }
}



