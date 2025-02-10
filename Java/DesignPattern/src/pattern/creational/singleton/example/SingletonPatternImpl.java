package pattern.creational.singleton.example;

import java.util.*;

public class SingletonPatternImpl
{
   public static void main (String arg[])
   {
      String str[] = { "Air", "Zen", "Aeroplane", "Zebra", "Dutch", "Basket" };
      List<String> list = null;
      
      list = Arrays.asList(str);
      System.out.println("Length Comparator");
      System.out.println("-----------------");
      Collections.sort(list, StringLengthComparator.getInstance());
      System.out.println(list);
      System.out.println();
      
      list = Arrays.asList(str);
      System.out.println("Descending Comparator");
      System.out.println("---------------------");      
      Collections.sort(list, StringDescComparator.getInstance());
      System.out.println(list);
   }
}

/**
 * Singleton Length Comparator
 */
class StringLengthComparator implements Comparator<String>
{
   private static final StringLengthComparator INSTANCE = new StringLengthComparator();
   
   private StringLengthComparator ()
   {
      
   }
   
   public static StringLengthComparator getInstance ()
   {
      return INSTANCE;
   }
   
   @Override
   public int compare (String str1, String str2)
   {
      return (str1.length() - str2.length());
   }
}

/**
 * Singleton Length Comparator
 */
class StringDescComparator implements Comparator<String>
{
   private static final StringDescComparator INSTANCE = new StringDescComparator ();
   
   private StringDescComparator ()
   {
      
   }
   
   public static StringDescComparator getInstance ()
   {
      return INSTANCE;
   }
   
   @Override
   public int compare (String str1, String str2)
   {
      return -1 * str1.compareTo(str2);
   }
}

