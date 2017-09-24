package com.vdprime.vdlib.utils;
import com.vdprime.vdlib.activities.vdActivity;
import com.vdprime.vdlib.utils.inject.BindAnim;
import com.vdprime.vdlib.utils.inject.BindDB;
import com.vdprime.vdlib.utils.inject.BindDimen;
import com.vdprime.vdlib.utils.inject.BindString;
import com.vdprime.vdlib.utils.inject.BindVD;
import com.vdprime.vdlib.utils.inject.BindView;
import com.vdprime.vdlib.utils.inject.Inject;
import com.vdprime.vdlib.utils.inject.InjectFields;
import com.vdprime.vdlib.utils.inject.InjectView;
import com.vdprime.vdlib.utils.inject.InjectView.IFindView;
import com.vdprime.vdlib.utils.inject.OnCheck;
import com.vdprime.vdlib.utils.inject.OnClick;
import com.vdprime.vdlib.utils.inject.OnFocus;
import com.vdprime.vdlib.utils.inject.OnLongClick;
import com.vdprime.vdlib.utils.inject.OnTextChanged;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created by Burak on 20.08.2017.
 */
public class vdReflection
{
    private Map<Class<?>, Field[]>  fieldMap  = new HashMap<Class<?>, Field[]>();
    private Map<Class<?>, Method[]> methodMap = new HashMap<>();
    private vdActivity activity;
    private StringBuilder stringBuilder = new StringBuilder(1024);
    
    public vdReflection(vdActivity activity)
    {
        this.activity = activity;
    }
    
    public void injectFields(Object obj)
    {
        long    time   = System.currentTimeMillis();
        int     count  = 0;
        boolean isroot = false;
        if (activity.isDebugMode())
        {
            if (stringBuilder.length() > 0) { stringBuilder.append(" --> "); }
            else
            {
                stringBuilder.append("\n ## ");
                isroot = true;
            }
            stringBuilder.append("injectFields begin for ")
                         .append(obj.getClass()
                                    .getSimpleName())
                         .append("\n");
        }
        Field[] fields = getAllFields(obj.getClass());
        for (int i = 0; i < fields.length; i++)
        {
            Field        f          = fields[i];
            Annotation[] annotation = f.getDeclaredAnnotations();
            if (annotation.length == 0) continue;
            Class clz = annotation[0].annotationType();
            if (clz == BindDB.class) { InjectFields.BindDatabase(activity, f, obj, stringBuilder); }
            else if (clz == BindVD.class)
            { InjectFields.BindActivity(activity, f, obj, stringBuilder); }
            else if (clz == Inject.class)
            {
                InjectFields.InjectAny(activity, f, obj, stringBuilder);
            }
            else if (clz == BindDimen.class)
            {
                InjectFields.BindDimension(activity,
                                           f,
                                           obj,
                                           (BindDimen) annotation[0],
                                           stringBuilder);
            }
            else if (clz == BindAnim.class)
            {
                InjectFields.BindAnimation(activity,
                                           f,
                                           obj,
                                           (BindAnim) annotation[0],
                                           stringBuilder);
            }
            else if (clz == BindString.class)
            {
                InjectFields.BindString(activity,
                                        f,
                                        obj,
                                        (BindString) annotation[0],
                                        stringBuilder);
            }
            else { continue; }
            count++;
        }
        if (stringBuilder != null)
        {
            if (isroot == false || count > 0)
            {
                stringBuilder.append(isroot ? " ## " : " <-- ")
                             .append("injectFields end for ")
                             .append(obj.getClass()
                                        .getSimpleName())
                             .append(" done in ")
                             .append(System.currentTimeMillis() - time)
                             .append(" ms.\n");
            }
            if (isroot)
            {
                if (count > 0) activity.devInfo(stringBuilder.toString());
                stringBuilder.setLength(0);
            }
        }
    }
    
    @SuppressWarnings ("unchecked") public <T> Field[] getAllFields(final Class<T> type)
    {
        Field[] fields = fieldMap.get(type);
        if (fields != null) { return fields; }
        final List<Class<T>> classHierarchy = new ArrayList<>();
        Class<T>             nextClass      = type;
        while (nextClass != Object.class)
        {
            classHierarchy.add(nextClass);
            nextClass = (Class<T>) nextClass.getSuperclass();
        }
        final ArrayList<Field> allFields = new ArrayList<Field>();
        for (int i = classHierarchy.size() - 1; i >= 0; i--)
        {
            Collections.addAll(allFields,
                               classHierarchy.get(i)
                                             .getDeclaredFields());
        }
        final Map<String, Field> orderedFields = new TreeMap<>();
        for (final Field f : allFields)
        {
            if (Modifier.isStatic(f.getModifiers()) ||
                Modifier.isTransient(f.getModifiers()) ||
                f.isSynthetic())
            {
                  continue;
            }
            try
            {
                f.setAccessible(true);
            }
            catch (final Exception e)
            {
                activity.devInfo("%s is not accessible, %s",f.getName(),e.getMessage());
                continue;
            }
            orderedFields.put(f.getName(), f);
        }
        fields = orderedFields.values()
                              .toArray(new Field[] {});
        fieldMap.put(type, fields);
       for(Field f : fields)
        {
            activity.devInfo("%s -> %s",type.getSimpleName(),f.getName());
        }
        return fields;
    }
    
