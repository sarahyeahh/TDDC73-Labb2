package com.example.sarah.labb2;
        import android.app.Activity;
        import android.content.Context;
        import android.graphics.Color;
        import android.os.Bundle;
        import android.text.Editable;
        import android.text.TextWatcher;
        import android.util.Log;
        import android.view.Menu;
        import android.view.MenuItem;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.AdapterView;
        import android.widget.EditText;
        import android.widget.ExpandableListView;
        import android.widget.ExpandableListView.OnChildClickListener;
        import android.widget.TextView;
        import android.widget.Toast;

        import java.util.ArrayList;
        import java.util.HashMap;
        import java.util.List;
        import java.util.Map;




public class MainActivity extends Activity {

    private ExpandableListView elw;
    private Adapter listAdapter;
   // private SecondAdapter secondAdapter;
    private List<String> listDataTitle, parentA, parentB, parentC, parentD;

    private HashMap<String, List<String>> listHash;
    private EditText edit;


   // private HashMap<String, HashMap<String, List<String>>> newHash;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        edit = (EditText) findViewById(R.id.editText);

        elw = (ExpandableListView) findViewById(R.id.expandableListView);
        addData();

        listAdapter = new Adapter(this, listDataTitle, listHash);
        elw.setAdapter(listAdapter);

      // secondAdapter = new SecondAdapter(this, listDataChild, newHash);
      //  elw.setAdapter(secondAdapter);

       // elw.setOnGroupClickListener((ExpandableListView.OnGroupClickListener) this);


getText();



        elw.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                Log.d("onGroupClick:", "worked");
              //  parent.expandGroup(groupPosition);

                //EditText edit = (EditText) findViewById(R.id.editText);
                edit.setText("/" + listDataTitle.get(groupPosition));

                v.setBackgroundColor(Color.GRAY);

                return false;
            }

        });


        elw.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {

                Log.d("onChildClick:", "worked");
                edit.setText("/" + listDataTitle.get(groupPosition)+ "/" +  parent.getExpandableListAdapter().getChild(groupPosition, childPosition));
                v.setBackgroundColor(Color.GRAY);
                return true;
            }



        });

        edit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int start, int before, int count) {

                //Search

                for(int i = 0; i < listDataTitle.size(); i++){

                    if (listDataTitle.get(i).contains(charSequence)){
                        //Markera de som stämmer in
                       /* Toast toast =  Toast.makeText(MainActivity.this, listDataTitle.get(i), Toast.LENGTH_SHORT);
                        toast.show();*/
                       TextView text = findViewById(R.id.title);
                       text.setTextColor(Color.RED);
                       Log.d("Onwrite:", "worked");


                    }
                    else{
                        //Leta genom child.
                    }
                }


            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });


/*
        elw.setOnItemClickListener(new AdapterView.OnItemClickListener() {
           @Override
            public void onItemClick(AdapterView<?> parent, View view, int i, long id) {
                //do react to click do not change the ui without change the model/adapter
                Toast.makeText(MainActivity.this, "You clicked:"+ listAdapter.getGroup(i), Toast.LENGTH_SHORT).show();
                Log.v("Data", "You clicked");
            }*/

          /*
          @Override
          public boolean onGroupClick(ExpandableListView elw, View view, int i, long id){

              Toast.makeText("Hej").show();
              return true;

          }
        });

*/

    }

    private void addData(){
        listDataTitle = new ArrayList<>();
        listHash = new HashMap<>();

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

    public String getText(){

        String text = (String) edit.getText().toString();
       // Toast toast =  Toast.makeText(MainActivity.this, text, Toast.LENGTH_SHORT);
      //  toast.show();
        return text;
    }
}
