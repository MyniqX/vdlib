package com.vdprime.vdlib.activities;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.Toast;

import com.vdprime.vdlib.R;
import com.vdprime.vdlib.dialog.BaseDialog;
import com.vdprime.vdlib.fragments.BaseFragment;
import com.vdprime.vdlib.utils.console.ConsoleAdapter;
import com.vdprime.vdlib.utils.console.ConsoleDialog;
import com.vdprime.vdlib.utils.console.ConsoleItem;
import com.vdprime.vdlib.utils.database.vdCoreDatabase;
import com.vdprime.vdlib.utils.database.vdIDatabase;
import com.vdprime.vdlib.utils.inject.InjectView;
import com.vdprime.vdlib.utils.inject.vdInstance;
import com.vdprime.vdlib.utils.json.Json;
import com.vdprime.vdlib.utils.vdReflection;
import com.vdprime.vdlib.utils.vdio;
import com.vdprime.vdlib.utils.vdstring;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Burak on 29.07.2017.
 */
public abstract class vdActivity extends AppCompatActivity
{
    public boolean printStackTrace = true;
    protected Toolbar        toolBar;
    protected ImageView      sharedImageView;
    protected Toast          currentToast;
    protected vdCoreDatabase coreDatabase;
    protected DrawerLayout                        drawerLayout            = null;
    protected HashMap<Integer, IPermissionResult> permissionResultHashMap = new HashMap<>();
    private vdReflection reflection;
    protected BaseFragment startFragment;
    protected List<Object> injectItems = new ArrayList<>();
    private   long         lastClick   = 0;
    private ConsoleAdapter    consoleAdapter;
    private SharedPreferences sharedPreferences;
    private Json json = null;
    
    public List<Object> getInjectItems() { return injectItems;}
    
    public <T extends vdIDatabase> T loadDatabase(Class<T> clzz)
    {
        return coreDatabase.loadDatabase(clzz);
    }
    
    public boolean hasPermission(String permission)
    {
        return ActivityCompat.checkSelfPermission(this, permission) ==
               PackageManager.PERMISSION_GRANTED;
    }
    
    public void injectViews(Object obj, Object rootView)
    {
        reflection.injectViews(obj, InjectView.getInstance(rootView));
    }
    
    public <T extends vdInstance> T createInstance(Class<T> clzz)
    {
        try
        {
            T t = clzz.newInstance();
            injectFields(t);
            return t;
        }
        catch (Exception e)
        {
            printStack("createInstance failed.", e);
        }
        return null;
    }
    
    public void injectFields(Object obj)
    {
        reflection.injectFields(obj);
    }
    
    public final void printStack(final String str, final Exception e)
    {
        StringBuilder message = new StringBuilder();
        if (str != null)
        {
            message.append(str)
                   .append("\n");
        }
        message.append("Message: ")
               .append(e.getMessage())
               .append("\n");
        if (printStackTrace) message.append(Log.getStackTraceString(e));
        devErr(message.toString());
    }
    
    public final void devErr(final String str, Object... params)
    {
        Log(str, ConsoleItem.ERR, params);
    }
    
    private final void Log(String str, int log, Object... params)
    {
        if (params != null && params.length > 0) str = String.format(str, params);
        Log.d(getClass().getSimpleName(), str);
        appendLogBuffer(str, log);
    }
    
    private final void appendLogBuffer(final String str, int level)
    {
        if (isDebugMode() == false) { return; }
        consoleAdapter.add(str, level);
    }
    
    public abstract boolean isDebugMode();
    
    public void requestPermission(int Code, String permission, IPermissionResult result)
    {
        if (permissionResultHashMap.containsKey(Code))
        {
            toast("Code (%d) has already ask for permission : %s", Code, permission);
            return;
        }
        permissionResultHashMap.put(Code, result);
        ActivityCompat.requestPermissions(this, new String[] {permission}, Code);
    }
    
    public void hideToolbar(boolean hide)
    {
        if (getSupportActionBar() == null) return;
        if (hide)
        {
            // toolBar.animate().translationY(-toolBar.getBottom()).setInterpolator(new AccelerateInterpolator()).start();
            getSupportActionBar().hide();
        }
        else
        {
            // toolBar.animate().translationY(0).setInterpolator(new DecelerateInterpolator()).start();
            getSupportActionBar().show();
        }
    }
    