    public void injectViews(final Object obj, final IFindView rootView)
    {
        if (rootView == null)
        {
            activity.throwException(new Exception("rootView is null!"),
                                    "Without rootView inject view is not work");
            return; //no need for the rest
        }
        StringBuilder stringBuilder = null;
        long          time          = System.currentTimeMillis();
        int           count         = 0;
        if (activity.isDebugMode())
        {
            stringBuilder = new StringBuilder("\n## injectViews begin for ").append(obj.getClass()
                                                                                       .getSimpleName())
                                                                            .append("\n");
        }
        final Field[] fields = getAllFields(obj.getClass());
        for (int i = 0; i < fields.length; i++)
        {
            Field    f        = fields[i];
            BindView bindView = f.getAnnotation(BindView.class);
            if (bindView == null) continue;
            InjectView.BindView(activity,
                                f,
                                obj,
                                rootView.findViewByID(bindView.value()),
                                stringBuilder);
            count++;
        }
        Method[] methods = getAllMethods(obj.getClass());
        for (int i = 0; i < methods.length; i++)
        {
            final Method method      = methods[i];
            Annotation[] annotations = method.getDeclaredAnnotations();
            if (annotations.length == 0) continue;
            Annotation annotation = annotations[0];
            Class      clz        = annotation.annotationType();
            if (clz == OnClick.class)
            {
                InjectView.OnClick(activity,
                                   method,
                                   obj,
                                   rootView.findViewByID(((OnClick) annotation).value()),
                                   stringBuilder);
            }
            else if (clz == OnLongClick.class)
            {
                InjectView.OnLongClick(activity,
                                       method,
                                       obj,
                                       rootView.findViewByID(((OnLongClick) annotation).value()),
                                       stringBuilder);
            }
            else if (clz == OnCheck.class)
            {
                InjectView.OnCheck(activity,
                                   method,
                                   obj,
                                   rootView.findViewByID(((OnCheck) annotation).value()),
                                   stringBuilder);
            }
            else if (clz == OnTextChanged.class)
            {
                InjectView.OnTextChanged(activity,
                                         method,
                                         obj,
                                         rootView.findViewByID(((OnTextChanged) annotation).value()),
                                         stringBuilder);
            }
            else if (clz == OnFocus.class)
            {
                InjectView.OnFocus(activity,
                                   method,
                                   obj,
                                   rootView.findViewByID(((OnFocus) annotation).value()),
                                   stringBuilder);
            }
            count++;
        }
        if (stringBuilder != null && count > 0)
        {
            stringBuilder.append("-- injectView for class ")
                         .append(obj.getClass()
                                    .getSimpleName())
                         .append(" done in ")
                         .append(System.currentTimeMillis() - time)
                         .append(" ms.");
            activity.devInfo(stringBuilder.toString());
        }
    }
    
    @SuppressWarnings ("unchecked") private <T> Method[] getAllMethods(final Class<T> type)
    {
        Method[] methods = methodMap.get(type);
        if (methods != null) { return methods; }
        final List<Class<T>> classHierarchy = new ArrayList<>();
        Class<T>             nextClass      = type;
        while (nextClass != Object.class)
        {
            classHierarchy.add(nextClass);
            nextClass = (Class<T>) nextClass.getSuperclass();
        }
        final ArrayList<Method> allMethods = new ArrayList<Method>();
        for (int i = classHierarchy.size() - 1; i >= 0; i--)
        {
            Collections.addAll(allMethods,
                               classHierarchy.get(i)
                                             .getDeclaredMethods());
        }
        final Map<String, Method> orderedFields = new TreeMap<>();
        for (final Method f : allMethods)
        {
            if (f.getDeclaredAnnotations().length == 0) continue;
            if (Modifier.isStatic(f.getModifiers()) ||
                Modifier.isTransient(f.getModifiers()) ||
                f.isSynthetic())
            {
                continue;
            }
            try
            {
                f.setAccessible(true);
            }
            catch (final Exception e)
            {
                continue;
            }
            orderedFields.put(f.getName(), f);
        }
        methods = orderedFields.values()
                               .toArray(new Method[] {});
        methodMap.put(type, methods);
        return methods;
    }
}
