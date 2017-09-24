package com.vdprime.vdlib.views;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Build.VERSION_CODES;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.vdprime.vdlib.R;

/**
 * Created by Burak on 26.08.2017.
 */
public class SplitView extends LinearLayout
{
    TextViewExt textViewExt;
    FrameLayout viewContainer;
    int         percantage;
    float       radius;
    float       border;
    int         borderColor;
    
    public SplitView(final Context context)
    {
        super(context);
        init(null, 0, 0);
    }
    
    void init(
            final AttributeSet attrs, final int defStyleAttr, final int defStyleRes
    )
    {
        float density = getContext().getResources()
                                    .getDisplayMetrics().density;
        textViewExt = new TextViewExt(getContext(), attrs, defStyleAttr);
        viewContainer = new FrameLayout(getContext(), attrs, defStyleAttr);
        textViewExt.setGravity(Gravity.CENTER_VERTICAL | Gravity.RIGHT);
        textViewExt.setTextColor(Color.BLACK);
        textViewExt.setPadding((int) (density * 5),
                               (int) (density * 2),
                               (int) (density * 5),
                               (int) (density * 2));
        addView(textViewExt);
        addView(viewContainer);
        TypedArray typedArray = getContext().obtainStyledAttributes(attrs,
                                                                    R.styleable.SplitView,
                                                                    defStyleAttr,
                                                                    defStyleRes);
        parseArray(typedArray);
        typedArray.recycle();
        GradientDrawable gradientDrawable = new GradientDrawable();
        gradientDrawable.setShape(GradientDrawable.RECTANGLE);
        gradientDrawable.setCornerRadius(radius);
        gradientDrawable.setStroke((int) border, borderColor);
        Drawable backDrawable = getBackground();
        if (backDrawable != null && backDrawable instanceof ColorDrawable)
        {
            ColorDrawable colorDrawable = (ColorDrawable) backDrawable;
            gradientDrawable.setColor(colorDrawable.getColor());
        }
        ViewCompat.setBackground(this, gradientDrawable);
        setOrientation(getOrientation());
    }
    
    @Override public void addView(final View child)
    {
        if (getChildCount() == 2) { viewContainer.addView(child); }
        else { super.addView(child); }
    }
    
    @Override public void addView(final View child, final int index)
    {
        if (getChildCount() == 2) { viewContainer.addView(child, index); }
        else { super.addView(child, index); }
    }
    
    @Override public void addView(final View child, final int width, final int height)
    {
        if (getChildCount() == 2) { viewContainer.addView(child, width, height); }
        else { super.addView(child, width, height); }
    }
    
    @Override public void addView(final View child, final ViewGroup.LayoutParams params)
    {
        if (getChildCount() == 2) { viewContainer.addView(child, params); }
        else { super.addView(child, params); }
    }
    
    @Override
    public void addView(final View child, final int index, final ViewGroup.LayoutParams params)
    {
        if (getChildCount() == 2) { viewContainer.addView(child, index, params); }
        else { super.addView(child, index, params); }
    }
    
    @Override public void removeView(final View view)
    {
        viewContainer.removeView(view);
    }
    
    @Override public void removeViewInLayout(final View view)
    {
        viewContainer.removeViewInLayout(view);
    }
    
    @Override public void removeViewsInLayout(final int start, final int count)
    {
        viewContainer.removeViewsInLayout(start, count);
    }
    
    @Override public void removeViewAt(final int index)
    {
        viewContainer.removeViewAt(index);
    }
    
    @Override public void removeViews(final int start, final int count)
    {
        viewContainer.removeViews(start, count);
    }
    
    @Override public void removeAllViews()
    {
        viewContainer.removeAllViews();
    }
    
    @Override public void removeAllViewsInLayout()
    {
        viewContainer.removeAllViewsInLayout();
    }
    
    private void parseArray(final TypedArray typedArray)
    {
        percantage = typedArray.getInteger(R.styleable.SplitView_percentage, 30);
        radius = typedArray.getDimension(R.styleable.SplitView_radius, 0);
        border = typedArray.getDimension(R.styleable.SplitView_border, 0);
        borderColor = typedArray.getColor(R.styleable.SplitView_borderColor, Color.TRANSPARENT);
    }
    
    public SplitView(final Context context, @Nullable final AttributeSet attrs)
    {
        super(context, attrs);
        init(attrs, 0, 0);
    }
    
    public SplitView(
            final Context context, @Nullable final AttributeSet attrs, final int defStyleAttr
    )
    {
        super(context, attrs, defStyleAttr);
        init(attrs, defStyleAttr, 0);
    }
    
    @TargetApi (VERSION_CODES.LOLLIPOP) public SplitView(
            final Context context,
            final AttributeSet attrs,
            final int defStyleAttr,
            final int defStyleRes
    )
    {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(attrs, defStyleAttr, defStyleRes);
    }
    
    public TextView getLabel()
    {
        return textViewExt;
    }
    
    @Override protected void onMeasure(final int widthMeasureSpec, final int heightMeasureSpec)
    {
        if (getOrientation() == HORIZONTAL)
        {
            LayoutParams params = (LayoutParams) textViewExt.getLayoutParams();
            params.width = 0;
            params.height = viewContainer.getChildCount() == 0
                            ? LayoutParams.WRAP_CONTENT
                            : LayoutParams.MATCH_PARENT;
            params.weight = percantage;
            textViewExt.setLayoutParams(params);
            params = (LayoutParams) viewContainer.getLayoutParams();
            params.width = 0;
            params.height = LayoutParams.WRAP_CONTENT;
            params.weight = 100 - percantage;
            viewContainer.setLayoutParams(params);
        }
        else
        {
            LayoutParams params = (LayoutParams) textViewExt.getLayoutParams();
            params.width = LayoutParams.MATCH_PARENT;
            params.height = LayoutParams.WRAP_CONTENT;
            params.weight = 0;
            textViewExt.setLayoutParams(params);
            params = (LayoutParams) viewContainer.getLayoutParams();
            params.width = LayoutParams.MATCH_PARENT;
            params.height = LayoutParams.WRAP_CONTENT;
            params.weight = 0;
            viewContainer.setLayoutParams(params);
        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }
    
    @Override public void setOrientation(final int orientation)
    {
        super.setOrientation(orientation);
    }
    
    protected boolean anyViewAdded()
    {
        int size = getChildCount();
        for (int i = 0; i < size; i++)
        {
            View v = getChildAt(i);
            if (v instanceof TextViewExt) continue;
            return true;
        }
        return false;
    }
    
    private class TextViewExt extends android.support.v7.widget.AppCompatTextView
    {
        public TextViewExt(final Context context)
        {
            super(context);
        }
        
        public TextViewExt(final Context context, final AttributeSet attrs)
        {
            super(context, attrs);
        }
        
        public TextViewExt(final Context context, final AttributeSet attrs, final int defStyleAttr)
        {
            super(context, attrs, defStyleAttr);
        }
    }
}
