package com.vdprime.vdlib.utils;
/**
 * Created by Burak on 20.08.2017.
 */
public class vdparse
{
    static public float toFloat(final String str, final float def)
    {
        try
        {
            return Float.parseFloat(str);
        }
        catch (final Exception e)
        {
        }
        return def;
    }
    
    static public int toInt(final String str, final int def)
    {
        try
        {
            return Integer.parseInt(str);
        }
        catch (final Exception e)
        {
        }
        return def;
    }
    
    static public float toFloat(final String[] strs, final int index, final float def)
    {
        try
        {
            return Float.parseFloat(strs[index]);
        }
        catch (final Exception e)
        {
        }
        return def;
    }
    
	/*
		static public void toQuart (final String str, final Quaternion q)
		{
			final String[] args = str.replace ("[", "").replace ("]", "").split ("|");
			final float x = toFloat (args, 0, 0);
			final float y = toFloat (args, 1, 0);
			final float z = toFloat (args, 2, 0);
			final float w = toFloat (args, 3, 0);
			q.set (x, y, z, w);
		}
	
	public static void toVec3 (final String str, final Vector3 def, final Vector3 out)
	{
		final String[] args = str.replace ("(", "").replace (")", "").split (",");
		out.z = toFloat (args, 2, def.z);
		out.y = toFloat (args, 1, def.y);
		out.x = toFloat (args, 0, def.x);
	}*/
    
    public static Boolean toBool(final String value, final Boolean def)
    {
        try
        {
            return Boolean.parseBoolean(value);
        }
        catch (final Exception e)
        {
        }
        return def;
    }
    
    @SuppressWarnings ("unchecked")
    public static <T extends Object> T parse(final String str, final T def)
    {
        try
        {
            return (T) parse(str, def.getClass());
        }
        catch (final Exception e)
        {
            e.printStackTrace();
        }
        return def;
    }
    
    @SuppressWarnings ("unchecked")
    public static <T extends Object> T parse(final String str, final Class<T> clz) throws Exception
    {
        if (clz == Long.class || clz == long.class) { return (T) (Object) Long.parseLong(str); }
        if (clz == Integer.class || clz == int.class) { return (T) (Object) Integer.parseInt(str); }
        if (clz == CharSequence.class || clz == String.class) { return (T) str; }
        if (clz == Float.class || clz == float.class) { return (T) (Object) Float.parseFloat(str); }
        if (clz == Double.class || clz == double.class)
        {
            return (T) (Object) Double.parseDouble(str);
        }
        if (clz == Character.class || clz == char.class) { return (T) (Object) str.charAt(0); }
        if (clz == Byte.class || clz == byte.class) { return (T) (Object) Byte.parseByte(str); }
      //  if (clz == Range.class) { return (T) Range.parseRange(str); }
        if (clz.isAssignableFrom(Enum.class))
        {
            final Enum<?>[] enums = (Enum[]) clz.getEnumConstants();
            for (int i = 0, c = enums.length; i < c; i++)
            {
                final Enum<?> e = enums[i];
                if (e.name()
                     .equals(str)) { return (T) e; }
            }
        }
        return null;
    }
}
