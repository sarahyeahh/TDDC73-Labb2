//TDDC73 labb2
//sarfo265 och malwe794

package com.example.sarah.labb2;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ExpandableListView;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.StringTokenizer;


public class MainActivity extends Activity {

    //Variables
    private ExpandableListView elw;
    private Adapter listAdapter;
    private List<String> listDataTitle, parentA, parentB, parentC, parentD;
    private HashMap<String, List<String>> listHash;
    private EditText edit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Get the editText from activity_main.xml
        edit = (EditText) findViewById(R.id.editText);
        edit.getText().insert(0,"/");

        elw = (ExpandableListView) findViewById(R.id.expandableListView);

        //Add parents and children
        addData();

        //Creates an adapter with the parents and children
        listAdapter = new Adapter(this, listDataTitle, listHash);
        //Connect the adapter with the expandable list view.
        elw.setAdapter(listAdapter);

        //Click listener for the parents
        elw.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                //Print parent-node in editText
                edit.setText('/' + listDataTitle.get(groupPosition));
                listAdapter.notifyDataSetChanged();
                return true;
            }
        });

        //Click listener for the children
        elw.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                //Print parent and child-node in editText
                edit.setText('/' + listDataTitle.get(groupPosition)+ '/' +  parent.getExpandableListAdapter().getChild(groupPosition, childPosition));
                listAdapter.notifyDataSetChanged();
                return true;
            }
        });

        //Listener when group(parents) expands
        elw.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
            int previousItem = -1;

            @Override
            public void onGroupExpand(int i) {
                //Collapse previous group when opening a new one
                if(i != previousItem){
                    elw.collapseGroup(previousItem);
                    previousItem = i;
                    listAdapter.notifyDataSetChanged();
                }

            }
        });

        //Listener text change
        edit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

                //Read in text from editText and create tokens.
                //The text is separated into tokens at "/"
                String newText = edit.getText().toString().toLowerCase();
                StringTokenizer st = new StringTokenizer(newText,"/");

                //Initially sets background to red and sets found to false
                edit.setBackgroundColor(Color.RED);
                boolean found = false;

                int groupCount = listAdapter.getGroupCount();

                //If the editText is empty
                if(!newText.startsWith("/")){
                    edit.setText("/");
                }
                //If the editText contains no word
                if(st.countTokens() == 0 ){

                    //Collapse all groups and deselect all
                    for(int i = 0; i < groupCount; i++){
                        elw.collapseGroup(i);
                        elw.setItemChecked(i, false);
                    }

                    //Set background to white.
                    edit.setBackgroundColor(Color.WHITE);
                    found = true;
                    listAdapter.notifyDataSetChanged();

                }
                //If editText contains one word
                else if(st.countTokens() == 1){

                    //Get the first token
                    String inputword =  st.nextToken().toLowerCase();

                    for(int i = 0; i < groupCount; i++){

                        //If editText begins with the same letters as a parent
                        //Expand group and select.
                        if(listDataTitle.get(i).toLowerCase().startsWith(inputword)){
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
                //If editText contains two words
                else if(st.countTokens()==2){

                    String inputword =  st.nextToken().toLowerCase();

                    for(int i = 0; i < groupCount; i++){

                        //If editText equals a parent
                        if(inputword.equals(listDataTitle.get(i).toLowerCase())){
                            edit.setBackgroundColor(Color.WHITE);
                            elw.setItemChecked(i, true);
                            elw.expandGroup(i);

                            //Get next token
                            String nextInputword =  st.nextToken().toLowerCase();

                            //Get all the children from the current parent
                            int childrenCount = listAdapter.getChildrenCount(i);

                            List<String> childrenList = listHash.get(listDataTitle.get(i));

                            for(int j = 0; j < childrenCount; j++ ){

                                //If editText begins with the same letters as a child
                                //Select the child
                                if(childrenList.get(j).toLowerCase().startsWith(nextInputword)) {
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

                //If the editText doesn't match with any node the background is set to red
                //All the groups are collapsed and deselected.
                if(!found){
                    edit.setBackgroundColor(Color.RED);

                    for(int i = 0; i<listDataTitle.size(); i++){
                        elw.collapseGroup(i);
                        elw.setItemChecked(i, false);
                    }
                }
                //Update changes
                listAdapter.notifyDataSetChanged();
            }
        });
    }

    //Add all the parents and children
    private void addData(){
        listDataTitle = new ArrayList<>();
        listHash = new HashMap<>();

        //Add parents
        listDataTitle.add("Fruit");
        listDataTitle.add("Vegetables");
        listDataTitle.add("Snacks");
        listDataTitle.add("Fastfood");

        //Add children to each parent
        parentA = new ArrayList<>();
        parentA.add("Apple");

        parentB = new ArrayList<>();
        parentB.add("Cucumber");
        parentB.add("Tomato");
        parentB.add("Carrot");

        parentC = new ArrayList<>();
        parentC.add("Chips");
        parentC.add("Popcorn");

        parentD = new ArrayList<>();
        parentD.add("Hamburger");
        parentD.add("Pizza");

        //Add the parents with children into the hashmap.
        listHash.put(listDataTitle.get(0), parentA);
        listHash.put(listDataTitle.get(1), parentB);
        listHash.put(listDataTitle.get(2), parentC);
        listHash.put(listDataTitle.get(3), parentD);
     }
}
