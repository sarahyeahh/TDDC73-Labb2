package com.example.sarah.labb2;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.StringTokenizer;


public class MainActivity extends Activity {

    private ExpandableListView elw;
    private Adapter listAdapter;
    private List<String> listDataTitle, parentA, parentB, parentC, parentD;
    private HashMap<String, List<String>> listHash;
    private EditText edit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        edit = (EditText) findViewById(R.id.editText);
        edit.getText().insert(0,"/");

        elw = (ExpandableListView) findViewById(R.id.expandableListView);
        addData();
        listAdapter = new Adapter(this, listDataTitle, listHash);
        elw.setAdapter(listAdapter);

        elw.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                Log.d("onGroupClick:", "worked");
                edit.setText(listDataTitle.get(groupPosition));
                //fråga***
                edit.getText().insert(0,"/");
                listAdapter.notifyDataSetChanged();
                return true;
            }
        });

        elw.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                Log.d("onChildClick:", "worked");
                edit.setText(listDataTitle.get(groupPosition)+ '/' +  parent.getExpandableListAdapter().getChild(groupPosition, childPosition));
                //fråga***
                edit.getText().insert(0,"/");


                listAdapter.notifyDataSetChanged();

                System.out.println("Group "+ groupPosition + " Child "+ childPosition);

                return true;
            }
        });

        //When group collapse
        elw.setOnGroupCollapseListener(new ExpandableListView.OnGroupCollapseListener() {
            @Override
            public void onGroupCollapse(int i) {

            }
        });

        //When group expands
        elw.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
            int previousItem = -1;

            @Override
            public void onGroupExpand(int i) {

                if(i != previousItem){

                    elw.collapseGroup(previousItem);
                    previousItem = i;

                   // System.out.println("Inte like med previous " + i);

                    listAdapter.notifyDataSetChanged();

                }
                else if(i == previousItem){
                   // elw.collapseGroup(i);
                    //elw.clearChoices();
                    //listAdapter.notifyDataSetChanged();
                    //listAdapter.notifyDataSetInvalidated();

                    //System.out.println("Stäng " + i);
                }
            }
        });

        edit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

                String newText = edit.getText().toString();
                StringTokenizer st = new StringTokenizer(newText,"/");
                edit.setBackgroundColor(Color.RED);

                boolean found = false;

                if(st.countTokens() == 0 || newText == "/" || newText == ""){

                    for(int i = 0; i<listDataTitle.size(); i++){
                        elw.collapseGroup(i);
                        elw.setItemChecked(i, false);
                    }

                    edit.setBackgroundColor(Color.WHITE);
                    listAdapter.notifyDataSetChanged();
                    found = true;

                }
                else if(st.countTokens() == 1){

                    System.out.println("newText " + newText + " st " + st + "count: " + st.countTokens());

                    String inputword =  st.nextToken().toLowerCase();

                    for(int i = 0; i < 4; i++){

                        if(listDataTitle.get(i).startsWith(inputword)){
                            edit.setBackgroundColor(Color.WHITE);
                            elw.setItemChecked(i, true);
                            elw.expandGroup(i);
                            listAdapter.notifyDataSetChanged();
                            found = true;
                            break;

                        }
                        else{
                            found = false;
                            continue;
                        }
                    }

                }
                else if(st.countTokens()==2){

                    String inputword =  st.nextToken().toLowerCase();

                    for(int i = 0; i < 4; i++){


                        if(inputword.equals(listDataTitle.get(i))){
                            edit.setBackgroundColor(Color.WHITE);
                            elw.setItemChecked(i, true);
                            elw.expandGroup(i);

                            String nextInputword =  st.nextToken().toLowerCase();
                            int childrenCount = listAdapter.getChildrenCount(i);
                            List<String> childrenList = listHash.get(listDataTitle.get(i));

                            for(int j = 0; j < childrenCount; j++ ){

                                if(childrenList.get(j).startsWith(nextInputword)) {
                                    edit.setBackgroundColor(Color.WHITE);
                                    int check = j + i + 1;
                                    elw.setItemChecked(check, true);
                                    listAdapter.notifyDataSetChanged();
                                    found = true;
                                    break;
                                }
                                else{
                                    found = false;
                                    continue;
                                }
                            }

                        }

                    }

                }

                if(!found){
                    edit.setBackgroundColor(Color.RED);
                }

                listAdapter.notifyDataSetChanged();
            }
        });
    }

    private void addData(){
        listDataTitle = new ArrayList<>();
        listHash = new HashMap<>();

        //Add parents
        listDataTitle.add("abc");
        listDataTitle.add("def");
        listDataTitle.add("ghi");
        listDataTitle.add("jkl");

        parentA = new ArrayList<>();
        parentA.add("a");

        parentB = new ArrayList<>();
        parentB.add("b");
        parentB.add("c");
        parentB.add("d");

        parentC = new ArrayList<>();
        parentC.add("c");
        parentC.add("d");

        parentD = new ArrayList<>();
        parentD.add("e");
        parentD.add("f");

        listHash.put(listDataTitle.get(0), parentA);
        listHash.put(listDataTitle.get(1), parentB);
        listHash.put(listDataTitle.get(2), parentC);
        listHash.put(listDataTitle.get(3), parentD);
     }
}
