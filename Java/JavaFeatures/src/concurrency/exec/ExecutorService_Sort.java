package concurrency.exec;

import java.util.*;
import java.util.concurrent.*;
import util.Util;

/**
 * This class performs the following
 * <ul>
 *    <li> Generate an array with random numbers.
 *    <li> Break the array into fragments
 *    <li> Sort each fragment in parallel using executor service
 *    <li> Merge sorted fragments in parallel using executor service.
 * </ul>
 *
 */
public class ExecutorService_Sort
{
   private static int num[] = null;

   public static void main(String arg[]) throws InterruptedException, ExecutionException
   {
      int numLength = 125;
      
      num  = new int [numLength];
      for (int i = 0; i < num.length; ++i)
         num[i] = (int)(Math.random() * num.length * 2) + 1;
      
      System.out.println("Num : " + Arrays.toString(num));

      int threadCount = 5, fragSize = 4;
      ExecutorService execService = Executors.newFixedThreadPool(threadCount);
      
      List<Fragment> listFrag = getFragments(fragSize);
      System.out.println("Fragments : " + listFrag);
      
      doSort(execService, listFrag);      
      Util.printHR();
      System.out.println("Sorted: " + Arrays.toString(num));

      doMerge(execService, listFrag);
      Util.printHR();
      System.out.println("Merged: " + Arrays.toString(num));

      execService.shutdown();
   }

   private static List<Fragment> getFragments(int fragSize)
   {
      Util.printHeading ("Fragment");
      
      List<Fragment> listFragment = new ArrayList<>();
      for (int index = 0; index + fragSize - 1 < num.length; index += fragSize)
         listFragment.add(new Fragment(index, index + fragSize - 1));
      
      int remainder = num.length % fragSize; 
      if (remainder > 0)
         listFragment.add (new Fragment(num.length - remainder, num.length - 1));
      
      return listFragment;
   }

   private static void doSort(ExecutorService execService, List<Fragment> listFragment) throws InterruptedException, ExecutionException
   {
      Util.printHeading ("Sort");
      
      List<SortFragment> listSortFragment = new ArrayList<>();
      for (Fragment frag : listFragment)
         listSortFragment.add(new SortFragment(frag));

      List<Future<Void>> listResult = execService.invokeAll(listSortFragment);
      for (Future<Void> currResult : listResult)
         currResult.get();
   }

   private static void doMerge(ExecutorService execService, List<Fragment> listFragment) throws InterruptedException, ExecutionException
   {
      int count = 0;
      while (listFragment.size() > 1)
      {
         Util.printHeading ("Merge Iteration " + ++count);
         List<MergeFragment> listMergeFragment = new ArrayList<>();
         while (listFragment.size() >= 2)
         {
            Fragment fragA = listFragment.remove(0);
            Fragment fragB = listFragment.remove(0);
            listMergeFragment.add(new MergeFragment(fragA, fragB));
         }
         Fragment oddFrag = (listFragment.size() > 0) ? listFragment.remove(0) : null;

         List<Future<Fragment>> listResult = execService.invokeAll(listMergeFragment);
         for (Future<Fragment> currResult : listResult)
            listFragment.add(currResult.get());

         if (oddFrag != null)
            listFragment.add(oddFrag);
      }
   }

   /**
    * Represents a fragment 
    */
   static class Fragment
   {
      int startIndex = -1, endIndex = -1;

      public Fragment(int startIndex, int endIndex)
      {
         this.startIndex = startIndex;
         this.endIndex = endIndex;
      }

      public int length()
      {
         return endIndex - startIndex + 1;
      }

      public String display()
      {
         StringBuilder builder = new StringBuilder("[");
         for (int i = 0; i < endIndex; ++i)
            builder.append(num[i]).append(", ");
         builder.append(num[endIndex]).append("]");
         return builder.toString();
      }

      public String toString()
      {
         return String.format("frag[%d-%d]", startIndex, endIndex);
      }
   }

   /**
    * Class to sort fragment 
    */
   static class SortFragment implements Callable<Void>
   {
      private Fragment frag;

      public SortFragment(Fragment frag)
      {
         this.frag = frag;
      }

      @Override
      public Void call()
      {
         Util.threadLog("Sort " + frag);
         Arrays.sort(num, frag.startIndex, frag.endIndex + 1);
         return null;
      }
   }

   /**
    * Class to merge two sorted fragments 
    */
   static class MergeFragment implements Callable<Fragment>
   {
      private Fragment fragA, fragB;

      public MergeFragment(Fragment fragA, Fragment fragB)
      {
         this.fragA = fragA;
         this.fragB = fragB;
      }

      @Override
      public Fragment call()
      {
         Util.threadLog("Merging " + fragA + " and " + fragB);

         int fragAB[] = new int[fragA.length() + fragB.length()];
         int startA = fragA.startIndex;
         int startB = fragB.startIndex;

         int indexAB = 0;
         while (startA <= fragA.endIndex && startB <= fragB.endIndex)
         {
            if (num[startA] < num[startB])
               fragAB[indexAB++] = num[startA++];
            else if (num[startA] > num[startB])
               fragAB[indexAB++] = num[startB++];
            else
            {
               fragAB[indexAB++] = num[startA++];
               fragAB[indexAB++] = num[startB++];
            }
         }

         if (startA <= fragA.endIndex)
            System.arraycopy(num, startA, fragAB, indexAB, fragA.endIndex - startA + 1);

         if (startB <= fragB.endIndex)
            System.arraycopy(num, startB, fragAB, indexAB, fragB.endIndex - startB + 1);

         System.arraycopy(fragAB, 0, num, fragA.startIndex, fragAB.length);
         return new Fragment(fragA.startIndex, fragB.endIndex);
      }
   }
}
