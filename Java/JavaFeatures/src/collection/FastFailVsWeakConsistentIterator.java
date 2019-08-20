package collection;

import java.util.*;
import java.util.concurrent.*;

import util.Util;

/**
 * <pre>
 * The class has two methods 
 *    - readAll    : Iterates through the collection of fruits (20ms sleep after each read).
 *    - removeFew  : Removes 'Orange' after 5ms and 'Jackfruit' after 40ms.
 * There are two threads invoking these methods.
 * 
 * Essentially, 
 *    - Thread-t2 attempts to remove an element while Thread-t1 is iterating the collection.
 *    - The time delays are such that same element is read by Thread-t1 and removed by Thread-t2 
 * 
 * Scenario A
 * ----------
 *    - Collection used is TreeSet
 *    - ConcurrentModificationException is thrown.
 *    
 * Scenario B
 * ----------
 *    - Collection used is ConcurrentSkipListSet
 *    - No exception is thrown.
 *    - With the above time delays
 *       - Thread-t1 prints 'Jackfruit'      - Read and removed at the same time.
 *       - Thread-t1 does NOT print 'Orange' - Removed much earlier. 
 *    - We see that certain write operations reflect and certain others don't (State value is present during read).   
 *      
 * Scenario C
 * ----------      
 *    - Collection used is CopyOnWriteArraySet
 *    - No exception is thrown.
 *    - With the above time delays
 *       - Thread-t1 prints 'Jackfruit'  - Read and removed at the same time.
 *       - Thread-t1 prints 'Orange'     - Removed much earlier. 
 *    - We see that the write operation are made on a copy and does not affect read.   
 *</pre>
 */
public class FastFailVsWeakConsistentIterator implements Runnable
{  
    private static final String FRUIT[] = new String[] {"Apple", "Grape", "Jackfruit", "Mango", "Orange", "Watermelon"};
    
    // Remove Orange
    private static final String FRUIT_TO_REMOVE_EARLY = FRUIT[4];
    
    // Remove Jackfruit
    private static final String FRUIT_TO_REMOVE_LATER = FRUIT[2];    
    
    private Set<String> set = null;
    
    public FastFailVsWeakConsistentIterator (Set<String> set)
    {
       this.set = set;
       set.addAll(Arrays.asList(FRUIT));
    }
    
    public static void main (String arg[]) throws InterruptedException
    {
       execConcurrent (new TreeSet<String> ());
       execConcurrent (new ConcurrentSkipListSet<String> ());
       execConcurrent (new CopyOnWriteArraySet<String> ());
    }  
    
    public static void execConcurrent (Set<String> set) throws InterruptedException
    {
       Util.printHeading ("Iterator for " + set.getClass().getSimpleName());
       FastFailVsWeakConsistentIterator demo = new FastFailVsWeakConsistentIterator(set);
       Thread t1 = new Thread (demo, "t1");
       Thread t2 = new Thread (demo, "t2");
       t1.start ();
       t2.start ();
       t1.join (3000);
       t2.join (3000);
    }
    
    @Override
    public void run()
    {
       try
       {
          String tName = Thread.currentThread().getName();
          if (tName.equals("t1"))
             readAll ();
          else if (tName.equals("t2"))
             removeFew();
       }
       catch (Exception e)
       {
          Util.threadLog ("Exception: " + e);
       }
    }    
    
    public void readAll ()
    {
       Util.threadLog ("Started reading list");
       for (String currStr : set)
       {
          Util.threadLog (currStr);
          Util.sleepInMilli(20);
       }
       Util.threadLog ("Finished reading list");
    }
    
    public void removeFew ()
    {
       Util.sleepInMilli(5);
       remove (FRUIT_TO_REMOVE_EARLY);
       Util.sleepInMilli(35);
       remove (FRUIT_TO_REMOVE_LATER);
    }
    
    public void remove (String fruit)
    {
       Util.threadLog ("Started removing fruit "  + fruit);
       set.remove (fruit);
       Util.threadLog ("Finished removing fruit " + fruit);
    }
}

