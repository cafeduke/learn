package pattern.creational.singleton;

import java.lang.reflect.*;

/**
 * Singleton pattern - Lazy Instantiation With Double Checked Locking
 * ------------------------------------------------------------------
 * If all instances of a class are functionally equivalent consider making 
 * the class a Singleton.
 * Example :
 *    - Class having no private fields.
 *    - Class the implements Comparator<T>
 */
public class SingletonPattern implements Runnable
{
   
   Singleton singleton = null;
   
   public SingletonPattern ()
   {
   }   
   
   public static void main (String arg[]) throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException
   {
      Method method= Singleton.class.getDeclaredMethod("getInstance");
      System.out.println(method.invoke(null));
      System.out.println(method.invoke(null));
      System.out.println(method.invoke(null));
      System.out.println(method.invoke(null));
   }
   
   public void testWithThread ()
   {
      SingletonPattern singletonPattern = new SingletonPattern();
      Thread t[] = new Thread[3];
      for (int i = 0; i < t.length; ++i)
         t[i] = new Thread (singletonPattern, "t" + (i+1));
      
      for (int i = 0; i < t.length; ++i)
         t[i].start();   
   }

   @Override
   public void run()
   {
      for (int i = 0; i < 5; ++i)
      {
         System.out.println("[Thread=" + Thread.currentThread().getName() + "] " + Singleton.getInstance());
      }
   }
}

class A
{
   
}

class Singleton
{
   private static Singleton instance = null;
   
   private int randNum = -1;
   
   /* Note: Constructor is private */
   private Singleton ()
   {
      randNum = (int)(Math.random() * 100);
   }
   
   /* Note: Static factory to provide the object */
   public static Singleton getInstance ()
   {
      /* First check */
      if (instance == null)
      {
         synchronized (Singleton.class)
         {
            /* Second check */
            if (instance == null)
               instance = new Singleton();
         }
      }
      return instance;
   }
   
   @Override
   public String toString ()
   {
      return "[Rand=" + randNum + "]";
   }
}


