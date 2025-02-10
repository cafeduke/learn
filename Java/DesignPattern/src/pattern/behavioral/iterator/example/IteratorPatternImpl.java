package pattern.behavioral.iterator.example;

public class IteratorPatternImpl
{
   public static void main (String arg[])
   {
      System.out.println ("----- Coffee -----");
      HomeDeliveryPack.pack(new CoffeeShop ());
      
      System.out.println ("----- Tea -----");
      HomeDeliveryPack.pack(new TeaShop());
   }
}

class HomeDeliveryPack
{
   public static void pack (Aggregate aggregate)
   {
      
      Iterator<Beverage> iter = aggregate.createIterator();
      while (iter.hasNext())
         System.out.println ("Packing [" + iter.next() + "]");
      
      iter.last();
      while (iter.hasPrevious())
         System.out.println ("Sealing [" + iter.previous() + "]");
   }
}

/* ------------------- Aggregate -------------------- */

interface Aggregate
{
   public Iterator<Beverage> createIterator ();
   
}

class CoffeeShop implements Aggregate
{
   private static final String name[] = { "Cappuccino", "CafeLate", "Expresso" };
   
   public Coffee coffee[] = null;
   
   public CoffeeShop ()
   {
      coffee = new Coffee[name.length];
      for (int i = 0; i < coffee.length; ++i)
         coffee[i] = new Coffee (name[i]);
   }

   @Override
   public Iterator<Beverage> createIterator()
   {
      return new ConcreteArrayIterator<Beverage>(coffee);
   }
}

class TeaShop implements Aggregate
{
   private static final String name[] = { "Assam", "Darjeeling", "TataTea" };
   
   public Tea tea[] = null;
   
   public TeaShop ()
   {
      tea = new Tea [name.length];
      for (int i = 0; i < tea.length; ++i)
         tea[i] = new Tea (name[i]);
   }

   @Override
   public Iterator<Beverage> createIterator()
   {
      return new ConcreteArrayIterator<Beverage>(tea);
   }
}


/* ------------------- Iterators -------------------- */

interface Iterator<T>
{
    public boolean hasNext ();
    public T next ();
    
    public boolean hasPrevious ();
    public T previous ();
    
    public T last  ();    
    public T first ();
    
    public T seek (int index);
}

class ConcreteArrayIterator<T> implements Iterator<T>
{
   private T data[] = null;
   
   private int currIndex = 0;
   
   public ConcreteArrayIterator (T data[])
   {
      this.data = data;
   }

   @Override
   public T first()
   {
      currIndex = 0;
      return data[currIndex];
   }

   @Override
   public boolean hasNext()
   {
      return (currIndex != data.length) ? true : false;
   }

   @Override
   public boolean hasPrevious()
   {
      return (currIndex != -1) ? true : false;
   }

   @Override
   public T last()
   {
      currIndex = data.length - 1;
      return data[currIndex];
   }

   @Override
   public T next() throws IllegalStateException
   {
      if (currIndex == data.length)
         throw new IllegalStateException ("Reached end of iteration");
      return data[currIndex++];
   }

   @Override
   public T previous() throws IllegalStateException
   {
      if (currIndex == -1)
         throw new IllegalStateException ("Reached end of iteration");
      return data[currIndex--];
   }

   @Override
   public T seek (int index)
   {
      if (index < 0 || index > data.length - 1)
         throw new IllegalArgumentException("Index " + index + " out of bounds. " +
                   "Valid range [0-" + (data.length - 1));
      currIndex = index; 
      return data[currIndex];
   }   
}

/* ---------------------- Items --------------------- */

abstract class Beverage 
{
   private String name = null;
   
   public Beverage (String name)
   {
      this.name = name;
   }
   
   public String toString ()
   {
      return name;
   }
}

class Coffee extends Beverage
{
   public Coffee(String name)
   {
      super(name);
   }
}


class Tea extends Beverage
{
   public Tea(String name)
   {
      super(name);
   }
}



