package principle.enumeration;

public class EnumAbstractMethod
{
   public static void main (String arg[])
   {
      ArithmeticOperation op = ArithmeticOperation.PLUS;
      double x = 43;
      double y = 5;
      System.out.println(x + " " + ArithmeticOperation.PLUS + " " + y + " = " + op.apply(x, y));
      
      ArithmeticOperation.PLUS.status = 1;
      System.out.println("Status : " + ArithmeticOperation.MINUS.status);
      System.out.println("Status : " + ArithmeticOperation.PLUS.status);
      
   }
}

enum ArithmeticOperation
{
   PLUS
   {      
      double apply(double x, double y)
      {
         return x + y;
      }
   },
   
   MINUS
   {
      double apply(double x, double y)
      {
         return x - y;
      }
   },
   
   TIMES
   {
      double apply(double x, double y)
      {
         return x * y;
      }
   },
   
   DIVIDE
   {
      double apply(double x, double y)
      {
         return x / y;
      }
   };
   
   /* Note: The abstract method ensures that every enum implements the function. */
   abstract double apply (double x, double y);
   
   /* This is to demo that status variable is created for every enum */
   public int status = 0;
}
