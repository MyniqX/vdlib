package com.vdprime.vdlib.utils.database;
import com.vdprime.vdlib.activities.vdActivity;
import com.vdprime.vdlib.utils.vdio;
import com.vdprime.vdlib.utils.vdstring;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Burak on 19.07.2017.
 */
public final class vdCoreDatabase
{
    protected List<vdIDatabase> databases = new ArrayList<>();
    protected vdActivity vd;
    
    public vdCoreDatabase(vdActivity activity)
    {
        vd = activity;
    }
    
    public void flushAll()
    {
        for (vdIDatabase db : databases)
            if (db.autoFlush() == true) flush(db);
    }
    
    public void flush(vdIDatabase db)
    {
        synchronized (db)
        {
            vdio.writeAllText(new File(vd.getFilesDir(),
                                       db.getClass()
                                         .getSimpleName()), vd.toJson(db));
        }
    }
    
    private String loadDatabaseFromFile(String tag)
    {
        return vdio.readAllText(new File(vd.getFilesDir(), tag));
    }
    
    @Deprecated private String loadDatabaseFromSP(String tag)
    {
        return vd.getSharedPreferences()
                 .getString(tag, "");
    }
    
    public <T extends vdIDatabase> T loadDatabase(
            final Class<T> clzz
    )
    {
        //check if loaded before
        for (int i = 0; i < databases.size(); i++)
        {
            if (databases.get(i)
                         .getClass() == clzz) { return (T) databases.get(i); }
        }
        //load from file
        String tag  = clzz.getSimpleName();
        String data = loadDatabaseFromFile(tag);
        T      db   = null;
        if (vdstring.isNullorEmpty(data) == false)
        {
            try
            {
                db = vd.fromJson(data, clzz);
                db.setActivity(vd);
                db.onLoad();
            }
            catch (final Exception exception)
            {
                vd.printStack(tag + " database loading failed", exception);
            }
        }
        else { vd.devInfo(tag + " named database is not exist."); }
        if (db == null)
        {
            try
            {
                db = clzz.newInstance();
                db.setActivity(vd);
                db.onCreate();
            }
            catch (final Exception e)
            {
                vd.throwException(e, "Create class instance failed : " + clzz.getSimpleName());
            }
        }
        databases.add(db);
        return db;
    }
}
