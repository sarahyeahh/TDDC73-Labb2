package com.example.sarah.labb2;

import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
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
        elw = (ExpandableListView) findViewById(R.id.expandableListView);
        addData();
        listAdapter = new Adapter(this, listDataTitle, listHash);

        elw.setAdapter(listAdapter);

        elw.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                Log.d("onGroupClick:", "worked");

                edit.setText("/" + listDataTitle.get(groupPosition));

                return false;
            }

        });

        elw.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                Log.d("onChildClick:", "worked");
                edit.setText("/" + listDataTitle.get(groupPosition)+ "/" +  parent.getExpandableListAdapter().getChild(groupPosition, childPosition));

                return false;
               // int index = parent.getFlatListPosition(ExpandableListView.getPackedPositionForChild(groupPosition, childPosition));
                //parent.setItemChecked(index, true); //Markera
               // parent.setItemChecked(index, false); //Avmarkera
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

                //parent
                if(st.countTokens() == 1){

                    //Expandera alla parent som matchar
                    Log.d("Count tokens", "1");
                    int i = 0;

                    while(st.hasMoreTokens()){

                        String inputword = st.nextToken();

                        if(inputword.equals(listDataTitle.get(i))){

                            Toast toast =  Toast.makeText(MainActivity.this, listDataTitle.get(i), Toast.LENGTH_SHORT);
                            toast.show();
                            elw.expandGroup(i);

                            //  int index = elw.getFlatListPosition(ExpandableListView.getPackedPositionForGroup(i));
                            //elw.setItemChecked(index, true); //Markera
                            // elw.setItemChecked(index, false); //Avmarkera
                        }
                        //Avmarkera alla
                        else{
                           // elw.getChi
                        }

                        i++;
                    }

                }
                //child
                else if(st.countTokens() == 2){

                    Log.d("Count tokens", "2");
                    int i = 0;

                    while(st.hasMoreTokens()){

                        String word = st.nextToken();

                        //Markera alla som stämmer
                        if(word.equals(listDataTitle.get(i))){

                            for(int j = 0; j < 2; j++){
                                System.out.println(j);
                               // int child  =  elw.getExpandableListAdapter().getChild(i, j);
                                //Toast toast =  Toast.makeText(MainActivity.this, i, Toast.LENGTH_SHORT);
                                //toast.show();
                            }
                            Toast toast =  Toast.makeText(MainActivity.this, listDataTitle.get(i), Toast.LENGTH_SHORT);
                            toast.show();
                        }
                        //Avmarkera alla.
                        else{

                        }
                        i++;
                    }
                }
                //Other
                else{
                    //Expandera inte
                    Log.d("Count tokens", "annat/0");
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

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
        parentB.add("childB2");

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
