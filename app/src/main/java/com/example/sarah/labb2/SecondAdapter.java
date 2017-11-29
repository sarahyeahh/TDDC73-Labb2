package com.example.sarah.labb2;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.TextView;

import java.util.HashMap;
import java.util.List;

/**
 * Created by Sarah on 2017-11-27.
 */

public class SecondAdapter extends BaseExpandableListAdapter {


    private Context context;
    private List<String> listDataTitle;
    private HashMap<String, List<String>> listHash;

    public SecondAdapter(Context context, List<String> listDataTitle, HashMap<String, List<String>> listHash){

        this.context = context;
        this.listDataTitle = listDataTitle;
        this.listHash = listHash;

    }
    @Override
    public int getGroupCount() {
        return listDataTitle.size();
    }

    @Override
    public int getChildrenCount(int i) {
        return listHash.get(listDataTitle.get(i)).size();
    }

    @Override
    public Object getGroup(int i) {
        return null;
    }

    @Override
    public Object getChild(int i, int i1) {
        return null;
    }

    @Override
    public long getGroupId(int i) {
        return 0;
    }

    @Override
    public long getChildId(int i, int i1) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int i, boolean b, View view, ViewGroup viewGroup) {
        String hTitle = (String)getGroup(i);

        TextView title = (TextView)view.findViewById(R.id.child);
        title.setTypeface(null, Typeface.BOLD);
        title.setText(hTitle);

        if(view==null)
        {
            LayoutInflater inflater = (LayoutInflater)this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.list_child, null);
        }
        else{
            ExpandableListView elv = new ExpandableListView(context);
            elv.setAdapter(new Adapter(context, listDataTitle, listHash));

        }

        return view;
    }

    @Override
    public View getChildView(int i, int i1, boolean b, View view, ViewGroup viewGroup) {
        String cText = (String)getChild(i, i1);

        if(view==null)
        {
            LayoutInflater inflater = (LayoutInflater)this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.list_grandchild, null);
        }

        TextView grandchild = (TextView)view.findViewById(R.id.grandchild);
        grandchild.setTypeface(null, Typeface.BOLD);
        grandchild.setText(cText);

        return view;
    }

    @Override
    public boolean isChildSelectable(int i, int i1) {
        return true;
    }
}
