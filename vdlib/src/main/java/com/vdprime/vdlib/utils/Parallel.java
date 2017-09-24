package com.vdprime.vdlib.utils;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by Burak on 20.07.2017.
 * <p>
 * https://stackoverflow.com/a/4010275/7244524
 */
public class Parallel
{
    private static final int NUM_CORES           = Runtime.getRuntime()
                                                          .availableProcessors();
    private static final ExecutorService forPool = Executors.newFixedThreadPool(NUM_CORES,
                                                                                new NamedThreadFactory(
                                                                                        "Parallel.For"));

    public static <T> void For(final Iterable<T> elements, final Operation<T> operation)
    {
        try
        {
            // invokeAll blocks for us until all submitted tasks in the call complete
            forPool.invokeAll(createCallables(elements, operation));
        }
        catch (InterruptedException e)
        {
            e.printStackTrace();
        }
    }
    
    private static <T> Collection<Callable<Void>> createCallables(
            final Iterable<T> elements,
            final Operation<T> operation
    )
    {
        List<Callable<Void>> callables = new LinkedList<Callable<Void>>();
        for (final T elem : elements)
        {
            callables.add(new Callable<Void>()
            {
                @Override public Void call()
                {
                    operation.perform(elem);
                    return null;
                }
            });
        }
        return callables;
    }
    
    public static interface Operation<T>
    {
        public void perform(T pParameter);
    }
    
    private static class NamedThreadFactory implements ThreadFactory
    {
        private final String baseName;
        private final AtomicInteger threadNum = new AtomicInteger(0);
        
        public NamedThreadFactory(String baseName)
        {
            this.baseName = baseName;
        }
        
        @Override public synchronized Thread newThread(Runnable r)
        {
            Thread t = Executors.defaultThreadFactory()
                                .newThread(r);
            t.setName(baseName + "-" + threadNum.getAndIncrement());
            return t;
        }
    }
}