    public void openDrawer()
    {
        if (drawerLayout != null)
        {
            drawerLayout.openDrawer(Gravity.START);
        }
    }
    
    public boolean isDrawerOpen()
    {
        return drawerLayout != null && drawerLayout.isDrawerOpen(Gravity.START);
    }
    
    public void closeDrawer()
    {
        if (drawerLayout != null)
        {
            drawerLayout.closeDrawer(Gravity.START);
        }
    }
    
    public ConsoleAdapter getConsoleAdapter() {return consoleAdapter;}
    
    public void flushDatabase(vdIDatabase db)
    {
        coreDatabase.flush(db);
    }
    
    protected BaseFragment drawerFragment = null;
    @Override protected void onCreate(final Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
        reflection = new vdReflection(this);
        json = jsonInstance();
        consoleAdapter = new ConsoleAdapter(this);
        coreDatabase = new vdCoreDatabase(this);
        reflection.injectFields(this);
        drawerFragment = getDrawerFragment();
        if (drawerFragment != null)
        {
            setContentView(R.layout.vdlib_activity_drawer_layout);
            drawerLayout = (DrawerLayout) findViewById(R.id.vdlib_activity_drawer);
            getSupportFragmentManager().beginTransaction()
                                       .replace(R.id.vdlib_activity_drawer_container,
                                                drawerFragment)
                                       .commit();
            toolBar = (Toolbar) findViewById(R.id.vdlib_activity_toolbar);
            setSupportActionBar(toolBar);
            ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,
                                                                     drawerLayout,
                                                                     toolBar,
                                                                     R.string.navigation_drawer_open,
                                                                     R.string.navigation_drawer_close);
            drawerLayout.addDrawerListener(toggle);
            toggle.syncState();
        }
        else
        {
            setContentView(R.layout.vdlib_activty_layout);
            toolBar = (Toolbar) findViewById(R.id.vdlib_activity_toolbar);
            setSupportActionBar(toolBar);
        }
        sharedImageView = (ImageView) findViewById(R.id.vdlib_activity_shared);
        startFragment = getStartFragment();
        setFragment(startFragment);
        createActivity();
        if (isDebugMode() == false)
        {
            findViewById(R.id.vdlib_console_panel).setVisibility(View.GONE);
        }
        else
        {
            findViewById(R.id.vdlib_console_panel).setOnClickListener(new OnClickListener()
            {
                @Override public void onClick(final View v)
                {
                    showDialog(new ConsoleDialog());
                }
            });
        }
    }
    
    protected Json jsonInstance()
    {
        return new Json();
    }

    protected BaseFragment getDrawerFragment()
    {
        return null;
    }
    
    protected abstract BaseFragment getStartFragment();
    
    public final void setFragment(final BaseFragment fragment)
    {
        setFragment(fragment, true);
    }
    
    protected abstract void createActivity();
    
    public void showDialog(BaseDialog baseDialog)
    {
        baseDialog.show(getSupportFragmentManager(), "");
    }
    
    public final void setFragment(final BaseFragment fragment, final boolean back)
    {
        final FragmentTransaction ft = getSupportFragmentManager().beginTransaction()
                                                                  .setCustomAnimations(android.R.anim.slide_in_left,
                                                                                       android.R.anim.fade_out,
                                                                                       android.R.anim.fade_in,
                                                                                       android.R.anim.slide_out_right);
        if (back == true)
        {
            ft.addToBackStack(null);
        }
        ft.replace(R.id.vdlib_activity_container, fragment)
          .commit();
    }
    
    public Toolbar getToolBar()
    {
        return toolBar;
    }
    
    public final BaseFragment getCurrentFragment()
    {
        return (BaseFragment) getSupportFragmentManager().findFragmentById(R.id.vdlib_activity_container);
    }
    
    public final <T extends BaseFragment> void setFragment(final Class<T> clz)
    {
        try
        {
            setFragment(clz.newInstance());
        }
        catch (final Exception e)
        {
            e.printStackTrace();
        }
    }
    
    public final <T extends BaseFragment> void setFragment(final Class<T> clz, final boolean back)
    {
        try
        {
            setFragment(clz.newInstance(), back);
        }
        catch (final Exception e)
        {
            e.printStackTrace();
        }
    }
    
    @Override public void onBackPressed()
    {
        if(isDrawerOpen())
        {
            if(drawerFragment.onBackPressed())
                return;
        }
        final BaseFragment fragment = getCurrentFragment();
        if (fragment == startFragment)
        {
            if(startFragment.onBackPressed() == true)
                return;
            if (System.currentTimeMillis() - lastClick > 1500)
            {
                toast("Çıkmak için tekrar geri tuşuna basınız.");
                lastClick = System.currentTimeMillis();
                return;
            }
            else { finish(); }
        }
        if (fragment == null || fragment.onBackPressed() == false)
        {
            super.onBackPressed();
        }
    }
    
    @Override protected void onPause()
    {
        super.onPause();
        coreDatabase.flushAll();
    }
    
    @Override public void onRequestPermissionsResult(
            int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults
    )
    {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        IPermissionResult result = permissionResultHashMap.get(requestCode);
        if (result != null)
        {
            permissionResultHashMap.remove(requestCode);
            result.onResult(grantResults[0] == PackageManager.PERMISSION_GRANTED);
        }
    }
    
    public final void toast(@StringRes int res, final Object... params)
    {
        toast(getResources().getString(res), params);
    }
    
    public void toast(final String text, final Object... params)
    {
        if (Looper.getMainLooper()
                  .equals(Looper.myLooper()) == false)
        {
            runOnUiThread(new Runnable()
            {
                @Override public void run()
                {
                    toast(text, params);
                }
            });
            return;
        }
        if (currentToast != null)
        {
            currentToast.cancel();
        }
        currentToast = Toast.makeText(this, String.format(text, params), Toast.LENGTH_SHORT);
        currentToast.show();
        appendLogBuffer("TOAST: " + text, ConsoleItem.INFO);
    }
    
    public final SharedPreferences getSharedPreferences()
    {
        if (sharedPreferences == null)
        { sharedPreferences = getPreferences(Context.MODE_PRIVATE); }
        return sharedPreferences;
    }
    
    public final void Dump(final Object... obj)
    {
        if (isDebugMode() == false) { return; }
        StringBuilder sbdump = new StringBuilder();
        sbdump.append(obj[0]);
        for (int i = 1; i < obj.length; i++)
        {
            sbdump.append(" -%- ");
            sbdump.append(obj[i]);
        }
        Log(sbdump.toString(), ConsoleItem.DEV);
    }
    
    public final void devInfo(final String str, Object... params)
    {
        Log(str, ConsoleItem.INFO, params);
    }
    
    public final void devWarn(final String str, Object... params)
    {
        Log(str, ConsoleItem.WARN, params);
    }
    
    public final void devDev(final String str, Object... params)
    {
        Log(str, ConsoleItem.DEV, params);
    }
    
    public final void toJsonthenFile(final Object object, final File file)
    {
        String data = toJson(object);
        vdio.writeAllText(file, data);
    }
    
    public final String toJson(final Object object)
    {
        return toJson(object, false);
    }
    
    public final String toJson(final Object object, final boolean encode)
    {
        final String json = this.json.toJson(object);
        if (encode)
        {
            try
            {
                return "$" + Base64.encodeToString(json.getBytes("UTF-8"), Base64.DEFAULT);
            }
            catch (final UnsupportedEncodingException e)
            {
                throwException(e, null);
            }
        }
        return json;
    }
    
    public void throwException(final Exception exception, String extraMessage)
    throws RuntimeException
    {
        printStack(extraMessage, exception);
        if (isDebugMode()) throw new RuntimeException(exception);
    }
    
    public final <T> T fromJson(final String str, Class<T> clz)
    {
        //  return json.fromJson(getReadJsonData(str), clz);
        return json.fromJson(clz, getReadJsonData(str));
    }
    
    private final String getReadJsonData(final String str)
    {
        if (vdstring.isNullorEmpty(str)) { return str; }
        if (str.charAt(0) == '$')
        {
            final String data    = str.substring(1);
            final byte[] decoded = Base64.decode(data, Base64.DEFAULT);
            return new String(decoded);
        }
        return str;
    }
    
    public final <T> T fromJson(final File file, Class<T> clz)
    {
        //  return json.fromJson(vdio.readAllText(file), clz);
        return json.fromJson(clz, file);
    }
    
    public interface IPermissionResult
    {
        public void onResult(boolean granted);
    }
}
