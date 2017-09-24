package com.vdprime.vdlib.views.mini;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build.VERSION_CODES;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.Switch;
import android.widget.TextView;

import com.vdprime.vdlib.R;
import com.vdprime.vdlib.utils.vdstring;
import com.vdprime.vdlib.views.BaseView;

/**
 * Created by Burak on 18.07.2017.
 */
public class MiniSwitch extends BaseView
{
    TextView subTitle;
    TextView Title;
    Switch   aSwitch;
    IMiniSwitch miniSwitchCallback = null;
    String _positive, _negative, _title;
    String  defaultTitle;
    String  defaultPositive;
    String  defaultNegative;
    View    seperator;
    boolean _seperator;
    boolean _value;
    
    public MiniSwitch(Context context)
    {
        super(context);
    }
    
    public MiniSwitch(
            Context context, @Nullable AttributeSet attrs
    )
    {
        super(context, attrs);
    }
    
    public MiniSwitch(
            Context context, @Nullable AttributeSet attrs, int defStyleAttr
    )
    {
        super(context, attrs, defStyleAttr);
    }
    
    @TargetApi (VERSION_CODES.LOLLIPOP)
    public MiniSwitch(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes)
    {
        super(context, attrs, defStyleAttr, defStyleRes);
    }
    
    @Override public int getLayoutResource()
    {
        return R.layout.mini_switch_layout;
    }
    
    @Override public int[] getLayoutStyle()
    {
        return R.styleable.MiniSwitchStyle;
    }
    
    public void parseArray(final TypedArray a)
    {
        defaultTitle = getResources().getString(R.string.mini_switch_default_title);
        defaultPositive = getResources().getString(R.string.mini_switch_default_positive);
        defaultNegative = getResources().getString(R.string.mini_switch_default_negative);
        _title = a.getString(R.styleable.MiniSwitchStyle_switch_title);
        _positive = a.getString(R.styleable.MiniSwitchStyle_switch_positive_message);
        _negative = a.getString(R.styleable.MiniSwitchStyle_switch_negative_message);
        _seperator = a.getBoolean(R.styleable.MiniSwitchStyle_switch_sep, false);
        if (vdstring.isNullorEmpty(_title)) _title = defaultTitle;
        if (vdstring.isNullorEmpty(_positive)) _positive = defaultPositive;
        if (vdstring.isNullorEmpty(_negative)) _negative = defaultNegative;
        _value = a.getBoolean(R.styleable.MiniSwitchStyle_switch_value, true);
    }
    
    @Override public void prepareView()
    {
        subTitle = (TextView) findViewById(R.id.mini_subtitle);
        Title = (TextView) findViewById(R.id.mini_title);
        aSwitch = (Switch) findViewById(R.id.mini_switch);
        seperator = findViewById(R.id.mini_seperator);
        seperator.setVisibility(_seperator ? VISIBLE : GONE);
        aSwitch.setOnCheckedChangeListener(new OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(final CompoundButton buttonView, final boolean isChecked)
            {
                if (_positive != null)
                {
                    subTitle.setText(aSwitch.isChecked() ? _positive : _negative);
                }
                if (miniSwitchCallback == null) return;
                miniSwitchCallback.onSwitch(aSwitch.isChecked());
            }
        });
        Title.setText(_title);
        if (isInEditMode())
        {
            subTitle.setText(String.format("%s\n%s", _positive, _negative));
        }
        update(_value);
    }
    
    /**
     * update switch without trigger evet
     *
     * @param value is new value of switch
     */
    public void update(boolean value)
    {
        IMiniSwitch cb = miniSwitchCallback;
        miniSwitchCallback = null;
        aSwitch.setChecked(value);
        miniSwitchCallback = cb;
    }
    
    public MiniSwitch set(String title, String subtitle, boolean checked, IMiniSwitch callback)
    {
        Title.setText(title);
        subTitle.setText(subtitle);
        aSwitch.setChecked(checked);
        return setOnChangeListener(callback);
    }
    
    public MiniSwitch setOnChangeListener(IMiniSwitch miniSwitchCallback)
    {
        this.miniSwitchCallback = miniSwitchCallback;
        return this;
    }
    
    public MiniSwitch set(
            String title, String positive, String negative, boolean checked, IMiniSwitch callback
    )
    {
        Title.setText(title);
        subTitle.setText(checked ? positive : negative);
        aSwitch.setChecked(checked);
        _positive = positive;
        _negative = negative;
        return setOnChangeListener(callback);
    }
    
    public interface IMiniSwitch
    {
        void onSwitch(boolean value);
    }
}
