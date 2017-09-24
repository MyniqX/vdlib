package com.vdprime.vdlib.utils.inject;
import android.app.Activity;
import android.support.annotation.IdRes;
import android.support.v4.app.FragmentActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.View.OnLongClickListener;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;

import com.vdprime.vdlib.activities.vdActivity;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * Created by Burak on 13.09.2017.
 */
public class InjectView
{
    public static void BindView(
            vdActivity activity, Field f, Object obj, View view, StringBuilder stringBuilder
    )
    {
        if (view == null)
        {
            NoViewFound("BindView", obj, f.getName(), activity);
            return;
        }
        try
        {
            f.set(obj, view);
            if (stringBuilder != null)
            { PrintSuccess("BindView", obj, f.getName(), view, stringBuilder); }
        }
        catch (Exception e)
        {
            EventError("BindView", obj, view, e, activity);
        }
    }
    
    private static void NoViewFound(String title, Object obj, String fname, vdActivity activity)
    {
        activity.devErr("!! %s(%s): View not found for use of %s",
                        title,
                        obj.getClass()
                           .getSimpleName(),
                        fname);
    }
    
    private static void PrintSuccess(
            final String title,
            final Object obj,
            final String fname,
            final View view,
            final StringBuilder stringBuilder
    )
    {
        stringBuilder.append(title)
                     .append("(")
                     .append(obj.getClass()
                                .getSimpleName())
                     .append("): Event added for ")
                     .append(view.getClass()
                                 .getSimpleName())
                     .append(" -> ")
                     .append(fname)
                     .append(";\n");
    }
    
    private static void EventError(
            String title, Object obj, View v, Exception e, vdActivity activity
    )
    {
        String text = String.format("%s(%s) on view %s",
                                    title,
                                    obj.getClass()
                                       .getSimpleName(),
                                    v.getClass()
                                     .getSimpleName());
        activity.printStack(text, e);
    }
    
    public static void OnClick(
            final vdActivity activity,
            final Method m,
            final Object obj,
            final View view,
            StringBuilder stringBuilder
    )
    {
        if (view == null)
        {
            NoViewFound("OnClick", obj, m.getName(), activity);
            return;
        }
        if (stringBuilder != null)
        {
            PrintSuccess("OnClick", obj, m.getName(), view, stringBuilder);
        }
        view.setOnClickListener(new OnClickListener()
        {
            @Override public void onClick(final View v)
            {
                try
                {
                    m.invoke(obj);
                }
                catch (Exception e)
                {
                    EventError("OnClick", obj, view, e, activity);
                }
            }
        });
    }
    
    public final static void OnCheck(
            final vdActivity activity,
            final Method m,
            final Object obj,
            final View view,
            StringBuilder stringBuilder
    )
    {
        if (view == null || (view instanceof CompoundButton) == false)
        {
            NoViewFound("OnCheck", obj, m.getName(), activity);
            return;
        }
        final CompoundButton compoundButton = (CompoundButton) view;
        if (stringBuilder != null)
        {
            PrintSuccess("OnCheck", obj, m.getName(), view, stringBuilder);
        }
        compoundButton.setOnCheckedChangeListener(new OnCheckedChangeListener()
        {
            @Override public void onCheckedChanged(
                    final CompoundButton buttonView, final boolean isChecked
            )
            {
                try
                {
                    m.invoke(obj, isChecked, buttonView);
                }
                catch (Exception e)
                {
                    EventError("OnCheck", obj, view, e, activity);
                }
            }
        });
    }
    
    public static void OnLongClick(
            final vdActivity activity,
            final Method m,
            final Object obj,
            final View view,
            StringBuilder stringBuilder
    )
    {
        if (view == null)
        {
            NoViewFound("OnLongClick", obj, m.getName(), activity);
            return;
        }
        if (stringBuilder != null)
        {
            PrintSuccess("OnLongClick", obj, m.getName(), view, stringBuilder);
        }
        view.setOnLongClickListener(new OnLongClickListener()
        {
            @Override public boolean onLongClick(final View v)
            {
                try
                {
                    return (boolean) m.invoke(obj);
                }
                catch (Exception e)
                {
                    EventError("OnLongClick", obj, view, e, activity);
                }
                return false;
            }
        });
    }
    
