import java.util.*;
import java.io.*;

public class BTree
{
    int order = -1;
    
    int keyLength = -1;
    
    File fileIndex = null;
    
    File fileRecord = null;
    
    BTreeProperties properties = null;
    
    BNode root = null;
    
    /* BTree specific properties */
    
    LinkedList stack = null;
    
    Long rootOffset = null;
    
    public static final int SIBLING_LEFT = 1;
    
    public static final int SIBLING_RIGHT = 2;
    
    private BTree () 
    {
        stack = new LinkedList ();
        rootOffset = new Long (-1);
    }
    
    public static BTree createBTree (int order, int keyLength, File fileIndex, File fileRecord) throws IOException
    {   
        if (fileIndex == null || (fileIndex.exists() && !fileIndex.isFile()))
            throw new IOException ("Error creating/overwritting file '" + fileIndex.getAbsolutePath() + "'");        
        fileIndex.delete();
        
        if (fileRecord == null || (fileRecord.exists() && !fileRecord.isFile()))
            throw new IOException ("Error creating/overwritting file '" + fileRecord.getAbsolutePath() + "'");
        fileRecord.delete();
            
        BTree tree = new BTree ();
        tree.order = order;
        tree.keyLength = keyLength;
        tree.fileIndex = fileIndex;
        tree.fileRecord = fileRecord;
        tree.properties = new BTreeProperties (order, keyLength, fileIndex);
        
        /* Make room for meta data though the data is incorrect */
        tree.saveMetaData();
        
        tree.root = new BNode (tree.properties, BNode.NODE_LEAF);
        tree.root.save();
        
        /* Save the actual meta data */
        tree.saveMetaData();
        return tree;
    }
    
    public static BTree createBTree (int order, int keyLength) throws IOException
    {
        return BTree.createBTree (order, keyLength, new File ("BTreeIndex.dat"), new File ("BTreeRecord.dat"));
    }
    
    public static BTree loadBTree (File fileIndex, File fileRecord) throws IOException
    {
        if (fileIndex == null || !fileIndex.exists())
            throw new IOException ("Error reading Index file '" + fileIndex + "'");
                
        BTree tree = new BTree ();        
        tree.fileIndex = fileIndex;
        tree.fileRecord = fileRecord;
        tree.loadMetaData();
        tree.properties = new BTreeProperties (tree.order, tree.keyLength, fileIndex);        
        tree.root = tree.loadNode(tree.rootOffset);
        return tree;
    }
   
    public static void main (String arg[]) throws Exception
    {
    
        File f = new File ("Btree.log");
        PrintStream out = new PrintStream (new FileOutputStream (f));
    
        BTree tree = BTree.loadBTree(new File ("BTreeIndex.dat"), new File ("BTreeRecord.dat"));
        tree.printIndexTree(out);
        
        String key = null;
        while ((key = tree.getRandomKey()) != null)
        {
            out.println ("########################################");
            out.println (">>>>>>>>>>>>> " + key.substring(0, key.indexOf('\0')) + " <<<<<<<<<<<<<<");
            out.println ("########################################");
            boolean result = tree.remove(key);
            out.println (">>>>>>>>>>>>> " + result + " <<<<<<<<<<<<<<");
            System.out.println (result);
            tree.printIndexTree(out);                
        }
        out.close();

    
/*      int arr[] = {18, 7, 86, 58, 89, 72, 41, 40};
        
        for (int i = 0; i < arr.length; ++i)
        {
            System.out.println ("\n_____ " + arr[i] + " _____");
            tree.insert(""+arr[i], "text"+arr[i]);
            tree.displayIndexTree();
        }
        System.out.println ("\n_____ " + 7 + " _____");
        tree.insert(""+7, "text19");
        tree.displayIndexTree();
        
        System.out.println ("\n____ Records _______\n");
        tree.displayRecords(); */   
        
/*      File f = new File ("Btree.log");
        PrintStream out = new PrintStream (new FileOutputStream (f));
        BTree tree = BTree.createBTree(4, 10);
        Random r = new Random (); 
        for (int i = 0; i < 50; ++i)
        {
            int num;            
            num = r.nextInt(1000) + 1;
            out.println ();
            out.println ("_____ " + num + " _____");
            tree.insert (""+num, "text"+num);
            tree.printIndexTree(out);
        }    
        tree.printRecords(out);
*/     
    }
    
