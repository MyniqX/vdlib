package com.vdprime.vdlib.utils.database;
import com.vdprime.vdlib.activities.vdActivity;

/**
 * Created by Burak on 19.07.2017.
 */
public interface vdIDatabase <T>
{
    int getVersion();
    boolean autoFlush();
    void onCreate();
    void onLoad();
    void flush();
    void setActivity(vdActivity activity);
    vdActivity getActivity();
}