    public static IFindView getInstance(final Object obj)
    {
        if (obj == null) return null;
        if (obj instanceof View)
        {
            return new IFindView()
            {
                @Override public View findViewByID(final int resID)
                {
                    return ((View) obj).findViewById(resID);
                }
            };
        }
        if (obj instanceof Activity)
        {
            return new IFindView()
            {
                @Override public View findViewByID(final int resID)
                {
                    return ((Activity) obj).findViewById(resID);
                }
            };
        }
        if (obj instanceof FragmentActivity)
        {
            return new IFindView()
            {
                @Override public View findViewByID(final int resID)
                {
                    return ((FragmentActivity) obj).findViewById(resID);
                }
            };
        }
        return null;
    }
    
    public static void OnFocus(
            final vdActivity activity,
            final Method m,
            final Object obj,
            final View view,
            StringBuilder stringBuilder
    )
    {
        if (view == null)
        {
            NoViewFound("OnFocus", obj, m.getName(), activity);
            return;
        }
        view.setOnFocusChangeListener(new OnFocusChangeListener()
        {
            @Override public void onFocusChange(final View v, final boolean hasFocus)
            {
                try
                {
                    m.invoke(obj, hasFocus);
                }
                catch (Exception e)
                {
                    EventError("OnFocus", obj, view, e, activity);
                }
            }
        });
        if (stringBuilder != null) PrintSuccess("OnFocus", obj, m.getName(), view, stringBuilder);
    }
    
    public static void OnTextChanged(
            final vdActivity activity,
            final Method method,
            final Object obj,
            final View view,
            final StringBuilder stringBuilder
    )
    {
        if (view == null || (view instanceof EditText) == false)
        {
            NoViewFound("OnFocus", obj, method.getName(), activity);
            return;
        }
        final EditText editText = (EditText) view;
        editText.addTextChangedListener(new TextWatcher()
        {
            @Override public void beforeTextChanged(
                    final CharSequence s, final int start, final int count, final int after
            )
            {
                InvokeText(activity,
                           editText,
                           method,
                           obj,
                           TextChangeEvent.BEFORE,
                           null,
                           s,
                           start,
                           count,
                           after);
            }
            
            @Override public void onTextChanged(
                    final CharSequence s, final int start, final int before, final int count
            )
            {
                InvokeText(activity,
                           editText,
                           method,
                           obj,
                           TextChangeEvent.CHANGE,
                           null,
                           s,
                           start,
                           before,
                           count);
            }
            
            @Override public void afterTextChanged(final Editable s)
            {
                InvokeText(activity,
                           editText,
                           method,
                           obj,
                           TextChangeEvent.AFTER,
                           s,
                           null,
                           0,
                           0,
                           0);
            }
        });
        if (stringBuilder != null) PrintSuccess("OnFocus", obj, method.getName(), view, stringBuilder);
    }
    
    static void InvokeText(
            vdActivity activity,
            EditText editText,
            Method method,
            Object obj,
            TextChangeEvent textChangeEvent,
            Editable editable,
            CharSequence sequence,
            int start,
            int before,
            int count
    )
    {
        try
        {
           // method.invoke(obj, textChangeEvent, editable, sequence, start, before, count);
            method.invoke(obj,textChangeEvent,editText.getText());
        }
        catch (Exception e)
        {
            EventError("OnTextChanged", obj, editText, e, activity);
        }
    }
    
    public enum TextChangeEvent
    {
        BEFORE, AFTER, CHANGE
    }
    
    public interface IFindView
    {
        View findViewByID(@IdRes int resID);
    }
}
