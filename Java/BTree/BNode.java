import java.io.*;
import java.util.*;

public class BNode
{
    private byte type = 1;
    
    private String key[] = null;
    
    private Long offset [] = null;
    
    private int keycount = 0;
    
    private Long myoffset = null;
    
    private BTreeProperties properties;
    
    private int minCount = -1; 
    
    public static final byte NODE_INDEX = 1;
    
    public static final byte NODE_LEAF = 2;
    
    public static final int SUCCESS = 1;
    
    public static final int UPDATE = 3;
    
    public static final int OVERFLOW = 4;
    
    public static final int UNDERFLOW = 8;
    
    BNode (BTreeProperties properties, byte type, String x[], Long offset[]) throws IOException 
    {
        this.properties = properties;
        this.type = type;
        setKeyOffset(x, offset);
        this.keycount = (x == null)? 0 : x.length;        
        RandomAccessFile in = new RandomAccessFile (properties.getIndexFile(), "rw");        
        this.myoffset = new Long (in.length());
        minCount = (int)Math.ceil((properties.getOrder())/2.0);
        in.close();
    }
    
    BNode (BTreeProperties properties, byte type) throws IOException
    {
        this (properties, type, null, null);
    }
    
    public BNode split (String xKey, Long xOffset) throws IOException
    {
        xKey = padKey (xKey);
        
        String tempKey[]  = new String [properties.getOrder()+1];
        Long tempOffset[] = new Long [properties.getOrder()+1];
        
        int index = search (xKey);
        if (index >= 0)
            throw new RuntimeException ("Key '" + xKey + "' found in BNode during split.");
        
        /**
         * Find index where the new key fits (insertIndex)
         * Insert all key/offset before 'insertIndex' to temp
         * Add new key/offset to temp
         * Add all key/offset after 'insertIndex' to temp
         */
        int insertIndex = (-1 * index) - 1;
        System.arraycopy(this.key, 0, tempKey, 0, insertIndex);
        System.arraycopy(this.offset, 0, tempOffset, 0, insertIndex);
        tempKey[insertIndex] = xKey;
        tempOffset[insertIndex] = xOffset;        
        if (insertIndex < this.key.length)
        {
            System.arraycopy(this.key, insertIndex, tempKey, insertIndex+1, keycount-insertIndex);
            System.arraycopy(this.offset, insertIndex, tempOffset, insertIndex+1, keycount-insertIndex);
        }
        
        /**
         * Half of temp key/offsets are moved to this node.
         */
        int half = (int)Math.ceil((properties.getOrder()+1)/2.0);        
        System.arraycopy (tempKey, 0, this.key, 0, half);
        System.arraycopy (tempOffset, 0, this.offset, 0, half);
        this.keycount = half;

        /**
         * Remaining key/offsets are moved to newnode
         */
        int newnodeLen = properties.getOrder() + 1 - half;
        String newnodeKey[]  = new String [newnodeLen];
        Long newnodeOffset[] = new Long [newnodeLen];
        System.arraycopy (tempKey, half, newnodeKey, 0, newnodeLen);
        System.arraycopy (tempOffset, half, newnodeOffset, 0, newnodeLen);
        
        BNode sibling = new BNode (properties, type, newnodeKey, newnodeOffset);
        this.save();
        sibling.save();
        return sibling;
    }
    
    /**
     * Update the node having offset <b>offset</b> with key <b>x</b>
     * <br> If offset <b>offset</b> is not found in this node, exception is thrown.
     * 
     * <br> If the updated key is the largest key in this node then 'UPDATE' is returned.
     * ( Indicating that the parent may require updation ) else 'SUCCESS' is returned
     *     
     * @param x New key 
     * @param offset Offset who's key needs to be updated to <b>x</b>
     * @return
     */
    public int update (String xKey, Long xOffset)
    {
        xKey = padKey (xKey);
        
        int index = search (xOffset);
        if (index < 0)
            throw new RuntimeException ("Offset '" + xOffset +"' not found in node '" + this + "'");
        
        key[index] = xKey;
        
        save ();
        
        if (index == keycount-1)
            return UPDATE;
        return SUCCESS;
    }
    
    /**
     * Insert key <b>x</b> and its offset <b>offset</b> to the node.
     * <br> If key <b>x</b> is already present, exception is thrown.
     * 
     * <br> If key <b>x</b> cannot be inserted as max elements are already
     * present, 'OVERFLOW' is returned.
     *  
     * <br> If newly inserted key <b>x</b> is highest in this node,  
     * its parent node may require updation, so 'UPDATE' is returned.
     *     
     * <br> Any normal insertion results in 'SUCCESS' 
     * <br><b>Note :</b> 'UPDATE' is a type of 'SUCCESS' which further indicates
     * that parent may require updation. 
     * @param xKey  key to be inserted
     * @param xOffset Offset to be inserted
     * @return
     */
    public int insert (String xKey, Long xOffset)
    {
        xKey = padKey(xKey);  
        
        if (keycount == properties.getOrder())
            return BNode.OVERFLOW;
        
        /* If search succeeds its duplicate entry */
        int index = -1;        
        if ((index = search (xKey)) >= 0)
            throw new RuntimeException ("Duplicate entry for key '" + xKey + "'");
        
        int insertIndex = (-1*index) - 1;
        int shiftCount = keycount - insertIndex;
        if (shiftCount > 0)
        {
            System.arraycopy(key, insertIndex, key, insertIndex+1, shiftCount);
            System.arraycopy(offset, insertIndex, offset, insertIndex+1, shiftCount);
        }
        
        this.key[insertIndex] = xKey;
        this.offset[insertIndex] = xOffset;
        keycount++;
        
        this.save();
            
        if (insertIndex == keycount -1)
            return BNode.UPDATE;
        
        return BNode.SUCCESS;
    }
    
