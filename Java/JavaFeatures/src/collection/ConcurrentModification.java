package collection;

import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.*;

import util.Util;

public class ConcurrentModification
{
    public static void main (String arg[]) throws Exception
    {
        // Hash table will result in java.util.ConcurrentModificationException
        // Map<Integer,String> mapNumInfo = new Hashtable<Integer, String>();
        
        Map<Integer,String> mapNumInfo = new ConcurrentHashMap<Integer, String>();
        MyReader reader = new MyReader (Optional.of(mapNumInfo));
        MyWriter writer = new MyWriter (Optional.of(mapNumInfo));
        new Thread (reader, "tReader").start();
        new Thread (writer, "tWriter").start();
    }    
}

class MyReader implements Runnable
{
    private Map<Integer,String> mapNumInfo = null;
    
    public MyReader (Optional<Map<Integer,String>> mapNumInfo)
    {
        this.mapNumInfo = mapNumInfo.get();
    }

    @Override
    public void run()
    {
        Util.threadLog("Started");
        Util.sleepInMilli(200);
        for (Integer currNum : mapNumInfo.keySet())
        {                        
            Util.threadLog(String.format("Get %d --> %s", currNum, mapNumInfo.get(currNum)));
            Util.sleepInMilli(50);
        }
        Util.threadLog("Done");
    }
    
}


class MyWriter implements Runnable
{
    private Map<Integer,String> mapNumInfo = null;
    
    public MyWriter (Optional<Map<Integer,String>> mapNumInfo)
    {
        this.mapNumInfo = mapNumInfo.get();
    }

    @Override
    public void run()
    {
        for (int i = 0; i < 10; ++i)
        {                        
            mapNumInfo.put(i, "Hello-"+i);
            Util.threadLog(String.format("Put %d --> %s", i, "Hello-"+i));
            Util.sleepInMilli(100);
        }
    }
    
}
