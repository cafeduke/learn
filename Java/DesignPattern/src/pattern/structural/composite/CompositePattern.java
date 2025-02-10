package pattern.structural.composite;

import java.util.*;

/**
 * Compose objects into tree structures to represent part-whole hierarchies.
 * Allows individual objects and compositions to be treated uniformly. 
 *
 */
public class CompositePattern
{
   public static void main (String arg[])
   {
      Composite root = new Composite ("Root");
      
      Composite a1 = new Composite ("A.1");
      Composite b1 = new Composite ("B.1");
      Composite c1 = new Composite ("C.1");
      
      Composite a11  = new Composite ("A.1.1");
      Leaf      a111 = new Leaf      ("A.1.1.1");      
      Leaf      a12  = new Leaf      ("A.1.2");
      
      Composite b11  = new Composite ("B.1.1");
      Leaf      b111 = new Leaf      ("B.1.1.1");
      Leaf      b12  = new Leaf      ("B.1.2");
      
      
      Leaf      c11  = new Leaf       ("C.1.1");
      
      root.addChild(a1);   root.addChild(b1);    root.addChild(c1);
      a1.addChild(a11);    a1.addChild(a12);
      a11.addChild(a111);
      
      b1.addChild(b11);    b1.addChild(b12);
      b11.addChild(b111);
      
      c1.addChild(c11);
      
      System.out.println (root.getHierarchy());
      System.out.println ("------------------------");
      
      Iterator<Component> iter = root.iterator();
      while (iter.hasNext())
         System.out.println (iter.next());
   }
}

abstract class Component
{
   private String name = null;
   
   public Component (String name)
   {
      this.name = name;
   }
   
   public void operation ()
   {
      throw new UnsupportedOperationException("Method operation is unsupported");
   }
   
   public void addChild (Component component)
   {
      throw new UnsupportedOperationException("Method addChild is unsupported");
   }
   
   public boolean isLeaf ()
   {
      throw new UnsupportedOperationException("Method addChild is unsupported");
   }
   
   public String toString ()
   {
      return name;
   }
   
   public String getName ()
   {
      return name;
   }
   
   public abstract Iterator<Component> iterator ();
}

class Leaf extends Component
{
   public Leaf (String name)
   {
      super(name);
   }
   
   public void operation ()
   {
      System.out.println ("Leaf Level Operation");
   }
   
   public boolean isLeaf ()
   {
      return true;
   }

   @Override
   public Iterator<Component> iterator()
   {
      return new NullIterator();
   }
   
   private class NullIterator implements Iterator<Component>
   {
      @Override
      public boolean hasNext()
      {
         return false;
      }

      @Override
      public Component next()
      {
         return null;
      }

      @Override
      public void remove()
      {
         throw new UnsupportedOperationException ("Remove not supported on NullIterator");
      }
   }
}

class Composite extends Component
{
   private List<Component> listComp = new ArrayList<Component> ();
   
   private static final String TAB = "   ";
   
   private static final String NEWLINE = System.getProperty("line.separator");
   
   public Composite (String name)
   {
      super (name);
   }

   public void addChild (Component component)
   {
      listComp.add (component);
   }
   
   public String getHierarchy ()
   {
      return getHierarchy ("");
   }
   
   private String getHierarchy (String tab)
   {
      StringBuffer buffer = new StringBuffer (getName ());
      tab = tab + TAB;
      
      for (Component currComp : listComp)
      {
         if (currComp.isLeaf())
            buffer.append(NEWLINE + tab + currComp.toString());
         else
            buffer.append (NEWLINE + tab + ((Composite)currComp).getHierarchy(tab));
      }
      return buffer.toString();
   }
   
   @Override
   public boolean isLeaf ()
   {
      return false;
   }

   @Override
   public Iterator<Component> iterator()
   {
      return new CompositeIterator (listComp.iterator());
   }
   
   private class CompositeIterator implements Iterator<Component>
   {
      private Stack<Iterator<Component>> stackIterator = null;
      
      public CompositeIterator (Iterator<Component> iterator)
      {
         stackIterator = new Stack<Iterator<Component>>();
         stackIterator.push(iterator);
      }

      @Override
      public boolean hasNext()
      {
         if (stackIterator.isEmpty())
            return false;
         
         Iterator<Component> topIterator = stackIterator.peek();
         if (topIterator.hasNext())
         {
            return true;
         }
         else
         {
            stackIterator.pop();
            return hasNext ();
         }
      }

      @Override
      public Component next()
      {
         if (!hasNext())
            return null;
         
         Iterator<Component> topIterator = stackIterator.peek();
         Component currComp =  topIterator.next();

         if (!(topIterator instanceof CompositeIterator))
            stackIterator.push(currComp.iterator());
         
         return currComp;
      }

      @Override
      public void remove()
      {
         throw new UnsupportedOperationException("Remote not supported in CompositeIterator");
      }
   }
}