    public void merge (BNode nodeRight)
    {   
        if (getKeyCount() + nodeRight.getKeyCount() > properties.getOrder())
            throw new RuntimeException ("Merging '" + this +"' with '" + nodeRight + " exceeds ORDER");
        
        System.arraycopy(nodeRight.key, 0, this.key, this.keycount, nodeRight.keycount);
        System.arraycopy(nodeRight.offset, 0, this.offset, this.keycount, nodeRight.keycount);
        
        this.keycount += nodeRight.keycount;
        nodeRight.keycount = 0; 
        
        this.save();        
    }
    
    public void distribute (BNode sib, BitSet sibType)
    {        
        if (getKeyCount() >= minCount)
            throw new RuntimeException ("Distribute called, but current node '" + this + "' is not in UNDERFLOW state.");
        
        if (sib.getKeyCount() <= minCount)
            throw new RuntimeException ("Distribute called, but sibling node '" + sib + "' does not have more than ORDER/2 elements.");
                
        int transferCount = (int)Math.ceil((sib.getKeyCount() - minCount)/2.0);
        
        if (sibType.get(BTree.SIBLING_LEFT))
        {
            /* sibling curr */
            
            /* Create space in the beginning of curr to accomidate more elements from sib */            
            System.arraycopy(this.key, 0, this.key, transferCount, this.keycount);
            System.arraycopy(this.offset, 0, this.offset, transferCount, this.keycount);
            
            /* Copy elements from sib to curr */
            int sourceIndex = sib.keycount - transferCount;
            int targetIndex = 0;
            System.arraycopy (sib.key, sourceIndex, this.key, targetIndex, transferCount);
            System.arraycopy (sib.offset, sourceIndex, this.offset, targetIndex, transferCount);
            
            this.keycount += transferCount;
            sib.keycount  -= transferCount;
        }
        else        
        {
            /* curr sibling */            
            
            /* Copy elements from sib to curr */
            System.arraycopy(sib.key, 0, this.key, this.keycount, transferCount);
            System.arraycopy(sib.offset, 0, this.offset, this.keycount, transferCount);
            
            /* Move elements in sibling to beginning */
            System.arraycopy(sib.key, transferCount, sib.key, 0, sib.keycount-transferCount);
            System.arraycopy(sib.offset, transferCount, sib.offset, 0, sib.keycount-transferCount);
            
            this.keycount += transferCount;
            sib.keycount  -= transferCount;            
        }
        this.save ();
        sib.save();
    }
    
    /**
     * Removes key and corresponding offset of child <b>childIndex</b> from the node.
     * <br>If the removal resulted in less than <i>minCount</i> elements, 
     * UNDERFLOW is returned.
     * 
     * <br> If the child deleted was highest in this node,  
     * its parent node may require updation, so 'UPDATE' is returned.
     * 
     * <br> Any other normal removal results in 'SUCCESS'
     * 
     * <br>Throws ArrayIndexOutOfBoundsException if childIndex is invalid.
     * @param childIndex
     * @return
     */
    public int remove (int childIndex)
    {
        if (childIndex < 0 || childIndex >= keycount)
            new ArrayIndexOutOfBoundsException ("Remove :Index '" + childIndex + "' greater than keycount '" + keycount + "'");
        
        int shiftCount = keycount - childIndex - 1;
        if (shiftCount > 0)
        {
            System.arraycopy(key, childIndex+1, key, childIndex, shiftCount);
            System.arraycopy(offset, childIndex+1, offset, childIndex, shiftCount);
        }
        keycount--;
        
        if (keycount < minCount)
            return BNode.UNDERFLOW;
        
        this.save ();
        
        if (childIndex == keycount)
            return BNode.UPDATE;
        
        return BNode.SUCCESS;        
    }
    
    /**
     * Removes child with key <b>xKey</b> and corresponding offset, from the node.     * 
     * <br>If the removal resulted in less than <i>minCount</i> elements, 
     * UNDERFLOW is returned.
     * 
     * <br> If the child deleted was highest in this node,  
     * its parent node may require updation, so 'UPDATE' is returned.
     * 
     * <br> Any other normal removal results in 'SUCCESS'
     * 
     * <br>Throws ArrayIndexOutOfBoundsException if childIndex is invalid.
     * @param childIndex
     * @return
     */    
    public int remove (String xKey)
    {
        xKey = padKey(xKey);
        
        int childIndex = -1;        
        if ((childIndex = search (xKey)) < 0)
            throw new RuntimeException ("Key '" + xKey + "' not found in Node '" + this + "'");
        return remove (childIndex);
    }
    
