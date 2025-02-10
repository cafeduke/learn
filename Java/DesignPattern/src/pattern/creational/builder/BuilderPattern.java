package pattern.creational.builder;

/**
 * The BuilderPattern
 * --------------------
 * Builder pattern defines a builder inner class that builds the enclosing class.
 *    - This prevents the enclosing class from having multiple argument constructor
 *    - This helps the enclosing class to be immutable.
 *    
 * Details
 * -------   
 * Builder pattern takes effect when there is a class the requires a multiple argument constructor.
 * Some of the arguments are mandatory and many others are optional.
 * 
 * Possible bad solutions
 * ----------------------
 * Multiple Constructors:
 *    - Requires the programmer to carefully check "what argument stands for what purpose"
 *    - Requires the reader to cross check with the documentation to understand the code.
 *    
 * Java Bean Pattern
 *    - The Java bean sets all the optional arguments as setters.
 *    - Since we are using multiple setter methods, there is a point at which the object is created, but is in
 *      invalid/inconsistent state, as all the necessary setter are not yet executed.
 *    - This makes the object mutable as a setter can be called at any given point of time.  
 */
public class BuilderPattern
{
   public static void main (String arg[])
   {
      Outer.Builder builder = new Outer.Builder("ParamA", "ParamB").addOptionC("ParamC").addOptionE("ParamE");
      Outer outer = builder.build();
      System.out.println(outer);
      
   }
}

interface Builder<T>
{
   public T build ();
}

class Outer
{
   private String mandatoryParamA = null;
   
   private String mandatoryParamB = null;
   
   private String optionalParamC = null;
   
   private String optionalParamD = null;
   
   private String optionalParamE = null;   
   
   private Outer (Builder builder)
   {
      this.mandatoryParamA = builder.builderParamA;
      this.mandatoryParamB = builder.builderParamB;
      this.optionalParamC =  builder.builderParamC;
      this.optionalParamD =  builder.builderParamD;
      this.optionalParamE =  builder.builderParamE;
   }
   
   public String toString ()
   {
      return "[ParamA=" + mandatoryParamA + "] " +
      "[ParamB=" + mandatoryParamB + "] " +
      "[ParamC=" + optionalParamC + "] " +
      "[ParamD=" + optionalParamD + "] " +
      "[ParamE=" + optionalParamE + "] ";
   }
   
   public static class Builder
   {
      private String builderParamA = null;
      
      private String builderParamB = null;
      
      private String builderParamC = null;
      
      private String builderParamD = null;
      
      private String builderParamE = null;      
      
      public Builder (String builderParamA, String builderParamB)
      {
         this.builderParamA = builderParamA;
         this.builderParamB = builderParamB;
      }
      
      public Builder addOptionC (String optionalParamC)
      {
         this.builderParamC = optionalParamC;
         return this;
      }
      
      public Builder addOptionD (String optionalParamD)
      {
         this.builderParamD = optionalParamD;
         return this;
      }

      public Builder addOptionE (String optionalParamE)
      {
         this.builderParamE = optionalParamE;
         return this;
      }
      
      public Outer build ()
      {
         return new Outer (this);
      }
   }
}













