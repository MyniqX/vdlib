package com.vdprime.vdlib.utils.database;
import com.vdprime.vdlib.activities.vdActivity;

import java.util.ArrayList;

/**
 * Created by Burak on 19.07.2017.
 */
public abstract class vdListDatabase<T> extends ArrayList<T> implements vdIDatabase<T>
{
    protected           int        Version;
    transient protected vdActivity vd;
    
    @Override public void setActivity(final vdActivity activity)
    {
        vd = activity;
    }
    
    @Override public void flush()
    {
        vd.flushDatabase(this);
    }
    
    @Override public int getVersion()
    {
        return Version;
    }
    
    @Override public vdActivity getActivity()
    {
        return vd;
    }
}
