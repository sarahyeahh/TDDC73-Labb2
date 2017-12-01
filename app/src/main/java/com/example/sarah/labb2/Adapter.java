package com.example.sarah.labb2;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;
import java.util.HashMap;
import java.util.List;


//  notifyDataSetChanged();

public class Adapter extends BaseExpandableListAdapter {
    private Context context;
    private List<String> listDataTitle;
    private HashMap<String, List<String>> listHash;

    public Adapter(Context context, List<String> listDataTitle, HashMap<String, List<String>> listHash){
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
        return listDataTitle.get(i);
    }

    @Override
    public Object getChild(int i, int i1) {
        return listHash.get(listDataTitle.get(i)).get(i1);
    }

    @Override
    public long getGroupId(int i) {
        return i;
    }

    @Override
    public long getChildId(int i, int i1) {
        return i1;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int i, boolean b, View view, ViewGroup viewGroup) {
        String hTitle = (String)getGroup(i);

        if(view==null)
        {
            LayoutInflater inflater = (LayoutInflater)this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.list_parent, null);
        }

        TextView title = (TextView)view.findViewById(R.id.title);
        title.setTypeface(null, Typeface.BOLD);
        title.setText(hTitle);

        return view;
    }

    @Override
    public View getChildView(int i, int i1, boolean b, View view, ViewGroup viewGroup) {
        String cText = (String)getChild(i, i1);

        if(view==null)
        {
            LayoutInflater inflater = (LayoutInflater)this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.list_child, null);
        }

        TextView child = (TextView)view.findViewById(R.id.child);
        child.setTypeface(null, Typeface.BOLD);
        child.setText(cText);

        return view;
    }

    @Override
    public boolean isChildSelectable(int i, int i1) {
        return true;
    }

}
