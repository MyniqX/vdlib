package com.vdprime.vdlib.utils.inject;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.vdprime.vdlib.activities.vdActivity;

import java.lang.reflect.Field;

/**
 * Created by Burak on 15.09.2017.
 */
public class InjectFields
{
    /**
     * Inject vdActivity instance to field
     *
     * @param activity
     * @param f
     * @param obj
     * @param stringBuilder
     */
    public static void BindActivity(
            vdActivity activity, Field f, Object obj, StringBuilder stringBuilder
    )
    {
        try
        {
            f.set(obj, activity);
            if (stringBuilder != null)
            {
                PrintSuccess("BindActivity", obj, f, activity, stringBuilder);
            }
        }
        catch (Exception e)
        {
            activity.devErr("Set object error, class : %s, object : %s",
                            f.getType()
                             .getSimpleName(),
                            obj.getClass()
                               .getSimpleName());
        }
    }
    
    static void PrintSuccess(String name, Object obj, Field field, Object value, StringBuilder sb)
    {
        sb.append(name)
          .append("(")
          .append(obj.getClass()
                     .getSimpleName())
          .append(") : ")
          .append(field.getName())
          .append(" = ")
          .append(value)
          .append(";\n");
    }
    
    /**
     * Inject database to object
     *
     * @param activity
     * @param f             must be implement vdIDatabase
     * @param obj
     * @param stringBuilder
     *
     * @return
     */
    public static boolean BindDatabase(
            vdActivity activity, Field f, Object obj, StringBuilder stringBuilder
    )
    {
        Class c = f.getType();
        try
        {
            f.set(obj, activity.loadDatabase(c));
            if (stringBuilder != null)
            {
                PrintSuccess("BindDatabase", obj, f, c, stringBuilder);
            }
        }
        catch (Exception e)
        {
            activity.devErr("Set object error, class : %s, object : %s",
                            c.getSimpleName(),
                            obj.getClass()
                               .getSimpleName());
        } return true;
    }
    
    /**
     * @param activity
     * @param f
     * @param obj
     * @param bindDimen
     * @param stringBuilder
     *
     * @return
     */
    public static final boolean BindDimension(
            vdActivity activity,
            Field f,
            Object obj,
            BindDimen bindDimen,
            StringBuilder stringBuilder
    )
    {
        int dimen = activity.getResources()
                            .getDimensionPixelSize(bindDimen.value());
        try
        {
            f.set(obj, dimen);
            if (stringBuilder != null)
            {
                PrintSuccess("BindDimension", obj, f, dimen, stringBuilder);
            }
        }
        catch (Exception e)
        {
            activity.devErr("BindDimen(%s) error, field: %s, error: %s",
                            obj.getClass()
                               .getSimpleName(),
                            f.getName(),
                            e.getMessage());
        }
        return true;
    }
    
    public static final boolean BindAnimation(
            vdActivity activity,
            Field f,
            Object obj,
            BindAnim bindAnim,
            StringBuilder stringBuilder
    )
    {
        Animation animation = AnimationUtils.loadAnimation(activity,bindAnim.value());
        try
        {
            f.set(obj, animation);
            if (stringBuilder != null)
            {
                PrintSuccess("BindAnimation", obj, f, animation, stringBuilder);
            }
        }
        catch (Exception e)
        {
            activity.devErr("BindAnimation(%s) error, field: %s, error: %s",
                            obj.getClass()
                               .getSimpleName(),
                            f.getName(),
                            e.getMessage());
        }
        return true;
    }

    public static boolean BindString(
            vdActivity activity,
            Field f,
            Object obj,
            BindString bindString,
            StringBuilder stringBuilder
    )
    {
        String txt = activity.getResources()
                             .getString(bindString.value());
        try
        {
            f.set(obj, txt);
            if (stringBuilder != null)
            {
                PrintSuccess("BindString", obj, f, txt, stringBuilder);
            }
        }
        catch (Exception e)
        {
            activity.devErr("BindString(%s) error, field: %s, error: %s",
                            obj.getClass()
                               .getSimpleName(),
                            f.getName(),
                            e.getMessage());
        }
        return true;
    }
    
    public static final boolean InjectAny(
            vdActivity activity,
            Field f,
            Object obj,
            StringBuilder stringBuilder
    )
    {
        Class clzz = f.getType();
        for (Object o : activity.getInjectItems())
        {
            if (o.getClass() != clzz) continue;
            try
            {
                f.set(obj, o);
                if (stringBuilder != null)
                {
                    PrintSuccess("InjectAny", obj, f, o.getClass(), stringBuilder);
                }
            }
            catch (Exception e)
            {
                activity.devErr("InjectAny(%s) error, field: %s, error: %s",
                                obj.getClass()
                                   .getSimpleName(),
                                f.getName(),
                                e.getMessage());
            }
            return true;
        }

        Object o = activity.createInstance(clzz);
        if (o == null)
        {
            activity.devErr("InjectAny(%s), createInstance failed for field %s",
                            obj.getClass()
                               .getSimpleName(),
                            f.getName());
            return true;
        }
        activity.getInjectItems()
                .add(o);
        try
        {
            f.set(obj, o);
            if (activity.isDebugMode())
            {
                activity.devInfo("Inject(%s): %s to %s",
                                 obj.getClass()
                                    .getSimpleName(),
                                 f.getName(),
                                 o.getClass()
                                  .getSimpleName());
            }
        }
        catch (Exception e)
        {
            activity.devErr("InjectAny(%s) error, field: %s, error: %s",
                            obj.getClass()
                               .getSimpleName(),
                            f.getName(),
                            e.getMessage());
        }
        return true;
    }
}
