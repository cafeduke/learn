import java.util.*;

public class LexicoTree<I>
{
   private List<I> listItem;

   private Map<String, Object> cache;

   private int r;

   private boolean isPermutation;

   private Callback<I> beforeCallback, afterCallback;

   public LexicoTree(List<I> listItem, int r, Map<String, Object> cache)
   {
      this.listItem = listItem;
      this.cache = cache;
      this.r = r;
      this.isPermutation = true;
      this.beforeCallback = LexicoTree::handlerPrintItems;
      this.afterCallback = null;      
   }

   public LexicoTree(List<I> listItem, int r)
   {
      this(listItem, r, new Hashtable<String,Object>());
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

   public void traverse ()
   {
      boolean visited[] = new boolean[listItem.size()]; 
      cache.put("r", Integer.valueOf(r));
      
      Stack<Integer> stack = new Stack<>();
      for (int currIndex = 0; currIndex < listItem.size(); ++currIndex)
      {
         stack.add(currIndex);
         visited[currIndex] = true;
         traverse(listItem, currIndex, stack, visited);
         visited[stack.pop()] = false;
      }
   }
   
   public void clearCache ()
   {
      cache.clear();
   }
   
   public void traverse (List<I> listItem, int index, Stack<Integer> stack, boolean visited[])
   {
      if (beforeCallback != null && !beforeCallback.eval(getItemsInStack(listItem, stack), cache))
         return;
      
      if (r == 0 || r == stack.size())
         return;
      
      int startIndex = (isPermutation) ? 0 : index + 1;
      for (int currIndex = startIndex; currIndex < listItem.size(); ++currIndex)
      {
         if (visited[currIndex])
            continue;

         stack.push(currIndex);
         visited[currIndex] = true;         
         traverse(listItem, currIndex, stack, visited);
         visited[stack.pop()] = false;         
      }
      
      if (afterCallback != null)
         afterCallback.eval(getItemsInStack(listItem, stack), cache);      
   }
   
   public List<I> getItemsInStack (List<I> listItem, Stack<Integer> stack)
   {
      List<I> sublist = new ArrayList<I> ();
      stack.forEach((index) -> sublist.add(listItem.get(index))); 
      return sublist;
   }

   public void usePermutation()
   {
      this.isPermutation = true;
   }

   public void useCombination()
   {
      this.isPermutation = false;
   }

   public void setBeforeSubTreeCallback(Callback<I> beforeCallback)
   {
      this.beforeCallback = beforeCallback;
   }

   public void setAfterSubTreeCallback(Callback<I> afterCallback)
   {
      this.afterCallback = afterCallback;
   }

   @SuppressWarnings("hiding")
   @FunctionalInterface
   public interface Callback<I>
   {
      public boolean eval(List<I> listItem, Map<String, Object> cache);
   }

   public static <I> boolean handlerPrintItems(List<I> listItem, Map<String, Object> cache)
   {
      int r = (int)cache.get("r");
      if (r == 0 || r == listItem.size())
         System.out.println(listItem);
      return true;
   }
   
   @SuppressWarnings("unchecked")
   public static boolean handlerLargestSubset(List<Integer> listItem, Map<String, Object> cache)
   {
      List<Integer> listMax = (List<Integer>)cache.get("max_subset");      
      int currSum = listItem.stream().reduce(Integer::sum).get();
      int limit  = (int)cache.get("limit");      
      
      // No point entering subtree as the sum of items has exceeded the max
      if (currSum >= limit)
      {
         // System.out.println("No point entering subtree. currSum >= limit. currSum=" + currSum + " limit=" + limit + " currList=" + listItem + " maxList" + listMax);
         return false;
      }
      
      if (listMax.isEmpty() || listItem.size() > listMax.size())
      {
         System.out.println("Found a larger subset within limit. currList=" + listItem + " maxList=" + cache.get("max_subset"));
         cache.put("max_subset", listItem);
      }
      return true;
   }

}
