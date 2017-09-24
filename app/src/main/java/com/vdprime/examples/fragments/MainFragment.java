package com.vdprime.examples.fragments;
import com.vdprime.vdlib.fragments.BaseFragment;

/**
 * Created by Burak on 24.09.2017.
 */
public class MainFragment extends BaseFragment
{
    @Override protected int getResource()
    {
        return 0;
    }
    
    @Override protected void createView()
    {
    }
    
    @Override public boolean onBackPressed()
    {
        return false;
    }
}
