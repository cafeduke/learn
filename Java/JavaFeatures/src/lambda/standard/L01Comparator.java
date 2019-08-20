package lambda.standard;

import java.util.*;

import util.Util;

/**
 * Comparator - Used to compare. (FI: compare)
 * -------------------------------------------
 * A Comparator is used to compare two objects and return an int.
 * Parameters : ObjectA, ObjectB
 * Return     : int
 */
public class L01Comparator
{
   public static void main (String arg[])
   {
      String item [] = new String [] { "zebra", "pen", "apple", "orange", "peacock", "man", "zero" };
      String str[] = null;
      
      Comparator<String> descComparator = Comparator.reverseOrder();
      Comparator<String> lengthComparatorShortFirst = (sA, sB) -> sA.length() - sB.length();
      
      Util.printHeading("Reverse sort");
      str = Arrays.copyOf(item, item.length);
      Arrays.sort (str, descComparator);
      System.out.println(Arrays.toString(str));

      Util.printHeading("Sort by length");
      str = Arrays.copyOf(item, item.length);
      Arrays.sort (str, lengthComparatorShortFirst);
      System.out.println(Arrays.toString(str));
      
      Util.printHeading("Sort by length - reversed");
      Arrays.sort (str, lengthComparatorShortFirst.reversed());
      System.out.println(Arrays.toString(str));
      
   }
}
