package com.vdprime.vdlib.utils.database;
import com.vdprime.vdlib.activities.vdActivity;

import java.util.HashSet;

/**
 * Created by Burak on 19.07.2017.
 */
public abstract class vdSetDatabase<T> extends HashSet<T> implements vdIDatabase<T>
{
    protected           int        Version;
    transient protected vdActivity vd;
    
    @Override public int getVersion()
    {
        return Version;
    }
    
    @Override public void setActivity(final vdActivity activity)
    {
        vd = activity;
    }
    
    @Override public void flush()
    {
        vd.flushDatabase(this);
    }
    

    
    @Override public vdActivity getActivity()
    {
        return vd;
    }
}
