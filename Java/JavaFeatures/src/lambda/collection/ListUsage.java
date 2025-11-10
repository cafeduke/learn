package lambda.collection;

import java.util.*;
import java.util.function.*;
import util.Util;

/**
 * This class demonstrates the uses of lamda in the frequently used collection List
 * and array data structure.
 */
public class ListUsage
{
   public static void main (String arg[])
   {
      String name[] = new String [] {"orange", "apple", "chikko", "jackfruit", "mango", "lychee", "papaya", "banana"};
      Consumer<String> doDisplay = (s) -> System.out.println(s);
           
      // Print contents of array/List
      Util.printHeading("forEach");
      Arrays.asList(name).forEach(doDisplay);
      
      // Sort an array/List
      Util.printHeading("sort");
      List<String> list = new ArrayList<String>(Arrays.asList(name));
      list.sort((sA, sB) -> sA.compareTo(sB));
      list.forEach(doDisplay);
      
      // Conditionally removing 
      Util.printHeading("removeIf");
      list = new ArrayList<> (Arrays.asList(name));
      list.removeIf((s) -> s.matches("[aeiou].*"));
      list.forEach(doDisplay);
      
      // Replace All
      Util.printHeading("replaceAll");
      list = Arrays.asList(name);
      list.replaceAll((s) -> s.substring(0, 1).toUpperCase() + s.substring(1));
      list.forEach(doDisplay);
   }
   
   static void aFun(String str) throws Exception{
      
   }
}


