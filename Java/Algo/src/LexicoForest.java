import java.util.*;
import java.util.concurrent.*;
import java.util.stream.IntStream;

public class LexicoForest<I>
{
   private List<I> listNode;

   private Hashtable<String, Object> cache;

   private int r = 0;
   
   private int threadCount = 1;

   private boolean isPermutation;

   private Callback<I> beforeCallback, afterCallback;

   public LexicoForest(List<I> listNode, int r)
   {
      this.listNode = listNode;
      this.cache = new Hashtable<String, Object>();
      this.r = r;
      this.isPermutation = true;
      this.beforeCallback = LexicoForest::handlerPrintItems;
      this.afterCallback = null;      
   }
   
   public LexicoForest(List<I> listNode)
   {
      this(listNode, 0);
   }
   
   public static void main (String arg[])
   {
      LexicoForest<Integer> forest = null;
      
      heading("Permutation");
      forest = new LexicoForest<>(Arrays.asList(11, 12, 13, 14, 15), 3);      
      forest.setThreadCount(1);
      forest.traverse();
      
      heading("Combination");
      forest.useCombination();
      forest.cacheClear();
      forest.traverse();
      
      /** 
       * Pick numbers from the given set (need not be continuous) to form the largest sub-set possible 
       * with sum less than the limit 
       */
      heading("Largest Subset");
      Map<String, Object> param = Map.ofEntries(
         Map.entry("limit", Integer.valueOf(5)),
         Map.entry("max_subset", new ArrayList<Integer>())
      );      
      forest = new LexicoForest<>(Arrays.asList(3, 1, 2, 2, 1, 4, 7), 3);
      forest.cachePut(param);
      forest.setThreadCount(3);
      forest.useCombination();
      forest.setBeforeSubTreeCallback(LexicoForest::handlerLargestSubset);
      forest.traverse();
      System.out.println("LargestSubset=" + forest.cacheGet("max_subset") + " Limit=" + forest.cacheGet("limit"));
   }   
   
   public static void heading (String mesg)
   {
      System.out.println();
      System.out.println("----------------------------------------------------------------------------------------------------");
      System.out.println(" " + mesg);
      System.out.println("----------------------------------------------------------------------------------------------------");
   }

   /**
    * Traverse all trees in the forest.
    * @throws InterruptedException 
    */
   public void traverse () 
   {
      try
      {
         cache.put("r", Integer.valueOf(r));
         ExecutorService executor = Executors.newFixedThreadPool(threadCount, new Util.SimpleThreadFactory());
         
         // Create all Callable's to list
         List<LexicoTree> listToCall = new ArrayList<>();      
         IntStream.range(0, listNode.size())
            .forEach((x) -> listToCall.add(new LexicoTree(x)));
         
         executor.invokeAll(listToCall);
         
         executor.shutdown();
      }
      catch (InterruptedException e)
      {
         throw new IllegalStateException("Interrupted while traversing", e);
      }
   }
   
   public void cachePut(Map<String,Object> map)
   {
      cache.putAll(map);
   }
   
   /**
    * Clear everything stored in cache.
    */
   public void cacheClear ()
   {
      cache.clear();
   }
   
   public Object cacheGet (String key)
   {
      return cache.get(key);
   }
   
   /**
    * Selection and arrangement both matter. Hence 11,12,13 and 12,13,11 shall be considered different paths.
    */
   public void usePermutation()
   {
      this.isPermutation = true;
   }

   /**
    * Selection matters and arrangement does not. Hence 11,12,13 and 12,13,11 shall be considered same.
    */
   public void useCombination()
   {
      this.isPermutation = false;
   }
   
   public void setThreadCount(int threadCount)
   {
      this.threadCount = threadCount;
   }

   /**
    * Invoke the registered callback before traversing the children of the node.
    * 
    * @param beforeCallback Callback object to be registered.
    */
   public void setBeforeSubTreeCallback(Callback<I> beforeCallback)
   {
      this.beforeCallback = beforeCallback;
   }

   /**
    * Invoke the registered callback after traversing all the children of the node.
    * 
    * @param afterCallback Callback object to be registered.
    */
   public void setAfterSubTreeCallback(Callback<I> afterCallback)
   {
      this.afterCallback = afterCallback;
   }

   public class LexicoTree implements Callable<Void>
   {
      private int nodeIndex;
      
      public LexicoTree (int index)
      {
         this.nodeIndex = index;
      }
   
