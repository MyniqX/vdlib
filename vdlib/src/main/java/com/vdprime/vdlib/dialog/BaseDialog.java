package com.vdprime.vdlib.dialog;
/**
 * Created by Burak on 17.07.2017.
 */
import android.annotation.TargetApi;
import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Point;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import com.vdprime.vdlib.activities.vdActivity;

public abstract class BaseDialog extends DialogFragment
{
    public    vdActivity          vd;
    protected View                rootView;
    protected IBaseDialogListener baseDialogListener;
    float wp = 1f, wh = 1f;
    
    public BaseDialog setDialogListener(final IBaseDialogListener baseDialogListener)
    {
        this.baseDialogListener = baseDialogListener;
        return this;
    }
    
    @Override public View onCreateView(
            final LayoutInflater inflater,
            @Nullable final ViewGroup container,
            @Nullable final Bundle savedInstanceState
    )
    {
        vd = (vdActivity) getContext();
        final Dialog d = getDialog();
        d.setCanceledOnTouchOutside(shouldCancelOnTouchOutside());
        if (makeTransparent())
        {
            d.requestWindowFeature(Window.FEATURE_NO_TITLE);
            d.getWindow()
             .setBackgroundDrawableResource(android.R.color.transparent);
        }
        final int source = getDialogViewSource();
        if (source > 0)
        {
            vd.injectFields(this);
            rootView = vd.getLayoutInflater()
                         .inflate(source, container, false);
            vd.injectViews(this, rootView);
            CreateDialog();
            return rootView;
        }
        return super.onCreateView(inflater, container, savedInstanceState);
    }
    
    protected boolean shouldCancelOnTouchOutside()
    {
        return true;
    }
    
    protected boolean makeTransparent()
    {
        return true;
    }
    
    /**
     * create dialogfragment from custom view
     *
     * @return
     */
    protected abstract int getDialogViewSource();
    protected abstract void CreateDialog();
    
    @Override public Dialog onCreateDialog(final Bundle savedInstanceState)
    {
        final Dialog d = getDialogSource();
        if (d == null) { return super.onCreateDialog(savedInstanceState); }
        return d;
    }
    
    @Override public void onDismiss(final DialogInterface dialog)
    {
        super.onDismiss(dialog);
        if (baseDialogListener != null)
        {
            baseDialogListener.onDismiss();
        }
    }
    
    @TargetApi (Build.VERSION_CODES.HONEYCOMB_MR2) @SuppressWarnings ("deprecation") @Override
    public void onStart()
    {
        super.onStart();
        if (wp == 1f && wh == 1f) { return; }
        final Display display = getActivity().getWindowManager()
                                             .getDefaultDisplay();
        int width, height;
        if (Build.VERSION.SDK_INT > 13)
        {
            final Point size = new Point();
            display.getSize(size);
            width = size.x;
            height = size.y;
        }
        else
        {
            width = display.getWidth();
            height = display.getHeight();
        }
        final int w = (int) (width * wp);
        final int h = (int) (height * wh);
        SetDialogSize(w, h);
    }
    
    public void SetDialogSize(int width, int height)
    {
        final Window window = getDialog().getWindow();
        window.setLayout(width, height);
    }
    
    /**
     * create dialogfragment from dialog
     *
     * @return
     */
    protected abstract Dialog getDialogSource();
    
    public void setSizePercentage(final float wp, final float wh)
    {
        this.wp = wp;
        this.wh = wh;
    }
    
    public interface IBaseDialogListener
    {
        void onDismiss();
    }
}
