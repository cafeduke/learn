package lambda.streams;

import java.util.*;
import java.util.stream.*;
import java.util.function.*;
import util.Util;

public class L01Basic
{
   public static void main (String arg[])
   {
      List<String> list = Arrays.asList ("zebra", "man", "deer", "apple", "sparrow", "orange", "jug", "squirrel", "parrot", "apple", "kite");
      Consumer<String> consumerDisplay = (s) -> System.out.println(s);
      
      // List content of the stream
      Util.printHeading ("Iterate");
      list.stream()
          .forEach(consumerDisplay);
      
      // Remove duplicate, Filter & Sort
      Util.printHeading ("Distinct, Filter & Sort");
      list.stream()
          .distinct()
          .filter((s) -> s.length() > 3)
          .sorted()
          .forEach(consumerDisplay);
      
      
      // Remove duplicate, Filter & Sort
      Util.printHeading ("Distinct, Filter, Filter & Sort");
      list.stream()
          .distinct()
          .filter(s -> s.length() > 3)
          .filter(((Predicate<String>)s -> s.matches("[aeiou].*")).negate())
          .sorted()
          .forEach(consumerDisplay);
      
      
   }
}
