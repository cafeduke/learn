package lambda.collection;

import java.util.*;
import util.Util;

public class MapUsage
{
   public static void main (String arg[])
   {
      // Add contents to Map
      Map<Integer, String> map = new HashMap<>();
      for (int i = 0; i < 10; i++) 
         map.putIfAbsent(i, "val" + i);
      
      // Print contents of Map
      Util.printHeading ("Contents of a map");
      map.forEach( (key,val) -> System.out.println (key + " -> " + val));
      
      // Modify selected mapping
      Util.printHeading ("Modify values for key=2 and key=11");
      map.compute(2, (key, val) -> "Two");
      map.compute(11, (key, val) -> "Eleven");
      System.out.println("2->" + map.get(2));
      System.out.println("11->" + map.get(11));      
      
      // Compute
      Util.printHeading ("Remove key=5");
      map.compute(5, (key, val) -> "Key="+ key + ",Value=" + val);
      System.out.println("5->" + map.get(5));      

      
      Util.printHeading ("Modify 12 if present");
      map.computeIfPresent(12, (key, val) -> "Twelve");
      System.out.println("12->" + map.get(12));
      map.forEach( (key,val) -> System.out.println (key + " -> " + val));
      
      Util.printHeading ("Modify 13 if absent");
      map.computeIfAbsent (13, (key) -> null);
      System.out.println ("13->" + map.get(13));
      
      map.forEach( (key,val) -> System.out.println (key + " -> " + val));
   }
}
