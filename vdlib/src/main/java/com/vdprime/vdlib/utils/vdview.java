package com.vdprime.vdlib.utils;
import android.os.Build;
import android.os.Build.VERSION;
import android.text.Html;
import android.widget.TextView;

/**
 * Created by Burak on 25.07.2017.
 */
public class vdview
{
    public static void setHTMLContent(String text, TextView tv)
    {
        if (VERSION.SDK_INT >= Build.VERSION_CODES.N)
        { tv.setText(Html.fromHtml(text, Html.FROM_HTML_MODE_COMPACT)); }
        else { tv.setText(Html.fromHtml(text)); }
    }
}
