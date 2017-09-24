package com.vdprime.vdlib.views.mini;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build.VERSION_CODES;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.vdprime.vdlib.R;
import com.vdprime.vdlib.utils.vdstring;
import com.vdprime.vdlib.views.BaseView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Burak on 19.07.2017.
 */
public class MiniYesNo extends BaseView
{
    TextView    Title;
    ImageButton YesButton;
    ImageButton NoButton;
    IMiniYesNoCallback miniYesNoCallback = null;
    String             messageText       = "";
    String defaultMessage;
    int TimeOut = 0;
    View seperator;
    boolean _seperator;
    List<MiniYesNo> groupButtons = new ArrayList<>();
    
    public MiniYesNo(Context context)
    {
        super(context);
    }
    
    public MiniYesNo(
            Context context, @Nullable AttributeSet attrs
    )
    {
        super(context, attrs);
    }
    
    public MiniYesNo(
            Context context, @Nullable AttributeSet attrs, int defStyleAttr
    )
    {
        super(context, attrs, defStyleAttr);
    }
    
    @TargetApi (VERSION_CODES.LOLLIPOP)
    public MiniYesNo(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes)
    {
        super(context, attrs, defStyleAttr, defStyleRes);
    }
    
    @Override public int getLayoutResource()
    {
        return R.layout.mini_yesno_layout;
    }
    
    @Override public int[] getLayoutStyle()
    {
        return R.styleable.MiniYesNoStyle;
    }
    
    @Override public void prepareView()
    {
        Title = (TextView) findViewById(R.id.mini_yesno_title);
        YesButton = (ImageButton) findViewById(R.id.mini_button_yes);
        NoButton = (ImageButton) findViewById(R.id.mini_button_no);
        seperator = findViewById(R.id.mini_seperator);
        Title.setText(messageText);
        YesButton.setOnClickListener(new OnClickListener()
        {
            @Override public void onClick(final View v)
            {
                if (miniYesNoCallback != null) miniYesNoCallback.onEvenet(true);
                Deactive();
            }
        });
        NoButton.setOnClickListener(new OnClickListener()
        {
            @Override public void onClick(final View v)
            {
                if (miniYesNoCallback != null) miniYesNoCallback.onEvenet(false);
                Deactive();
            }
        });
        seperator.setVisibility(_seperator ? VISIBLE : GONE);
    }
    
    public void parseArray(final TypedArray a)
    {
        defaultMessage = getResources().getString(R.string.mini_yesno_default_message);
        messageText = a.getString(R.styleable.MiniYesNoStyle_yesno_message);
        TimeOut = a.getInteger(R.styleable.MiniYesNoStyle_yesno_timeout, 0);
        _seperator = a.getBoolean(R.styleable.MiniYesNoStyle_yesno_sep, false);
        if (vdstring.isNullorEmpty(messageText)) messageText = defaultMessage;
        if (TimeOut > 0 && isInEditMode() == false)
        {
            Deactive();
        }
    }
    
    public MiniYesNo set(String title, IMiniYesNoCallback callback)
    {
        getTitle().setText(title);
        miniYesNoCallback = callback;
        return this;
    }
    
    public void addGroup(MiniYesNo... buttons)
    {
        if (buttons == null) return;
        groupButtons.addAll(Arrays.asList(buttons));
    }
    
    public void Deactive(boolean iftimerset)
    {
        if (iftimerset == false || TimeOut > 0) Deactive();
    }
    
    public void Active()
    {
        if (getVisibility() == VISIBLE) return;
        setVisibility(VISIBLE);
        for (int i = 0; i < groupButtons.size(); i++)
        {
            try
            {
                groupButtons.get(i)
                            .Deactive(true);
            }
            catch (Exception ignored) {}
        }
        if (TimeOut > 0)
        {
            postDelayed(new Runnable()
            {
                @Override public void run()
                {
                    Deactive();
                }
            }, TimeOut * 1000);
        }
    }
    
    public void Toggle()
    {
        if (getVisibility() == VISIBLE) { Deactive(); }
        else { Active(); }
    }
    
    public void Deactive()
    {
        setVisibility(GONE);
        removeCallbacks(null);
    }
    
    public MiniYesNo setOnChangeCallback(IMiniYesNoCallback cb)
    {
        miniYesNoCallback = cb;
        return this;
    }
    
    public TextView getTitle()
    {
        return Title;
    }
    
    public interface IMiniYesNoCallback
    {
        void onEvenet(boolean positive);
    }
}
