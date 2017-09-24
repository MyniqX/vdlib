package com.vdprime.vdlib.dialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnKeyListener;
import android.content.res.Resources.Theme;
import android.util.TypedValue;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.TextView;

import com.vdprime.vdlib.R;

import static android.view.View.GONE;

/**
 * Created by Burak on 25.07.2017.
 */
public class NotificationDialog extends BaseDialog implements OnKeyListener
{
    protected FrameLayout notificationContainer;
    protected TextView    defaultTextView;
    protected TextView    subTitle;
    protected TextView    Title;
    protected ImageButton fabButton;
    protected View        insideView;
    int secondLeft = 0;
    
    @Override protected boolean shouldCancelOnTouchOutside()
    {
        return false;
    }
    
    void countDown()
    {
        if (secondLeft > 0)
        {
            subTitle.setText(String.format("%s saniye içinde kapatabilirsiniz.", secondLeft));
            secondLeft--;
            fabButton.postDelayed(new Runnable()
            {
                @Override public void run()
                {
                    countDown();
                }
            }, 1000);
        }
        else
        {
            fabButton.setVisibility(View.VISIBLE);
            subTitle.setText("");
        }
    }
    
    public NotificationDialog setTimeoutBeforeDissmiss(int second)
    {
        secondLeft = second;
        fabButton.setVisibility(GONE);
        fabButton.post(new Runnable()
        {
            @Override public void run()
            {
                countDown();
            }
        });
        return this;
    }
    
    protected int InsideView()
    {
        return 0;
    }
    
    protected void onCreateInsideView(View insideView) {}
    
    @Override protected void CreateDialog()
    {
        setSizePercentage(0.8f, 0.8f);
        notificationContainer = (FrameLayout) rootView.findViewById(R.id.vdlib_notification_container);
        defaultTextView = (TextView) rootView.findViewById(R.id.vdlib_notification_default);
        subTitle = (TextView) rootView.findViewById(R.id.vdlib_notification_subtitle);
        Title = (TextView) rootView.findViewById(R.id.vdlib_notification_title);
        fabButton = (ImageButton) rootView.findViewById(R.id.vdlib_notification_fab);
        fabButton.setOnClickListener(new OnClickListener()
        {
            @Override public void onClick(final View v)
            {
                dismiss();
            }
        });
        getDialog().setOnKeyListener(this);
        Theme      theme      = getContext().getTheme();
        TypedValue typedValue = new TypedValue();
        theme.resolveAttribute(R.attr.colorPrimary, typedValue, true);
        Title.setBackgroundColor(typedValue.data);
        subTitle.setBackgroundColor(typedValue.data);
        theme.resolveAttribute(R.attr.colorAccent, typedValue, true);
        fabButton.setBackgroundColor(typedValue.data);
        int id = InsideView();
        if (id == 0) return;
        insideView = View.inflate(getContext(), id, notificationContainer);
        defaultTextView.setVisibility(GONE);
        onCreateInsideView(insideView);
    }
    
    @Override public boolean onKey(
            final DialogInterface dialogInterface, final int i, final KeyEvent keyEvent
    )
    {
        if (i == KeyEvent.KEYCODE_BACK)
        {
            return fabButton.getVisibility() != View.VISIBLE;
        }
        return false;
    }
    
    @Override protected Dialog getDialogSource()
    {
        return null;
    }
    
    @Override protected int getDialogViewSource()
    {
        return R.layout.vdlib_notification_dialog_layout;
    }
}
