package com.vdprime.vdlib.utils.console;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Burak on 17.07.2017.
 */
public class ConsoleAdapter extends BaseAdapter
{
    private List<ConsoleItem> itemList = new ArrayList<>();
    @Override public int getCount()
    {
        return itemList.size();
    }
    
    Context mContext;
    public ConsoleAdapter(Context context)
    {
        mContext = context;
    }
    
    public void add(String text,int level)
    {
        ConsoleItem item = new ConsoleItem();
        item.Content = text;
        item.Level = level;
        item.date = new Date();
        itemList.add(item);
        notifyDataSetChanged();
    }
    public void clear()
    {
        itemList.clear();
        notifyDataSetChanged();
    }
    
    @Override public Object getItem(int i)
    {
        return itemList.get(i);
    }
    
    @Override public long getItemId(int i)
    {
        return i;
    }
    
    @Override public View getView(int i, View view, ViewGroup viewGroup)
    {
        ConsoleItemView civ = (ConsoleItemView) view;
        if(civ == null)
            civ = new ConsoleItemView(mContext);
        return civ.set(itemList.get(i));
    }
}