      @Override
      public Void call()
      {
         Stack<Integer> stack = new Stack<>();
         boolean visited[] = new boolean[listNode.size()]; 
         stack.add(nodeIndex);
         visited[nodeIndex] = true;         
         Util.threadLog("Traversing tree, starting with node=" + listNode.get(nodeIndex));
         traverse (nodeIndex, stack, visited);
         visited[stack.pop()] = false;
         return null;
      }
   
      /**
       * Traverse a tree from <b>index</b> node. 
       * 
       * @param listNode A list of all the nodes
       * @param nodeIndex Index of current node.
       * @param stack A stack of index corresponding to nodes traversed thus far.
       * @param visited A array of nodes that have been visited thus far.
       */
      private void traverse (int currIndex, Stack<Integer> stack, boolean visited[])
      {
         // Before visiting the children, check if the subtree is worth exploring
         // If beforeCallback exists and does not permit pursuing further, return 
         if (beforeCallback != null && !beforeCallback.eval(getItemsInStack(listNode, stack), cache))
            return;
         
         if (r == 0 || r == stack.size())
            return;
         
         // Visit each child sub-tree
         int startIndex = (isPermutation) ? 0 : currIndex + 1;
         for (int childIndex = startIndex; childIndex < listNode.size(); ++childIndex)
         {
            if (visited[childIndex])
               continue;
      
            stack.push(childIndex);
            visited[childIndex] = true;         
            traverse(childIndex, stack, visited);
            visited[stack.pop()] = false;         
         }
         
         // Invoke handle after visiting all the children
         if (afterCallback != null)
            afterCallback.eval(getItemsInStack(listNode, stack), cache);      
      }      
   }

   /**
    * Iterate the stack of index and get the corresponding node from the listNode. Return the list thus obtained.
    * 
    * @param listNode A list of nodes.
    * @param stack Stack of index. Each index refers to the node in the listNode.
    * @return nodes that correspond to the index in stack.
    */
   public static <T> List<T> getItemsInStack (List<T> listNode, Stack<Integer> stack)
   {
      List<T> sublist = new ArrayList<T> ();
      stack.forEach((index) -> sublist.add(listNode.get(index)));
      return sublist;
   }

   /**
    * The 'eval' function shall be called at specific nodes during the tree traversal.
    * <p/>
    * 
    * Each callback provides the content of the nodes traversed thus far in listNode. For example in an integer
    * list of 11,12,13,14,15,16 lets say we have taken the path 15,11,13 then listNode = [15, 11, 13].
    * <p/>
    * 
    * A cache map from a String key to an Object. It can be used by user to store anything during the course of traversal
    * across the entire tree. For example, cache contents at callback for {@link SubTreeCaller#setBeforeSubTreeCallback(Callback) setBeforeSubTreeCallback}
    * can be used to determine if we want to explore the subtree further or not and accordingly return true or false. 
    * <p/>
    *
    * @param listNode A list containing nodes that have been traversed so far. In short, the path taken thus far.
    * @param cache A key to object map. This is used by the user of the algo to store intermediate results
    */
   @SuppressWarnings("hiding")
   @FunctionalInterface
   public interface Callback<I>
   {
      public boolean eval(List<I> listNode, Map<String, Object> cache);
   }

   public static <I> boolean handlerPrintItems(List<I> listNode, Map<String, Object> cache)
   {
      int r = (int)cache.get("r");
      // if (r == 0 || r == listNode.size())
         Util.threadLog(listNode);
      return true;
   }   
   
   @SuppressWarnings("unchecked")
   public static synchronized boolean handlerLargestSubset(List<Integer> listNode, Map<String, Object> cache)
   {
      List<Integer> listMax = (List<Integer>)cache.get("max_subset");      
      int currSum = listNode.stream().reduce(Integer::sum).get();
      int limit  = (int)cache.get("limit");      
      
      // No point entering subtree as the sum of numbers in nodes has exceeded the max
      if (currSum >= limit)
      {
         Util.threadLog("No point entering subtree. currSum=" + currSum + " limit=" + limit + " currList=" + listNode + " maxList" + listMax);
         return false;
      }
      
      if (listMax.isEmpty() || listNode.size() > listMax.size())
      {
         Util.threadLog("Found a larger subset within limit. currList=" + listNode + " maxList=" + cache.get("max_subset"));
         cache.put("max_subset", listNode);
      }
      return true;
   }

}
