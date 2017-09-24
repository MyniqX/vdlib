package com.vdprime.vdlib.utils;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Burak on 18.07.2017.
 */
public class vdstring
{
    public final static boolean isNullorEmpty(String str)
    {
        return str == null || str.isEmpty();
    }
    
    public final static String[] SplitNoEmpty(String str,String regex)
    {
        if(isNullorEmpty(str)) return new String[0];
        if(isNullorEmpty(regex)) return new String[] {str};
        String [] parts = str.split(regex);
        List<String> list = new ArrayList<>(parts.length);
        for(String line : parts)
        {
            String l = line.trim();
            if(l.isEmpty()) continue;
            list.add(l);
        }
        return list.toArray(new String[list.size()]);
    }
    
    public final static String Join(String seperator, Iterable<String> list)
    {
       StringBuilder sb = new StringBuilder(1024);
        for(String str : list)
        {
            sb.append(str).append(seperator);
        }
        if(sb.length() > 0)
            return sb.substring(0,sb.length()-seperator.length());
            //sb.replace(sb.length()-seperator.length(),sb.length(),"");
        return sb.toString();
    }
    
    public static String ConvertToTag(String str)
    {
        StringBuilder sb = new StringBuilder(str.length());
        for (int i = 0; i < str.length(); i++)
        {
            char c = Character.toUpperCase(str.charAt(i));
            if (Character.isLetterOrDigit(c))
            {
                switch (c)
                {
                    case 'İ':
                        c = 'I';
                        break;
                    case 'Ş':
                        c = 'S';
                        break;
                    case 'Ö':
                        c = 'O';
                        break;
                    case 'Ü':
                        c = 'U';
                        break;
                    case 'Ç':
                        c = 'C';
                        break;
                    case 'Ğ':
                        c = 'G';
                        break;
                }
                sb.append(c);
            }
        }
        return sb.toString();
    }
    
    public static String printDate(Date date, String format)
    {
        if(date == null) return "belli değil";
        return new SimpleDateFormat(format).format(date);
    }
    
    public static String removeMultipleWhiteSpace(String str)
    {
        return str.replaceAll("\\s\\s+/g", " ");
    }
}