    /**
     * Returns a string after adjusting its length  
     * @param str
     * @return
     */
    public String padKey (String str)
    {
        if (str.length() == properties.getKeyLength())
            return str;
        
        if (str.length() > properties.getKeyLength())
            throw new RuntimeException ("Number of bytes in string '" + str + "' exceeds capacity of " + properties.getKeyLength());
        
        byte bytes[] = str.getBytes();        
        byte bytesKey[] = new byte [properties.getKeyLength()];
        for (int i = 0; i < bytes.length; ++i)
            bytesKey[i] = bytes[i];
        return new String (bytesKey);
    }
    
    
    /**
     * Overide toString method if object.
     * Add all keys
     */
    public String toString (String tab)
    {
        String str = "";
        char ch[] = new char[tab.length()];
        Arrays.fill (ch, ' ');
        String spaceTab = new String (ch);        
        
        str = str + tab;
        str = str + "[Me->" + myoffset + "]" + System.getProperty("line.separator");
        
        str = str + spaceTab;
        str = str + "[";        
        for (int i = 0; i < keycount; ++i)
            str = str + key[i].substring(0, key[i].indexOf('\0')) + "->" + offset[i] + "; ";
        str = str + "] ";
        
        return str;
    }
    
    public String toString ()
    {
        return toString ("");
    }
    
    /**
     * @return Return the key which is comparably greater than all other keys
     * in the node.
     */
    public String largestKey ()
    {
        return (keycount == 0) ? null : key[keycount - 1]; 
    }
    
    /**
     * Search for <b>searchKey</b> among the keys of this node.
     * If present returns the index where it was found. Returns negative number if not found.
     * <br> Absoute of this negative number minus 1 will give the index at which the new element
     * <b>searchKey</b> could be inserted preserving the sorted order. 
     * @param searchKey
     * @return index of <b>searchKey</b> or negative number if not found.
     */
    public int search (String searchKey)
    {
        searchKey = padKey (searchKey);
        String keySubSet[] = new String [keycount];
        System.arraycopy(key, 0, keySubSet, 0, keycount);
        int result = Arrays.binarySearch(keySubSet, searchKey);
        return result;    
    }
    
    /**
     * Searches for offset in the node.
     * <br> If present returns the index where it was found. Returns -1 if not found. 
     * @param offset
     * @return index of <b>offset</b> or -1 if not found.
     */
    public int search (Long xOffset)
    {
        for (int i = 0; i < keycount; ++i)
            if (xOffset.equals(this.offset[i]))
                return i;
        return -1;
    }
    
    public void save () 
    {
        if (myoffset.longValue() == -1)
            throw new RuntimeException ("Invalid offset '" + myoffset + "' while saving node.");
        
        try
        {
            RandomAccessFile fp = new RandomAccessFile (properties.getIndexFile(), "rw");
            fp.seek (myoffset.longValue());
            fp.writeByte(type);
            fp.writeLong (myoffset.longValue());            
            fp.writeInt (keycount);
            for (int i = 0; i < properties.getOrder(); ++i)
            {
                fp.write (key[i].getBytes());
                fp.writeLong(offset[i].longValue());
            }
            fp.close();
        }
        catch (Exception e)
        {
            throw new RuntimeException ("Error saving Node");
        }
    }
    
    public void setKeyOffset (String x[], Long offset[])
    {
        int ORDER = properties.getOrder();
        
        if  (x == null ^ offset == null)
            throw new IllegalArgumentException ("Either both key and offset are null or must have same length");
        
        if (x != null && offset != null && x.length != offset.length)
            throw new IllegalArgumentException ("Array length of key and offset do not match. " +
                  "Key :" + x.length + " Offset :" + offset.length);        
        
        this.key = new String[ORDER];
        this.offset = new Long[ORDER];
        
        for (int i = 0; i < ORDER; ++i)
        {
            if (x != null && i < x.length)
            {
                this.key[i] = padKey (x[i]);
                this.offset[i] = offset[i];
            }
            else
            {
                this.key[i] = padKey ("");
                this.offset[i] = new Long (-1);
            }
        }
    }
    
    public void setNodeOffset (Long offset)
    {
        this.myoffset = offset;
    }
    
    
    public void setKeyCount (int keycount)
    {
        this.keycount = keycount;
    }
    
    public Long getNodeOffset ()
    {
        return myoffset;
    }    
    
    public int getKeyCount ()
    {
        return keycount;
    }
    
    public Long getOffset (int index)
    {
        if (index < 0 || index >= keycount)
            return null;
        return offset[index];
    }
    
    public String getKey (int index)
    {
        if (index < 0 || index >= keycount)
            return null;
        return key[index];
    }
    
    public boolean isLeaf ()    
    {
        if (type == BNode.NODE_LEAF)
            return true;
        return false;
    }
};