    public String getRandomKey ()
    {
        Random r = new Random ();
        BNode curr = getRandomLeaf (root);
        if (curr.getKeyCount() == 0)
            return null;
        return curr.getKey(r.nextInt(curr.getKeyCount()));
    }
    
    public BNode getRandomLeaf (BNode curr)
    {
        if (curr.isLeaf())        
            return curr;
        
        Random r = new Random ();
        int childIndex = r.nextInt(curr.getKeyCount());
        return getRandomLeaf (loadNode(curr.getOffset(childIndex)));        
    }

    public void loadMetaData () throws IOException
    {                        
        RandomAccessFile fp = new RandomAccessFile (fileIndex, "rw");
        fp.seek(0);
        rootOffset = new Long (fp.readLong ());        
        order = fp.readInt();        
        keyLength = fp.readInt();        
        fp.close ();        
    }
    
    public void saveMetaData () throws IOException
    {
        RandomAccessFile fp = new RandomAccessFile (fileIndex, "rw");
        fp.seek(0);
        fp.writeLong(rootOffset.longValue());
        fp.writeInt(order);
        fp.writeInt(keyLength);
        fp.close();
    }

    BNode loadNode (Long offset)
    {   
        if (properties == null)
            throw new RuntimeException ("Properties not set to load node.");
        
        BNode node = null;
        byte nodeType;        
        Long nodeMyOffset = null;
        int nodeKeyCount = -1;
        String nodeKey [] = new String [order];
        Long nodeOffset[] = new Long [order];        
        
        try
        {
            RandomAccessFile fp = new RandomAccessFile (properties.getIndexFile(), "rw");
            if (fp == null)
                System.out.println ("Error getting handle to index file");
            fp.seek(offset.longValue());
            
            nodeType = fp.readByte();
            nodeMyOffset  = new Long (fp.readLong ());
            nodeKeyCount = fp.readInt();
            for (int i = 0; i < order; ++i)
            {
                byte b[] = new byte[keyLength];                
                fp.read (b);                
                nodeKey[i] = new String (b);
                nodeOffset[i] = new Long (fp.readLong ());
            }            
            fp.close();
            node = new BNode (properties, nodeType, nodeKey, nodeOffset);            
            node.setNodeOffset(nodeMyOffset);
            node.setKeyCount(nodeKeyCount);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return node;
    }


    public boolean insert (String xKey, String xRec) throws IOException
    {
        boolean rootSplit = false;
        BNode curr = getLeaf (xKey);
        if (curr == null)
            throw new RuntimeException ("Insertion failed. Root not loaded.");
        
        if (curr.search(xKey) >= 0)
            return false;
        
        Long xOffset = addRecord(xRec);
        int result = curr.insert(xKey, xOffset);
        
        if (result == BNode.SUCCESS)
            return true;
        
        if (result == BNode.UPDATE)
        {
            BNode par = null;
            while (stack.size() > 0)
            {
                par = (BNode)stack.removeFirst();
                result = par.update (curr.largestKey(), curr.getNodeOffset());
                if (result == BNode.SUCCESS)
                    return true;
                curr = par;
            }
            return true;
        }
        
        BNode newSibling = curr.split(xKey, xOffset);
        
        if (curr == root)
            rootSplit = true;
        
        BNode par = null;
        while (stack.size() > 0)
        {
            par = (BNode)stack.removeFirst();
            
            par.update(curr.largestKey(), curr.getNodeOffset());
            if (result == BNode.OVERFLOW)
                result = par.insert(newSibling.largestKey(), newSibling.getNodeOffset());
            
            if (result == BNode.SUCCESS)
                return true;
            
            if (result == BNode.UPDATE)
            {
                curr = par;
                continue;
            }
            
            /* Split Required */
            BNode newParSibling = par.split (newSibling.largestKey(), newSibling.getNodeOffset());
            curr = par;
            newSibling = newParSibling;
            
            if (curr == root)
                rootSplit = true;
        }
        
        if (rootSplit)
        {
            root = new BNode (properties, BNode.NODE_INDEX);
            root.insert (curr.largestKey(), curr.getNodeOffset());
            root.insert (newSibling.largestKey(), newSibling.getNodeOffset());            
            root.save();
            rootOffset = root.getNodeOffset();
            saveMetaData ();
        }
        return true;
    }
    
    public BNode getMergeSibling (BNode curr, BNode par, BitSet sibType)
    {
        int childIndex = -1;
        if ((childIndex = par.search(curr.getNodeOffset())) < 0)
            throw new RuntimeException ("Child node '" + curr + "' not found in parent node '" + par + "'" );

        BNode sibling = null;
        if (childIndex == 0)
        {
            sibling = loadNode (par.getOffset(childIndex+1));
            sibType.set(BTree.SIBLING_RIGHT);
        }
        else
        {
            sibling = loadNode (par.getOffset(childIndex-1));
            sibType.set(BTree.SIBLING_LEFT);
            
        }
        return sibling;
    }
    
    /**
     * Returns a immediate sibling ( sibling towards left or right of the <b>curr</b>
     * node) which can take part in distribution ( has more than ceil(ORDER/2) keys).
     * <br>Also set <b>sibType</b> to SIBLING_LEFT or SIBLING_RIGHT denpending on the position of the sibling.
     * If no sibling is found <b>sibType</b> is cleared.
     * <br> If neither of the siblings are eligible for distribution null is returned.  
     * @param curr Current node
     * @param par  Parent of <b>curr</b>
     * @param sibType This object will be filled with type of sibling. 
     * @return Sibling eligible for distribution, null otherwise.
     */
    public BNode getDistribSibling (BNode curr, BNode par, BitSet sibType)
    {
        int childIndex = -1;
        if ((childIndex = par.search(curr.getNodeOffset())) < 0)
            throw new RuntimeException ("Child node '" + curr + "' not found in parent node '" + par + "'" );
        
        BNode sibling = null;
        if (childIndex == 0)
        {
            sibling = loadNode (par.getOffset(childIndex+1));
            sibType.set(BTree.SIBLING_RIGHT);
        }
        else if (childIndex == par.getKeyCount()-1)
        {
            sibling = loadNode (par.getOffset(childIndex-1));
            sibType.set(BTree.SIBLING_LEFT);
        }
        else
        {
            BNode rightSibling = loadNode (par.getOffset(childIndex+1));
            BNode leftSibling = loadNode (par.getOffset(childIndex-1));
            if (rightSibling.getKeyCount() >= leftSibling.getKeyCount())
            {    
                sibling = rightSibling;
                sibType.set(BTree.SIBLING_RIGHT);
            }
            else
            {
                sibling = leftSibling;
                sibType.set(BTree.SIBLING_LEFT);
            }
        }
        
        int minCount = (int)Math.ceil((properties.getOrder())/2.0);
        if (sibling.getKeyCount() > minCount)        
            return sibling;        
        return null;
    }
    
    public boolean remove (String xKey) throws IOException
    {
        boolean rootMerge = false;
        
        BNode curr = getLeaf (xKey);
        if (curr == null)
            throw new RuntimeException ("Removal failed. Root not loaded.");
        
        if (curr.search(xKey) < 0)
        {
            System.out.println ("Key '" + xKey + " not found in leaf '" + curr + "'");
            return false;
        }
        
        int result = curr.remove(xKey);
        
        if (result == BNode.SUCCESS)        
            return true;
        
        BNode par = null;
        BitSet sibType = new BitSet ();
        
        while (stack.size() > 0)
        {            
            par = (BNode)stack.removeFirst();
            
            if (result == BNode.SUCCESS)
                return true;
            
            if (result == BNode.UPDATE)
            {
                result = par.update (curr.largestKey(), curr.getNodeOffset());
                if (result == BNode.SUCCESS)
                    return true;
                curr = par;
                continue;
            }

            sibType.clear();
            BNode sibling = getDistribSibling(curr, par, sibType);
            if (sibling != null)
            {
                curr.distribute(sibling, sibType);
                if (sibType.get(BTree.SIBLING_LEFT))
                {
                    /* sibling curr*/
                    par.update(sibling.largestKey(), sibling.getNodeOffset());
                    result = par.update(curr.largestKey(), curr.getNodeOffset());
                }
                else
                {
                    /* curr sibling */
                    par.update(curr.largestKey(), curr.getNodeOffset());
                    result = par.update(sibling.largestKey(), sibling.getNodeOffset());
                }
                curr = par;
                continue;
            }

            /* Merge Required */
            sibType.clear();            
            sibling = getMergeSibling (curr, par, sibType);            
            BNode nodeLeft = null;
            BNode nodeRight = null;
            if (sibType.get(BTree.SIBLING_LEFT))
            {
                /* sibling curr */
                nodeLeft = sibling;
                nodeRight = curr;
            }
            else
            {
                /* curr sibling */
                nodeLeft = curr;
                nodeRight = sibling;
            }
            int nodeRightIndex = par.search(nodeRight.getNodeOffset());
            if (nodeRightIndex < 0)
                throw new RuntimeException ("Child '" + nodeRight + "' not found in parent '" + par + "'");
            
            nodeLeft.merge(nodeRight);
            par.update(nodeLeft.largestKey(), nodeLeft.getNodeOffset());
            result = par.remove(nodeRightIndex);
            curr = par;
            
            if (curr == root)
                rootMerge = true;
        }
        
        if (rootMerge)
        {
            if (root.getKeyCount() == 1)
            {
                root = loadNode(root.getOffset(0));                
                rootOffset = root.getNodeOffset();
                saveMetaData ();
            }
            root.save ();
        }
        
        return true;
    }
    
    public void printIndexTree (PrintStream out)
    {
        String tab = "+--";
        if (root == null)       return;
        if (out == null)        out = System.out;        
        printIndexTree (root, tab, out);
        out.flush();
    }
    
    public void printIndexTree (BNode curr, String tab, PrintStream out)
    {        
        if (curr.isLeaf())
        {            
            out.println (curr.toString(tab));
        }
        else
        {
            char ch[] = new char[tab.length()];
            Arrays.fill (ch, ' ');
            String spaceTab = new String (ch);
            
            for (int i = 0; i < curr.getKeyCount(); ++i)
            {
                String currKey = curr.getKey(i);
                currKey = currKey.substring(0, currKey.indexOf('\0'));
                out.println (tab + "[Me->" + curr.getNodeOffset() + "]");
                out.println (spaceTab + "[" + currKey + "->" + curr.getOffset(i) + "]");
                
                tab = "   " + tab;
                printIndexTree (loadNode(curr.getOffset(i)), tab, out);
                tab = tab.substring(3);
            }
        }
    }
    
    public void printRecords (PrintStream out) throws IOException
    {
        if (root == null)   return;
        if (out == null)    out = System.out;
        printRecords (root, out);
    }
    
    public void printRecords (BNode curr, PrintStream out) throws IOException
    {
        if (curr.isLeaf())
        {
            for (int i = 0; i < curr.getKeyCount(); ++i)            
                out.println (getRecord(curr.getOffset(i)));            
        }
        else
        {
            for (int i = 0; i < curr.getKeyCount(); ++i)
                printRecords(loadNode(curr.getOffset(i)), out);
        }
    }
    
    public String getRecord (Long offset) throws IOException
    {   
        RandomAccessFile fp = new RandomAccessFile (fileRecord, "rw");
        fp.seek (offset.longValue());
        int recordLength = fp.readInt ();
        byte b[] = new byte [recordLength];
        fp.read(b);        
        fp.close ();
        return new String (b);
    }
    
    public Long addRecord (String record) throws IOException
    {
        Long offset = new Long (-1);
        RandomAccessFile fp = new RandomAccessFile (fileRecord, "rw");
        fp.seek (fp.length());        
        offset = new Long (fp.length());
        
        byte b[] = record.getBytes();
        fp.writeInt (b.length);
        fp.write (b);
        fp.close();
        return offset;
    }
    
    public BNode getLeaf (String x)
    {
        if (root == null) 
            return null;
        
        stack = new LinkedList ();
        
        BNode curr = root;        
        while (!curr.isLeaf())
        {
            stack.addFirst (curr);            
            int chldIndex = curr.search(x);
            if (chldIndex < 0)
            {
                chldIndex = (-1 * chldIndex ) - 1;
                if (chldIndex == curr.getKeyCount())
                    chldIndex = curr.getKeyCount() - 1;
            }
            curr = loadNode (curr.getOffset(chldIndex));            
        }
        return curr;
    }
    
    
    public int getOrder ()
    {
        return order;
    }
    
    public int getKeyLength ()
    {
        return keyLength;
    }
    
    public File getIndexFile ()
    {
        return fileIndex;
    }
    
    public File getRecordFile ()
    {
        return fileRecord;
    }
};

class BTreeProperties
{
    private int order = -1;
    
    private int keyLength = -1;
    
    private File fileIndex = null;
    
    BTreeProperties (int order, int keyLength, File fileIndex)
    {
        this.order = order;
        this.keyLength = keyLength;
        this.fileIndex = fileIndex;
    }
    
    public int getOrder ()
    {
        return order;
    }
    
    public int getKeyLength ()
    {
        return keyLength;
    }
    
    public File getIndexFile ()
    {
        return fileIndex;
    }
};