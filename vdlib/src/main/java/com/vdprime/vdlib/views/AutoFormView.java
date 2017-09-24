package com.vdprime.vdlib.views;
import android.content.Context;
import android.text.Editable;
import android.text.TextUtils.TruncateAt;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.vdprime.vdlib.activities.vdActivity;
import com.vdprime.vdlib.enums.Colors;
import com.vdprime.vdlib.utils.vdparse;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Burak on 20.08.2017.
 */
public class AutoFormView extends LinearLayout
{
    Object obj;
    List<AutoFormField> fields = new ArrayList<>();
    private vdActivity vd;
    
    public AutoFormView(final Context context, final AttributeSet attrs)
    {
        super(context, attrs);
        vd = (vdActivity) context;
    }
    
    public void prepareForm(final Object clazz)
    {
        removeAllViews();
        apply();
        fields.clear();
        obj = clazz;
        if (obj == null) { return; }
        final Class<?> clz = clazz.getClass();
        /* TODO */
        final Field[]  fs  = null; // vd.getReflection().getAllFields(clz);
        for (final Field field : fs)
        {
            if (isFieldOkey(field) == false)
            {
                continue;
            }
            try
            {
                fields.add(new AutoFormField(field, getContext()));
            }
            catch (final Exception e)
            {
                e.printStackTrace();
            }
        }
        final Button apply = new Button(getContext());
        apply.setText("Apply");
        apply.setOnClickListener(new OnClickListener()
        {
            @Override public void onClick(final View view)
            {
                AutoFormView.this.apply();
            }
        });
        final LayoutParams lp = new LayoutParams(LayoutParams.MATCH_PARENT,
                                                 LayoutParams.WRAP_CONTENT);
        addView(apply, lp);
    }
    
    public void apply()
    {
        if (obj == null) { return; }
        for (int i = 0; i < fields.size(); i++)
        {
            try
            {
                fields.get(i)
                      .apply();
            }
            catch (final Exception e)
            {
                e.printStackTrace();
            }
        }
    }
    
    boolean isFieldOkey(final Field f)
    {
        return f.getAnnotation(AutoFormHide.class) == null;
    }
    
    @Retention (RetentionPolicy.RUNTIME) @Target (ElementType.FIELD)
    public static @interface AutoFormHide
    {}
    
    class AutoFormField extends LinearLayout implements OnItemSelectedListener, TextWatcher
    {
        public Field    field;
        public TextView label;
        public View     actor;
        public Object   defaultValue;
        public Object   value;
        public Class<?> type;
        
        public AutoFormField(final Field f, final Context context) throws Exception
        {
            super(context);
            field = f;
            setOrientation(HORIZONTAL);
            LayoutParams lp = new LayoutParams(LayoutParams.MATCH_PARENT,
                                               LayoutParams.WRAP_CONTENT);
            setLayoutParams(lp);
            //build label
            label = new TextView(context);
            lp = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
            lp.weight = .3f;
            label.setLayoutParams(lp);
            label.setText(field.getName());
            label.setEllipsize(TruncateAt.MARQUEE);
            label.setMarqueeRepeatLimit(-1);
            label.setSelected(true);
            // value part
            value = defaultValue = f.get(obj);
            type = f.getType();
            if (type.getClass()
                    .isAssignableFrom(Enum.class))
            {
                final Spinner spinner = new Spinner(getContext());
                spinner.setAdapter(new ArrayAdapter<>(context,
                                                      android.R.layout.simple_spinner_item,
                                                      type.getEnumConstants()));
                spinner.setOnItemSelectedListener(this);
                spinner.post(new Runnable()
                {
                    @Override public void run()
                    {
                        spinner.setSelection(((Enum<?>) value).ordinal());
                    }
                });
                actor = spinner;
            }
            else
            {
                final EditText editText = new EditText(context);
                editText.setText(value == null ? "" : value.toString());
                editText.addTextChangedListener(this);
            }
            lp = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
            lp.weight = .7f;
            actor.setLayoutParams(lp);
            lp = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
            AutoFormView.this.addView(this, lp);
        }
        
        public void apply() throws Exception
        {
            field.set(obj, value);
        }
        
        @Override public void beforeTextChanged(
                final CharSequence s, final int start, final int count, final int after
        )
        {}
        
        @Override public void onTextChanged(
                final CharSequence s, final int start, final int before, final int count
        )
        {}
        
        @Override public void afterTextChanged(final Editable s)
        {
            final String str = s.toString();
            Colors       bg  = Colors.blue;
            try
            {
                final Object newVal = vdparse.parse(str, type);
                value = newVal;
            }
            catch (final Exception e)
            {
                bg = Colors.red;
            }
            setBackgroundColor(bg.getColor());
        }
        
        @Override public void onItemSelected(
                final AdapterView<?> parent, final View view, final int position, final long id
        )
        {
            value = type.getEnumConstants()[position];
            setBackgroundColor(Colors.blue.getColor());
        }
        
        @Override public void onNothingSelected(final AdapterView<?> parent)
        {}
    }
}
