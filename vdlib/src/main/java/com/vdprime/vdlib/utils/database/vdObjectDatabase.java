package com.vdprime.vdlib.utils.database;
import com.vdprime.vdlib.activities.vdActivity;

/**
 * Created by Burak on 19.07.2017.
 */
public abstract class vdObjectDatabase implements vdIDatabase
{
    protected           int        Version;
    transient protected vdActivity vd;
    
    @Override public int getVersion()
    {
        return Version;
    }
    
    @Override public void flush()
    {
        vd.flushDatabase(this);
    }
    
    @Override public void setActivity(final vdActivity activity)
    {
        vd = activity;
    }
    

    
    @Override public vdActivity getActivity()
    {
        return vd;
    }
}
