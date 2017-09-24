package com.vdprime.vdlib.views;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerTabStrip;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.View;

import com.vdprime.vdlib.R;
import com.vdprime.vdlib.fragments.BasePage;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Burak on 23.08.2017.
 */
public class vdPager extends BaseView
{
    ViewPager     viewPager;
    PagerTabStrip tabStrip;
    TabLayout     tabLayout;
    List<BasePage> basePages = new ArrayList<>();
    ViewPagerAdapter viewPagerAdapter;
    boolean animateChanges = false;
    int _strip;
    
    public vdPager(final Context context)
    {
        super(context);
    }
    
    public vdPager(
            final Context context, @Nullable final AttributeSet attrs
    )
    {
        super(context, attrs);
    }
    
    public vdPager(
            final Context context, @Nullable final AttributeSet attrs, final int defStyleAttr
    )
    {
        super(context, attrs, defStyleAttr);
    }
    
    public vdPager(
            final Context context,
            final AttributeSet attrs,
            final int defStyleAttr,
            final int defStyleRes
    )
    {
        super(context, attrs, defStyleAttr, defStyleRes);
    }
    
    @Override public int getLayoutResource()
    {
        return R.layout.vdlib_viewpager_layout;
    }
    
    @Override public int[] getLayoutStyle()
    {
        return R.styleable.vdPager;
    }
    
    @Override public void parseArray(final TypedArray typedArray)
    {
        animateChanges = typedArray.getBoolean(R.styleable.vdPager_animateChanges, true);
        _strip = typedArray.getInt(R.styleable.vdPager_striptype, 0);
    }
    
    @Override public void prepareView()
    {
        viewPager = (ViewPager) findViewById(R.id.vdlib_viewpager);
        tabStrip = (PagerTabStrip) findViewById(R.id.vdlib_viewpager_tab);
        tabLayout = (TabLayout) findViewById(R.id.vdlib_viewpager_tablayout);
        if (animateChanges && Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB)
        { viewPager.setPageTransformer(true, new ZoomOutPageTransformer()); }
        viewPager.addOnPageChangeListener(new PageCallBack());
        tabLayout.setupWithViewPager(viewPager, true);
        if (_strip != 0)
        {
            tabStrip.setVisibility(GONE);
            tabLayout.setVisibility(VISIBLE);
        }
        else
        {
            tabLayout.setVisibility(GONE);
        }
    }
    
    public void addPages(boolean clearFirst, BasePage... pages)
    {
        if (clearFirst) basePages.clear();
        for (int i = 0; i < pages.length; i++)
            basePages.add(pages[i]);
        viewPagerAdapter = new ViewPagerAdapter();
        viewPager.setAdapter(viewPagerAdapter);
    }
    
    public void showNextPage()
    {
        int index = viewPager.getCurrentItem() + 1;
        if (index < basePages.size()) viewPager.setCurrentItem(index);
    }
    
    public void showPrevPage()
    {
        int index = viewPager.getCurrentItem() - 1;
        if (0 <= index && index < basePages.size()) viewPager.setCurrentItem(index);
    }
    
    public boolean onBackPressed()
    {
        BasePage current = getCurrentPage();
        if (current != null && current.onBackPressed() == true) return true;
        return false;
    }
    
    public BasePage getCurrentPage()
    {
        return basePages.get(viewPager.getCurrentItem());
    }
    
    public ViewPager getViewPager() { return viewPager;}
    
    class ViewPagerAdapter extends FragmentStatePagerAdapter
    {
        public ViewPagerAdapter()
        {
            super(vd.getSupportFragmentManager());
        }
        
        @Override public Fragment getItem(final int position)
        {
            BasePage basePage = basePages.get(position);
            basePage.Parent = vdPager.this;
            return basePage;
        }
        
        @Override public int getCount()
        {
            return basePages.size();
        }
        
        @Override public CharSequence getPageTitle(final int position)
        {
            return basePages.get(position)
                            .getTitle();
        }
    }
    
    class ZoomOutPageTransformer implements ViewPager.PageTransformer
    {
        private static final float MIN_SCALE = 0.85f;
        private static final float MIN_ALPHA = 0.5f;
        
        @TargetApi (Build.VERSION_CODES.HONEYCOMB) @Override
        public void transformPage(final View view, final float position)
        {
            final int pageWidth  = view.getWidth();
            final int pageHeight = view.getHeight();
            if (position < -1)
            { // [-Infinity,-1)
                // This page is way off-screen to the left.
                view.setAlpha(0);
            }
            else if (position <= 1)
            { // [-1,1]
                // Modify the default slide transition to shrink the page as well
                final float scaleFactor = Math.max(MIN_SCALE, 1 - Math.abs(position));
                final float vertMargin  = pageHeight * (1 - scaleFactor) / 2;
                final float horzMargin  = pageWidth * (1 - scaleFactor) / 2;
                if (position < 0)
                {
                    view.setTranslationX(horzMargin - vertMargin / 2);
                }
                else
                {
                    view.setTranslationX(-horzMargin + vertMargin / 2);
                }
                // Scale the page down (between MIN_SCALE and 1)
                view.setScaleX(scaleFactor);
                view.setScaleY(scaleFactor);
                // Fade the page relative to its size.
                view.setAlpha(MIN_ALPHA +
                              (scaleFactor - MIN_SCALE) / (1 - MIN_SCALE) * (1 - MIN_ALPHA));
            }
            else
            { // (1,+Infinity]
                // This page is way off-screen to the right.
                view.setAlpha(0);
            }
        }
    }
    
    class PageCallBack implements ViewPager.OnPageChangeListener
    {
        int lastPage = -1;
        
        @Override
        public void onPageScrolled(final int paramInt1, final float paramFloat, final int paramInt2)
        {
        }
        
        @Override public void onPageSelected(final int paramInt)
        {
            FragmentStatePagerAdapter adapter = (FragmentStatePagerAdapter) viewPager.getAdapter();
            if (adapter == null) { return; }
            try
            {
                if (lastPage != -1)
                {
                    final BasePage last = (BasePage) adapter.getItem(lastPage);
                    last.afterView();
                    vd.devInfo("leaving : " + last.getTitle());
                }
                final BasePage next = (BasePage) adapter.getItem(paramInt);
                next.beforeView();
                vd.devInfo("entering : " + next.getTitle());
                lastPage = paramInt;
            }
            catch (final Exception e)
            {
                vd.throwException(e, "vdPager::onPageSelected");
            }
        }
        
        @Override public void onPageScrollStateChanged(final int paramInt)
        {}
    }
}
