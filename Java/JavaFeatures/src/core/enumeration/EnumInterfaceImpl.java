package core.enumeration;

interface Operation
{
   double apply(double x, double y);
}

enum BasicOperation implements Operation
{
   PLUS("+")
   {
      public double apply(double x, double y)
      {
         return x + y;
      }
   },
   
   MINUS("-")
   {
      public double apply(double x, double y)
      {
         return x - y;
      }
   },
   
   TIMES("*")
   {
      public double apply(double x, double y)
      {
         return x * y;
      }
   },
   
   DIVIDE("/")
   {
      public double apply (double x, double y)
      {
         return x / y;
      }
   };
   
   private final String symbol;

   BasicOperation(String symbol)
   {
      this.symbol = symbol;
   }

   @Override
   public String toString()
   {
      return symbol;
   }
}

public class EnumInterfaceImpl
{
   public static void main (String arg[])
   {
      BasicOperation op = BasicOperation.TIMES;
      double x = 43;
      double y = 5;
      System.out.println("" + x + BasicOperation.TIMES + y + " = " + op.apply(x, y));
   }
}

