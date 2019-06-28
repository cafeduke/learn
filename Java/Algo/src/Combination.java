import java.util.*;

public class Combination
{
   private int n = -1;

   private int r = -1;

   public Combination(int n, int r)
   {
      this.n = n;
      this.r = r;
      if (n <= 0 || r < 0 || n < r)
         throw new IllegalArgumentException("Invalid Arguments. Ensure n >= r, n > 0 and r >= 0");
   }

   public static void main(String arg[])
   {
      int n = 7, r = 3;
      Combination c = new Combination(n, r);
      for (int i = 1; i <= Combination.nCr (n, r); ++i)
         System.out.println(i + ") " + Arrays.toString(c.getIndicies(i)));
   }

   public static int nCr (int n, int r)
   {
      int numerator = 1;
      for (int i = 0; i < r; ++i)
         numerator = numerator * (n - i);

      int denominator = 1;
      for (int i = 0; i < r; ++i)
         denominator = denominator * (r - i);

      return numerator / denominator;
   }

   public int[] getIndicies(int seqNum)
   {
      int totalTreeSize = Combination.nCr(n, r);
      if (seqNum < 1 || seqNum > totalTreeSize)
         throw new IllegalArgumentException("Count should be within limits 1-" + totalTreeSize + "");

      List<Integer> listIndex = new ArrayList<Integer>();
      for (int i = 0; i < n; ++i)
         listIndex.add(i);

      int currN = n;
      int currR = r;
      int currChildTreeSize = totalTreeSize;
      int index[] = new int[r], currIndex = 0;
      Arrays.fill(index, -1);

      while (currIndex < index.length)
      {
//         System.out.println("____________________________________");
//         System.out.println("I=" + currIndex + " n=" + currN + " r=" + currR + " nCr=" + currNCR);

         int childCount = currN - currR + 1;
//         System.out.println("SubTreeCount=" + subTreeCount);

         for (int childIndex = 0; childIndex < childCount; ++childIndex)
         {
            if (childIndex == 0)
            {
               // First child gets its tree size from parent
               currChildTreeSize = (int) (((double) currR / currN) * currChildTreeSize);
               currR--;
            }
            else
            {
               // Subsequent children get their tree size from its previous sibling.
               currChildTreeSize = (int) (((double) (currN - currR) / currN) * currChildTreeSize);
            }
            
            if (seqNum <= currChildTreeSize)
            {
               // Choose the child. Going one level deeper into the tree
               index[currIndex++] = listIndex.remove(0);
               currN--;
//               System.out.println("IndexArray=" + Arrays.toString(index));
               break;
            }
            
            // Moving on to the next child. 
            listIndex.remove(0);
            currN--;
            seqNum -= currChildTreeSize;
            
//            System.out.println("SubTreeSize=" + subTreeSize);           
         }
      }
      return index;
   }

}
