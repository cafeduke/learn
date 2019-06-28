import java.util.*;

public class LexicoTree<I>
{
   private List<I> listNode;

   private Map<String, Object> cache;

   private int r;

   private boolean isPermutation;

   private Callback<I> beforeCallback, afterCallback;

   public LexicoTree(List<I> listNode, int r, Map<String, Object> cache)
   {
      this.listNode = listNode;
      this.cache = cache;
      this.r = r;
      this.isPermutation = true;
      this.beforeCallback = LexicoTree::handlerPrintItems;
      this.afterCallback = null;      
   }

   public LexicoTree(List<I> listNode, int r)
   {
      this(listNode, r, new Hashtable<String,Object>());
   }
   
   public static void main (String arg[])
   {
      LexicoTree<Integer> tree = null;
      
      heading("Permutation");
      tree = new LexicoTree<>(Arrays.asList(11, 12, 13, 14), 3);      
      tree.traverse();
      
      heading("Combination");
      tree.useCombination();
      tree.clearCache();
      tree.traverse();
      
      /** 
       * Pick numbers from the given set (need not be continuous) to form the largest sub-set possible 
       * with sum less than the limit 
       */
      heading("Largest Subset");
      Hashtable<String,Object> cache = new Hashtable<>(Map.ofEntries(
         Map.entry("limit", Integer.valueOf(5)),
         Map.entry("max_subset", new ArrayList<Integer>())
      ));      
      tree = new LexicoTree<>(Arrays.asList(3, 1, 2, 2, 1, 4, 7), 3, cache);
      tree.useCombination();
      tree.setBeforeSubTreeCallback(LexicoTree::handlerLargestSubset);
      tree.traverse();
      System.out.println("LargestSubset=" + cache.get("max_subset") + " Limit=" + cache.get("limit"));
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
    */
   public void traverse ()
   {
      boolean visited[] = new boolean[listNode.size()]; 
      cache.put("r", Integer.valueOf(r));
      
      Stack<Integer> stack = new Stack<>();
      for (int currIndex = 0; currIndex < listNode.size(); ++currIndex)
      {
         stack.add(currIndex);
         visited[currIndex] = true;
         traverse(listNode, currIndex, stack, visited);
         visited[stack.pop()] = false;
      }
   }
   
   /**
    * Clear everything stored in cache.
    */
   public void clearCache ()
   {
      cache.clear();
   }
   
   /**
    * Traverse a tree from <b>index</b> node. 
    * 
    * @param listNode A list of all the nodes
    * @param index Index of current node.
    * @param stack A stack of index corresponding to nodes traversed thus far.
    * @param visited A array of nodes that have been visisted thus far.
    */
   public void traverse (List<I> listNode, int index, Stack<Integer> stack, boolean visited[])
   {
      // Before visiting the children, check if the subtree is worth exploring
      // If beforeCallback exists and does not permit pursuing further, return 
      if (beforeCallback != null && !beforeCallback.eval(getItemsInStack(listNode, stack), cache))
         return;
      
      if (r == 0 || r == stack.size())
         return;
      
      // Visit each child sub-tree
      int startIndex = (isPermutation) ? 0 : index + 1;
      for (int currIndex = startIndex; currIndex < listNode.size(); ++currIndex)
      {
         if (visited[currIndex])
            continue;

         stack.push(currIndex);
         visited[currIndex] = true;         
         traverse(listNode, currIndex, stack, visited);
         visited[stack.pop()] = false;         
      }
      
      // Invoke handle after visiting all the children
      if (afterCallback != null)
         afterCallback.eval(getItemsInStack(listNode, stack), cache);      
   }
   
   /**
    * Iterate the stack of index and get the corresponding node from the listNode. Return the list thus obtained.
    * 
    * @param listNode A list of nodes.
    * @param stack Stack of index. Each index refers to the node in the listNode.
    * @return nodes that correspond to the index in stack.
    */
   public List<I> getItemsInStack (List<I> listNode, Stack<Integer> stack)
   {
      List<I> sublist = new ArrayList<I> ();
      stack.forEach((index) -> sublist.add(listNode.get(index))); 
      return sublist;
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
      if (r == 0 || r == listNode.size())
         System.out.println(listNode);
      return true;
   }   
   
   @SuppressWarnings("unchecked")
   public static boolean handlerLargestSubset(List<Integer> listNode, Map<String, Object> cache)
   {
      List<Integer> listMax = (List<Integer>)cache.get("max_subset");      
      int currSum = listNode.stream().reduce(Integer::sum).get();
      int limit  = (int)cache.get("limit");      
      
      // No point entering subtree as the sum of numbers in nodes has exceeded the max
      if (currSum >= limit)
      {
         // System.out.println("No point entering subtree. currSum >= limit. currSum=" + currSum + " limit=" + limit + " currList=" + listNode + " maxList" + listMax);
         return false;
      }
      
      if (listMax.isEmpty() || listNode.size() > listMax.size())
      {
         System.out.println("Found a larger subset within limit. currList=" + listNode + " maxList=" + cache.get("max_subset"));
         cache.put("max_subset", listNode);
      }
      return true;
   }

}
