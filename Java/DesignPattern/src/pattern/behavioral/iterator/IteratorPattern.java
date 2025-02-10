package pattern.behavioral.iterator;

import java.util.*;

/**
 * <pre>
 * 
 * Iterator Pattern
 * -----------------
 * Provides a way to iterate the aggregate object without exposing the underlying implementation.
 * 
 *
 *</pre>
 */
public class IteratorPattern
{
   public static void main (String arg[])
   {
      Iterable<Item> iterable = null ;
      
      System.out.println ("----- As -----");
      iterable = new ConcreteIterableA ();
      for (Item item : iterable)
         System.out.println (item);
      
      System.out.println ("----- Bs -----");
      iterable = new ConcreteIterableB ();
      for (Item item : iterable)
         System.out.println (item);
      
   }
}

/* --------------------- Items ---------------------- */
class Item
{
   private String name;
   
   Item (String name)
   {
      this.name = name;
   }
   
   public String toString ()
   {
      return name;
   }
}


/* ------------------- Aggregate -------------------- */

class ConcreteIterableA implements Iterable<Item>
{
   private String name[] = { "ItemA1", "ItemA2", "ItemA3" };
   
   private Item item[] = null;
   
   public ConcreteIterableA ()
   {
      item = new Item [name.length];
      for (int i = 0; i < item.length; ++i)
         item[i] = new Item (name[i]);
   }

   @Override
   public Iterator<Item> iterator ()
   {
      return new ConcreteIteratorA<Item> (item);
   }
}

class ConcreteIterableB implements Iterable<Item>
{
   private String name[] = { "ItemB1", "ItemB2", "ItemB3" };
   
   private Item item[] = null;
   
   public ConcreteIterableB ()
   {
      item = new Item [name.length];
      for (int i = 0; i < item.length; ++i)
         item[i] = new Item (name[i]);
   }

   @Override
   public Iterator<Item> iterator ()
   {
      return new ConcreteIteratorB<Item> (item);
   }
}

/* ------------------- Iterators -------------------- */

class ConcreteIteratorA<T> implements Iterator<T>
{
   private List<T> listData = null;
   
   private Iterator<T> iter = null;
   
   public ConcreteIteratorA (T item[])
   {
      listData = new ArrayList<T> (Arrays.asList(item));
      iter = listData.iterator();
   }

   @Override
   public boolean hasNext()
   {
      return iter.hasNext();
   }

   @Override
   public T next()
   {
      return iter.next();
   }

   @Override
   public void remove()
   {
      iter.remove();
   }
}

class ConcreteIteratorB<T> implements Iterator<T>
{
   private T data[] = null;
   
   int currIndex = 0;
   
   public ConcreteIteratorB (T data[])
   {
      this.data = data;
   }

   @Override
   public boolean hasNext()
   {
      return (currIndex != data.length) ? true : false; 
   }

   @Override
   public T next()
   {
      if (currIndex == data.length)
         throw new IllegalStateException("Reached end of Iterator " + currIndex);
      return data[currIndex++];
   }

   @Override
   public void remove()
   {
      throw new IllegalStateException();
   }
}



