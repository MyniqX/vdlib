package com.vdprime.vdlib.views;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build.VERSION_CODES;
import android.support.annotation.Keep;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;

import com.vdprime.vdlib.activities.vdActivity;

/**
 * Created by Burak on 20.08.2017.
 */
@Keep public abstract class BaseView extends LinearLayout
{
    protected View       rootView;
    protected vdActivity vd;
    
    public BaseView(final Context context)
    {
        super(context);
        initialize(null, 0, 0);
    }
    
    protected void initialize(AttributeSet attrs, int defStyleAttr, int defStyleRes)
    {
        if (isInEditMode() == false)
        {
            vd = (vdActivity) getContext();
            vd.injectFields(this);
        }
        int layoutResource = getLayoutResource();
        if (layoutResource >= 0)
        {
            rootView = inflate(getContext(), getLayoutResource(), this);
            if (isInEditMode() == false)
            {
                vd.injectViews(this, rootView);
            }
        }
        else { rootView = this; }
        int[] layoutStyle = getLayoutStyle();
        if (layoutStyle != null && layoutStyle.length > 0)
        {
            TypedArray typedArray = getContext().obtainStyledAttributes(attrs,
                                                                        layoutStyle,
                                                                        defStyleAttr,
                                                                        defStyleRes);
            parseArray(typedArray);
            typedArray.recycle();
        }
        prepareView();
    }
    
    public abstract int getLayoutResource();
    public abstract int[] getLayoutStyle();
    public abstract void parseArray(TypedArray typedArray);
    public abstract void prepareView();
    
    public BaseView(
            final Context context, @Nullable final AttributeSet attrs
    )
    {
        super(context, attrs);
        initialize(attrs, 0, 0);
    }
    
    public BaseView(
            final Context context, @Nullable final AttributeSet attrs, final int defStyleAttr
    )
    {
        super(context, attrs, defStyleAttr);
        initialize(attrs, defStyleAttr, 0);
    }
    
    @TargetApi (VERSION_CODES.LOLLIPOP) public BaseView(
            final Context context,
            final AttributeSet attrs,
            final int defStyleAttr,
            final int defStyleRes
    )
    {
        super(context, attrs, defStyleAttr, defStyleRes);
        initialize(attrs, defStyleAttr, defStyleRes);
    }
}
