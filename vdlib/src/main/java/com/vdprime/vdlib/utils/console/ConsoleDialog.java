package com.vdprime.vdlib.utils.console;
import android.app.Dialog;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ListView;

import com.vdprime.vdlib.R;
import com.vdprime.vdlib.dialog.BaseDialog;

public class ConsoleDialog extends BaseDialog
{
    ListView consoleList;
    ConsoleAdapter consoleAdapter;
    
    public ConsoleDialog()
    {
        setSizePercentage(.84f, .7f);
    }
    
    @Override protected void CreateDialog()
    {
        consoleAdapter = vd.getConsoleAdapter();
        consoleList = (ListView) rootView.findViewById(R.id.vdlib_console_list);
        rootView.findViewById(R.id.vdlib_console_clear)
                .setOnClickListener(new OnClickListener()
                {
                    @Override public void onClick(final View v)
                    {
                        consoleAdapter.clear();
                    }
                });
        consoleList.setAdapter(consoleAdapter);
        consoleList.postDelayed(new Runnable()
        {
            @Override public void run()
            {
                consoleList.setSelection(consoleAdapter.getCount() - 1);
            }
        }, 200);
    }
    
    @Override protected Dialog getDialogSource()
    {
        return null;
    }
    
    @Override protected int getDialogViewSource()
    {
        return R.layout.vdlib_console_layout;
    }
}
