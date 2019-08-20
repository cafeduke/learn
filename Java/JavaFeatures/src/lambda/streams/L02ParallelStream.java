package lambda.streams;

import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.*;
import util.Util;

public class L02ParallelStream
{
   public static void main (String arg[])
   {
      // Create a huge list
      int max = 1000000;
      List<String> list = new ArrayList<>(max);
      for (int i = 0; i < max; i++) 
      {
          UUID uuid = UUID.randomUUID();
          list.add(uuid.toString());
      }
      
      // Sequential Sort
      Util.printHeading("Sequential Stream");
      doSort (list.stream());

      // Parallel Sort
      Util.printHeading("Parallel Stream");
      doSort (list.parallelStream());
   }
   
   private static void doSort (Stream<String> stream)
   {
      Util.print ("Started");
      long timeStart = System.nanoTime();
      stream.sorted();
      long timeEnd = System.nanoTime();
      Util.print ("Finished");      
      long timeInMicro = TimeUnit.NANOSECONDS.toMicros (timeEnd - timeStart);
      System.out.println(String.format("Time Taken = %d mirco sec", timeInMicro));
   }
}
