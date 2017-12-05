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
               // int index = parent.getFlatListPosition(ExpandableListView.getPackedPositionForChild(groupPosition, childPosition));
                //parent.setItemChecked(index, true); //Markera
               // parent.setItemChecked(index, false); //Avmarkera
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

                    listAdapter.notifyDataSetChanged();
                }
            }
        });

        edit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int start, int before, int count) {

                String newText = edit.getText().toString();

                StringTokenizer st = new StringTokenizer(newText,"/");

                String Titles [] = {"parentA", "parentB", "parentC", "parentD"};
                String Childs [] ={"childA", "childB", "childB2", "childB3", "childC", "childC2"};
               // String ChildB [] ={"childB", "childB2", "childB3"};
               // String ChildC [] ={"childC", "childC2"};
                //String ChildD [] ={"childC", "childC2"};

                if(st.countTokens()==0 || newText == ""){
                 //Alla ska vara inflateade
                    System.out.print("Alla ska vara inflateade.");
                    //fråga***
                    //edit.getText().insert(0,"/");

                    for(int i = 0; i<Titles.length; i++){
                        elw.collapseGroup(i);
                        elw.setItemChecked(i, false);
                    }

                    //fråga***
                    edit.setBackgroundColor(Color.WHITE);
                }
                else if(st.countTokens()==1){
                    //Antingen skrivit eller tryckt

                    String inputword =  st.nextToken();

                    edit.setBackgroundColor(Color.WHITE);

                    for(int i = 0; i < Titles.length; i++){

                        if(inputword.equals(Titles[i])){
                            edit.setBackgroundColor(Color.WHITE);
                            elw.setItemChecked(i, true);
                            elw.expandGroup(i);
                            listAdapter.notifyDataSetChanged();
                        }
                        else{
                            //Collapse all and set bg-color to red.
                            //fråga***
                            edit.setBackgroundColor(Color.RED);
                        }

                    }
                    //fråga***
                    edit.setBackgroundColor(Color.WHITE);
                }
                else if(st.countTokens()==2){
                    //Antingen skrivit 2 eller tryckt 2

                    String inputword =  st.nextToken();

                    for(int i = 0; i < Titles.length; i++){

                        if(inputword.equals(Titles[i])){
                            edit.setBackgroundColor(Color.WHITE);
                            elw.setItemChecked(i, true);
                            elw.expandGroup(i);

                            String inputword2 = st.nextToken();
                            System.out.println("Inputword2 " + inputword2);

                            int childrenCount = listAdapter.getChildrenCount(i);
                            long groupId = listAdapter.getGroupId(i);
                            System.out.println("Children count " + childrenCount + " GroupId " + groupId);

                            List<String> strings = listHash.get(listDataTitle.get(i));

                            for(int j = 0; j < childrenCount; j++){

                                if(inputword2.equals(strings.get(j))){
                                    edit.setBackgroundColor(Color.WHITE);
                                    int check = j + i + 1;
                                    elw.setItemChecked(check, true);

                                    System.out.println( "String get " + strings.get(j));
                                    listAdapter.notifyDataSetChanged();
                                }
                                else{
                                    //fråga***
                                   edit.setBackgroundColor(Color.RED);
                                }
                            }
                            //fråga***
                            edit.setBackgroundColor(Color.WHITE);

                        }
                        else{
                            //Collapse all and set bg-color to red.
                            edit.setBackgroundColor(Color.WHITE);

                        }

                    }


                }

                listAdapter.notifyDataSetChanged();
            }

            @Override
            public void afterTextChanged(Editable editable) {

                listAdapter.notifyDataSetChanged();
            }
        });
    }

    private void addData(){
        listDataTitle = new ArrayList<>();
        listHash = new HashMap<>();

        //Add parents
        listDataTitle.add("parentA");
        listDataTitle.add("parentB");
        listDataTitle.add("parentC");
        listDataTitle.add("parentD");

        parentA = new ArrayList<>();
        parentA.add("childA");

        parentB = new ArrayList<>();
        parentB.add("childB");
        parentB.add("childB2");
        parentB.add("childB3");

        parentC = new ArrayList<>();
        parentC.add("childC");
        parentC.add("childC2");

        parentD = new ArrayList<>();
        parentD.add("childC");
        parentD.add("childC2");

        listHash.put(listDataTitle.get(0), parentA);
        listHash.put(listDataTitle.get(1), parentB);
        listHash.put(listDataTitle.get(2), parentC);
        listHash.put(listDataTitle.get(3), parentD);
     }
}
