package com.vdprime.vdlib.utils.console;
import android.content.Context;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.vdprime.vdlib.R;
import com.vdprime.vdlib.enums.Colors;

/**
 * Created by Burak on 17.07.2017.
 */
public class ConsoleItemView extends LinearLayout
{
    TextView date;
    TextView context;
    TextView type;
    
    public ConsoleItemView(Context context)
    {
        super(context);
        init();
    }
    
    public void init()
    {
        View view = inflate(getContext(), R.layout.vdlib_console_item, this);
        date = (TextView) view.findViewById(R.id.vdlib_console_item_date);
        context = (TextView) view.findViewById(R.id.vdlib_console_item_context);
        type = (TextView) view.findViewById(R.id.vdlib_console_item_type);
        context.setOnClickListener(new OnClickListener()
        {
            @Override public void onClick(final View v)
            {
                expand();
            }
        });
    }
    
    void expand()
    {
        context.setMaxLines(1000);
    }
    
    void collapse()
    {
        context.setMaxLines(3);
    }
    
    public ConsoleItemView set(ConsoleItem ci)
    {
        date.setText(ci.date.toString());
        context.setText(ci.Content);
        String typetxt = "";
        int    color   = 0;
        switch (ci.Level)
        {
            case ConsoleItem.DEV:
                color = Colors.yellowgreen.getColor();
                typetxt = "Dev:";
                break;
            default:
            case ConsoleItem.INFO:
                color = Colors.green.getColor();
                typetxt = "Dev:";
                break;
            case ConsoleItem.WARN:
                color = Colors.orange.getColor();
                typetxt = "Dev:";
                break;
            case ConsoleItem.ERR:
                color = Colors.red.getColor();
                typetxt = "Dev:";
                break;
        }
        type.setText(typetxt);
        type.setBackgroundColor(color);
        context.setTextColor(color);
        collapse();
        return this;
    }
}
