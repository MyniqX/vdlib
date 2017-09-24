package com.vdprime.vdlib.views;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.TextView;

import com.vdprime.vdlib.R;

/**
 * Created by Burak on 28.08.2017.
 */
public class SweetButton extends BaseView
{
    ImageView iconView;
    TextView  textView;
    String    title;
    Drawable  drawable;
    int textPadding   = 0;
    int textColor     = 0;
    int textBackColor = 0;
    int imgBackColor  = 0;
    int Size          = 0;
    
    public SweetButton(final Context context)
    {
        super(context);
    }
    
    public SweetButton(final Context context, @Nullable final AttributeSet attrs)
    {
        super(context, attrs);
    }
    
    public ImageView getIcon()
    {return iconView;}
    
    public TextView getLabel()
    {
        return textView;
    }
    
    @Override public int getLayoutResource()
    {
        return R.layout.vdlib_sweetbutton_layout;
    }
    
    @Override public int[] getLayoutStyle()
    {
        return R.styleable.SweetButton;
    }
    
    @Override public void parseArray(final TypedArray typedArray)
    {
        title = typedArray.getString(R.styleable.SweetButton_text);
        drawable = typedArray.getDrawable(R.styleable.SweetButton_src);
        textPadding = typedArray.getDimensionPixelSize(R.styleable.SweetButton_textPadding, 0);
        textColor = typedArray.getColor(R.styleable.SweetButton_textColor, Color.BLACK);
        textBackColor = typedArray.getColor(R.styleable.SplitView_textBackColor, Color.WHITE);
        imgBackColor = typedArray.getColor(R.styleable.SweetButton_imgBack, Color.WHITE);
        Size = typedArray.getDimensionPixelSize(R.styleable.SweetButton_size, 0);
    }
    
    @Override public void prepareView()
    {
        //Views
        textView = (TextView) rootView.findViewById(R.id.vdlib_sweet_text);
        iconView = (ImageView) rootView.findViewById(R.id.vdlib_sweet_icon);
        //   if(isInEditMode()) return;
        //Values
        textView.setText(title == null ? "?? - ??" : title);
        if (drawable == null) { iconView.setImageResource(R.drawable.cancel_icon); }
        else { iconView.setImageDrawable(drawable); }
        //Dimension
        int density = (int) getContext().getResources()
                                        .getDisplayMetrics().density;
        Size = Size == 0 ? density * 32 : Size;
        //Icon layout
        LayoutParams params = (LayoutParams) iconView.getLayoutParams();
        params.height = Size;
        params.width = Size;
        params.leftMargin = -Size;
        iconView.setLayoutParams(params);
        //Text layout
        params = (LayoutParams) textView.getLayoutParams();
        params.height = Size - (textPadding * 2);
        textView.setLayoutParams(params);
        //Text props
        textView.setTextColor(textColor);
        textView.setPadding(9 * density + Size / 3, 0, Size + density * 9, 0);
        //View backgrounds
        GradientDrawable circleDrawable = new GradientDrawable();
        circleDrawable.setShape(GradientDrawable.OVAL);
        circleDrawable.setStroke(density, Color.BLACK);
        circleDrawable.setColor(imgBackColor);
        ViewCompat.setBackground(iconView, circleDrawable);
        GradientDrawable ovalDrawable = new GradientDrawable();
        ovalDrawable.setShape(GradientDrawable.RECTANGLE);
        ovalDrawable.setColor(textBackColor);
        ovalDrawable.setStroke(density, Color.BLACK);
        ovalDrawable.setCornerRadius(Size / 2);
        ViewCompat.setBackground(textView, ovalDrawable);
        setClickable(true);
        setFocusable(true);
    }
}