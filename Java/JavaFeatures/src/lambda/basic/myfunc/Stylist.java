package lambda.basic.myfunc;

@FunctionalInterface
public interface Stylist
{
   public abstract String doStyle (String mesg);
   
   /**
    * Error: @FunctionalInterface can have only one abstract method.
    */
   // public abstract void doSomething (String mesg);
   
   default Stylist padSmile ()
   {
      return (s) -> ":-) " + doStyle(s) + " (-:";
   }
   
   default Stylist padWink ()
   {
      return (s) -> ";-) " + doStyle(s) + " (-;";
   }
}

