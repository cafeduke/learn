package lambda.basic.myfunc;

@FunctionalInterface
public interface Formula
{   
   public abstract int random (int a);

   // Extension Method
   default double sqrt (double a)
   {
      return Math.sqrt(a);
   }

   // Using extension method in another.
   default double forthroot (double a)
   {
      return sqrt(sqrt (a));
   }   
}
