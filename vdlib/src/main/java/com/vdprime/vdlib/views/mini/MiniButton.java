package com.vdprime.vdlib.views.mini;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build.VERSION_CODES;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

import com.vdprime.vdlib.R;
import com.vdprime.vdlib.utils.vdstring;
import com.vdprime.vdlib.views.BaseView;

/**
 * Created by Burak on 19.07.2017.
 */
public class MiniButton extends BaseView
{
    private TextView Title;
    private TextView subTitle;
    private String   _title;
    private String   _subtitle;
    private String   defaultTitle;
    private String   defaultSubtitle;
    private View     seperator;
    private boolean  _seperator;
    
    public MiniButton(Context context)
    {
        super(context);
    }
    
    public MiniButton(
            Context context, @Nullable AttributeSet attrs
    )
    {
        super(context, attrs);
    }
    
    public MiniButton(
            Context context, @Nullable AttributeSet attrs, int defStyleAttr
    )
    {
        super(context, attrs, defStyleAttr);
    }
    
    @TargetApi (VERSION_CODES.LOLLIPOP)
    public MiniButton(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes)
    {
        super(context, attrs, defStyleAttr, defStyleRes);
    }
    
    @Override public int getLayoutResource()
    {
        return R.layout.mini_button_layout;
    }
    
    @Override public int[] getLayoutStyle()
    {
        return R.styleable.MiniButtonStyle;
    }
    
    @Override public void prepareView()
    {
        Title = (TextView) findViewById(R.id.mini_title);
        subTitle = (TextView) findViewById(R.id.mini_subtitle);
        seperator = findViewById(R.id.mini_seperator);
        Title.setText(_title);
        subTitle.setText(_subtitle);
        seperator.setVisibility(_seperator ? VISIBLE : GONE);
    }
    
    public void parseArray(final TypedArray a)
    {
        defaultTitle = getResources().getString(R.string.mini_button_default_title);
        defaultSubtitle = getResources().getString(R.string.mini_button_default_subtitle);
        _title = a.getString(R.styleable.MiniButtonStyle_button_title);
        _subtitle = a.getString(R.styleable.MiniButtonStyle_button_subtitle);
        _seperator = a.getBoolean(R.styleable.MiniButtonStyle_button_sep, false);
        if (vdstring.isNullorEmpty(_title)) _title = defaultTitle;
        if (vdstring.isNullorEmpty(_subtitle)) _subtitle = defaultSubtitle;
    }
    
    public TextView getTitle()
    {
        return Title;
    }
    
    public TextView getSubTitle()
    {
        return subTitle;
    }
}
