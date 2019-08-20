package lambda.basic;

public class L05MethodReference
{
   public static class Lemon
   {
      public Lemonade getLemonade (Sugar sugar)
      {
         return new Lemonade ();
      }
   }
   
   public static class Sugar    
   {
      public Lemonade getLemonade (Lemon lemon)
      {
         return new Lemonade ();
      }
   }
   
   public static class Lemonade 
   { 
      public static Lemonade getInstance (Lemon lemon, Sugar sugar)
      {
         return new Lemonade ();
      }       
   } 
   
   @FunctionalInterface
   public static interface CoolRefresher
   {
      Lemonade make (Lemon lemon, Sugar sugar);
   }
   
   public static class JuiceJunction
   {
      public Lemonade prepareLemonade (Lemon lemon, Sugar sugar)
      {
         return new Lemonade ();
      }
   }   
   
   public static void main (String arg[])
   {
      Lemon lemon = new Lemon ();
      
      Sugar sugar = new Sugar ();
      
      CoolRefresher refresher = null;
      refresher = (l,s) -> l.getLemonade(s);
      
      /**
       * Reference to a Static Method
       * ---------------------------------------
       * 
       * Note: 
       *  - Lemonade's getInstance method accepts Lemon and Sugar and returns Lemonade
       *  - Lemonade's getInstance method is STATIC.
       * Hence, Lemonade's getInstance method can be used as lamda expression.
       */  
      refresher = Lemonade::getInstance;
      refresher.make(lemon, sugar);
      
      /**
       * Reference to an Instance Method of a Particular Object
       * ------------------------------------------------------
       *    - Instance Method   : JuiceJunction's prepareLemonade
       *    - Particular Object : jjJayanagar
       *    
       * Note: 
       *  - JuiceJunction's prepareLemonade method accepts Lemon,Sugar and returns Lemonade.
       *  - JuiceJunction's prepareLemonade is an INSTANCE method.
       *  - CoolRefresher is assigned the PARTICULAR JuiceJunction's object 'jjJayanagar' to be used to call the INSTANCE method.  
       *  - refresher.make(lemon, sugar) is equivalent of invoking "jJayanagar.prepareLemonade (lemon,sugar)"
       * 
       * Here, we are asking Java to use the particular object "jjJayanagar" to invoke a function "prepareLemonade"
       * which has the same arguments and return type as "make".
       * 
       */
      JuiceJunction jjJayanagar = new JuiceJunction ();
      refresher = jjJayanagar::prepareLemonade;
      refresher.make(lemon, sugar);
      
      
      /**
       * Reference to an Instance Method of an Arbitrary Object of a Particular Type
       * --------------------------------------------------------------------------- 
       *    - Instance Method   : Lemon's getLemonade
       *    - Arbitrary Object  : Arbitrary Lemon Object ( not known during assignment )
       *    -
       * 
       * Here,
       *  - CoolRefresher requires a method body to accept Lemon,Sugar and returns Lemonade.
       *  - Lemon's getLemonade method (INSTANCE METHOD) accepts Sugar and returns Lemonade.
       *  - refresher.make(lemon, sugar) is equivalent of invoking "lemon.getLemodate (sugar)" 
       */
      refresher = Lemon::getLemonade;
      refresher.make(lemon, sugar);
      

   }
   

}
