import java.util.*;

public class Permutation
{
   private int n = -1;
   
   private int r = -1;
   
   private int nPr = -1;
   
   public Permutation (int n, int r)
   {
      this.n = n;
      this.r = r;
      if (n <= 0 || r < 0 || n < r)
         throw new IllegalArgumentException("Invalid Arguments. Ensure n >= r, n > 0 and r >= 0");
      
      this.nPr = Permutation.nPr (n, r);
   }
   
   public static void main (String arg[])
   {
      int n = 5, r = 3;
      Permutation p = new Permutation(n, r);
      for (int i = 1; i <= Permutation.nPr(n, r); ++i)
         System.out.println(String.format("%02d) %s", i, Arrays.toString(p.getIndicies(i))));
   }
   
   public static int nPr (int n, int r)
   {
      int product = 1;
      for (int i = 0; i < r; ++i)
         product = product * (n - i);
      return product;
   }
   
   public int[] getIndicies (int seqNum)
   {
      if (seqNum < 1 || seqNum > nPr)
         throw new IllegalArgumentException("Sequence number should be within limits 1-" + nPr + "");
      
      List<Integer> listIndex = new ArrayList<> ();
      for (int i = 0; i < n; ++i)
         listIndex.add (i);

      int seq[] = new int [r];
      int subTreeSize = nPr;      
      --seqNum;
      for (int i = 0; i < seq.length; ++i)
      {
         // There are currN trees, each of equal size, that is equal to subTreeSize
         int currN = n - i;

         // The size of a subTree  is the number of paths (from top to leaf) in the subTree 
         // A subTree at ith index has root element as listIndex(i) 
         subTreeSize = subTreeSize / currN;
         
         // seqNum/subTreeSize gives the index of the subTree where seqNum fits.
         // Thus, (seqNum/subTreeSize) tells us which subTree among currN subtrees has to be chosen.
         // Element at index (seqNum/subTreeSize) is the root element of the subtree.
         // As we know the root element, remove it and add it to the lexicographical sequence. 
         seq[i] = listIndex.remove (seqNum / subTreeSize);
         
         // We found the tree and its root element. We now need to go one level deeper.
         seqNum = seqNum % subTreeSize;
      }
      return seq;
   }
}














