package com.vdprime.vdlib.fragments;
import android.os.Bundle;
import android.support.annotation.Keep;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.vdprime.vdlib.R;
import com.vdprime.vdlib.activities.vdActivity;

/**
 * Created by Burak on 17.07.2017.
 */
@Keep
public abstract class BaseFragment extends Fragment
{
    public    vdActivity vd;
    protected View       rootView;
    protected Bundle     savedInstanceBundle;
    
    @Override public View onCreateView(
            final LayoutInflater inflater,
            @Nullable final ViewGroup container,
            @Nullable final Bundle savedInstanceState
    )
    {
        vd = (vdActivity) getContext();
        savedInstanceBundle = savedInstanceState;
        vd.injectFields(this);
        vd.devInfo("onCreateview() : " + getClass().getSimpleName());
        if (rootView == null)
        {
            int     resource   = getResource();
            boolean noresource = false;
            if (resource == 0)
            {
                resource = R.layout.vdlib_not_yet_completed;
                noresource = true;
            }
            rootView = inflater.inflate(resource, container, false);
            if (noresource)
            {
                final TextView t = (TextView) rootView;
                t.setText(t.getText() + "\n" + getClass().getSimpleName());
            }
            else
            {
                vd.injectViews(this, rootView);
                createView();
            }
            vd.devInfo("onCreateView(), rootView created.");
        }
        else
        {
            vd.devInfo("onCreateView(), returning current rootView");
        }
        return rootView;
    }
    
    protected abstract int getResource();
    protected abstract void createView();
    public abstract boolean onBackPressed();
    
    public void toast(String str, Object... params)
    {
        vd.toast(str, params);
    }
    
    public void throwException(final Exception exception, String extraMessage)
    throws RuntimeException
    {
        vd.throwException(exception, extraMessage);
    }
}
