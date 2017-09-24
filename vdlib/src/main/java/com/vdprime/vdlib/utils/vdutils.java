package com.vdprime.vdlib.utils;
import java.util.List;
import java.util.Random;

/**
 * Created by Burak on 17.07.2017.
 */
public class vdutils
{
    public static Random random = new Random();
    
    public final static <T> T getRandom(T[] list)
    {
        return list[random.nextInt(list.length)];
    }
    
    public final static <T> T getRandom(List<T> list)
    {
        return list.get(random.nextInt(list.size()));
    }
    
    public final static <T> T getRandom(List<T> list,int count)
    {
        int limit = Math.min(count,list.size());
        return list.get(random.nextInt(limit));
    }
}
