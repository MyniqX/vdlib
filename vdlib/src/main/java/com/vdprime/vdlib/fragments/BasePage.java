package com.vdprime.vdlib.fragments;
import com.vdprime.vdlib.views.vdPager;

/**
 * Created by Burak on 20.08.2017.
 */
public abstract class BasePage extends BaseFragment
{
    public vdPager Parent;
    
    public abstract void beforeView();
    public abstract void afterView();
    public abstract String getTitle();
    
    @Override public boolean onBackPressed()
    {
        if (Parent != null) Parent.showPrevPage();
        return false;
    }
}
