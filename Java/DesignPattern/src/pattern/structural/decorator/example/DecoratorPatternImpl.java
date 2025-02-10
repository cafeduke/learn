/**
 * 
 */
package pattern.structural.decorator.example;


/**
 *
 * @author Raghunandan.Seshadri
 * @owner  Raghunandan.Seshadri
 */
public class DecoratorPatternImpl
{
   public static void main (String arg[])
   {
      System.out.println("Hot Chocolate Magic");
      System.out.println("-------------------");
      Dessert hotColdMagic = new Jamun(new ChocoSauce(new Cherry(new Vanilla())));
      System.out.println("Description: " + hotColdMagic.getDescribtion());
      System.out.println("Total Cost : " + hotColdMagic.getCost());
      
      System.out.println();
      System.out.println("Mango Delight      ");
      System.out.println("-------------------");
      Dessert mangoDelight = new GreenAppleSauce(new Cherry(new Almond(new Mango())));
      System.out.println("Description: " + mangoDelight.getDescribtion());
      System.out.println("Total Cost : " + mangoDelight.getCost());      
   }
}

/* --------------------------------  Component ----------------------------- */

abstract class Dessert
{
   public abstract String getDescribtion ();
   public abstract int getCost ();
}

/* ----------------------------- Concrete Components ----------------------- */

class Vanilla extends Dessert
{
   private String className = this.getClass().getSimpleName();
   
   private static final int COST = 15;
   
   public String getDescribtion ()
   {
      return "[ " + className + "(" + COST + ") ]";
   }
   
   @Override
   public int getCost()
   {
      return COST;
   }
}

class Chocolate extends Dessert
{
   private String className = this.getClass().getSimpleName();
   
   private static final int COST = 25;
   
   public String getDescribtion ()
   {
      return "[ " + className + "(" + COST + ") ]";
   }

   @Override
   public int getCost()
   {
      return COST;
   }
}

class Mango extends Dessert
{
   private String className = this.getClass().getSimpleName();
   
   private static final int COST = 30;
   
   public String getDescribtion ()
   {
      return "[ " + className + "(" + COST + ") ]";
   }

   @Override
   public int getCost()
   {
      return COST;
   }
}

/* ----------------------------- Abstract Decorator ----------------------- */

abstract class Topping extends Dessert
{
   private Dessert dessert;
   
   Topping (Dessert dessert)
   {
      this.dessert = dessert;
   }
}

/* --------------------------------  Decorator ----------------------------- */

class Cherry extends Topping
{
   private String className = this.getClass().getSimpleName();
   
   private static final int COST = 5;
   
   private Dessert dessert = null;
   
   Cherry (Dessert dessert)
   {
      super(dessert);
      this.dessert = dessert;
   }
   
   @Override
   public String getDescribtion ()
   {
      return "[ " + className + "(" + COST + ") " + dessert.getDescribtion() + " ]";
   }

   @Override
   public int getCost()
   {
      return COST + dessert.getCost();
   }   
}

class Almond extends Topping
{
   private String className = this.getClass().getSimpleName();
   
   private static final int COST = 17;
   
   private Dessert dessert = null;
   
   Almond (Dessert dessert)
   {
      super(dessert);
      this.dessert = dessert;
   }   
   
   @Override
   public String getDescribtion ()
   {
      return "[ " + className + "(" + COST + ") " + dessert.getDescribtion() + " ]";
   }

   @Override
   public int getCost()
   {
      return COST + dessert.getCost();
   }   
}

class GreenAppleSauce extends Topping
{
   private String className = this.getClass().getSimpleName();
   
   private static final int COST = 10;

   private Dessert dessert = null;
   
   GreenAppleSauce (Dessert dessert)
   {
      super(dessert);
      this.dessert = dessert;
   }
   
   @Override
   public String getDescribtion ()
   {
      return "[ " + className + "(" + COST + ") " + dessert.getDescribtion() + " ]";
   }

   @Override
   public int getCost()
   {
      return COST + dessert.getCost();
   }   
}

class ChocoSauce extends Topping
{
   private String className = this.getClass().getSimpleName();
   
   private static final int COST = 12;
   
   private Dessert dessert = null;
   
   ChocoSauce (Dessert dessert)
   {
      super(dessert);
      this.dessert = dessert;
   }   
   
   @Override
   public String getDescribtion ()
   {
      return "[ " + className + "(" + COST + ") " + dessert.getDescribtion() + " ]";
   }

   @Override
   public int getCost()
   {
      return COST + dessert.getCost();
   }   
}

class Jamun extends Topping
{
   private String className = this.getClass().getSimpleName();
   
   private static final int COST = 11;
   
   private Dessert dessert = null;
   
   Jamun (Dessert dessert)
   {
      super(dessert);
      this.dessert = dessert;
   }   
   
   @Override
   public String getDescribtion ()
   {
      return "[ " + className + "(" + COST + ") " + dessert.getDescribtion() + " ]";
   }

   @Override
   public int getCost()
   {
      return COST + dessert.getCost();
   }   
}
