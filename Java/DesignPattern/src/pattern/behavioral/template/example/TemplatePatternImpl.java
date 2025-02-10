package pattern.behavioral.template.example;

public class TemplatePatternImpl
{
   public static void main (String arg[])
   {
      System.out.println("----------------------------");
      Coffee coffee = new Coffee ();
      coffee.prepare();
      System.out.println("----------------------------");
      Tea tea = new Tea ();
      tea.prepare();
      
   }
}

abstract class HotBeverage
{
   public HotBeverage ()
   {
      
   }
   
   public abstract String getName ();
   
   public final void boilWater ()
   {
      System.out.println ("Boil water");
   }
   
   public abstract void brew ();
   
   public abstract void addBaseIngredient ();
   
   public final void pourToCup ()
   {
      System.out.println("Pour " + getName () + " into cup");
   }
   
   public final void addFlavour ()
   {
      System.out.println("Adding elichi flavour");
   }
   
   /**
    * Hook
    */
   public boolean needFlavour ()
   {
      return true;
   }
   
   /**
    * NOTE:
    * The template method is declared final to prevent subclasses
    * for changing the algorithm. 
    */
   public final void prepare ()
   {
      boilWater();
      brew ();
      pourToCup();
      addBaseIngredient();   
      if (needFlavour())
         addFlavour();
   }
}


class Coffee extends HotBeverage
{
   @Override
   public void addBaseIngredient()
   {
      System.out.println("Add sugar and milk");
   }

   @Override
   public void brew()
   {
      System.out.println("Brew " + getName () + " in boiling water");
   }

   @Override
   public String getName()
   {
      return "Coffee";
   }

   @Override
   public boolean needFlavour()
   {
      return false;
   }
}

class Tea extends HotBeverage
{
   @Override
   public void addBaseIngredient()
   {
      System.out.println("Add lemon");
   }

   @Override
   public void brew()
   {
      System.out.println("Steep " + getName () + " in boiling water");
   }

   @Override
   public String getName()
   {
      return "Tea";
   }
   
}

