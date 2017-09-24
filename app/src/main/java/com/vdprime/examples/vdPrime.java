package com.vdprime.examples;
import com.vdprime.examples.fragments.MainFragment;
import com.vdprime.vdlib.activities.vdActivity;
import com.vdprime.vdlib.fragments.BaseFragment;

public class vdPrime extends vdActivity
{
    @Override public boolean isDebugMode()
    {
        return BuildConfig.DEBUG;
    }
    
    @Override protected BaseFragment getStartFragment()
    {
        return new MainFragment();
    }
    
    @Override protected void createActivity()
    {
    }
}