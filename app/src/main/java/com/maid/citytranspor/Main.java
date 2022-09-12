package com.maid.citytranspor;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Main extends AppCompatActivity {
    GlobalClass gb = new GlobalClass();
    TextView t1,t2,t3,routetext,changetext, to, from;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    ArrayList<String> arrayList = new ArrayList<>();
    Map<String,ArrayList<String>> multiVal= new HashMap<String,ArrayList<String>>();
    int flag=0;
    ArrayList<String> sto = new ArrayList<>();
    String key="No bus available!";
    String root = new String();

    public void route(String routeVar1) {
        routetext = findViewById(R.id.routeText);
        routetext.setVisibility(View.INVISIBLE);
        t1 =findViewById(R.id.stop11);
        t2 =findViewById(R.id.stop22);
        t3 =findViewById(R.id.stop33);
        t1.setVisibility(View.INVISIBLE);
        t2.setVisibility(View.INVISIBLE);
        t3.setVisibility(View.INVISIBLE);
        Log.d("lolol", "arrvied at database class!");
        DatabaseReference reference = database.getReference().child("cities");
        Log.d("lolol", "still at at database class!");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                    if (snapshot1.child(routeVar1).exists()) {
                        for (DataSnapshot vals : snapshot1.child(routeVar1).getChildren()) {
                            Log.d("lolol", "arrvied at fireBase bottom!");
                            arrayList.add(vals.getValue().toString());

                        }
                    }


                }
                routetext.setVisibility(View.VISIBLE);
                routetext.setText("The Stops are : ");
                t1.setVisibility(View.VISIBLE);
                t2.setVisibility(View.VISIBLE);
                t3.setVisibility(View.VISIBLE);
                t1.setText(arrayList.get(0));
                t2.setText(arrayList.get(1));
                t3.setText(arrayList.get(2));
                t1.setTextSize(25f);
                t3.setTextSize(25f);
                t2.setTextSize(25f);
                Log.d("datac",arrayList.toString());
                Log.d("lolol", "leaving database class!");
                arrayList.clear();
            }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



    }

    public void stops(String stop1, String stop2) {
        t1.setVisibility(View.INVISIBLE);
        Log.d("stopsss",stop1 + " " + stop2);
        if (!stop1.equals("None") && !stop2.equals("None")) {
            DatabaseReference reference = database.getReference().child("cities");
            reference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                        for (DataSnapshot snapShot2 : snapshot1.getChildren()) {
                            multiVal.put(snapShot2.getKey(), new ArrayList<String>());
                            for (DataSnapshot snapShot3 : snapShot2.getChildren()) {
                                multiVal.get(snapShot2.getKey()).add(snapShot3.getValue().toString());
                            }
                        }
                    }
                    for(Map.Entry<String,ArrayList<String>> vals : multiVal.entrySet()){
                        gb.setRouteIs("No bus available!");
                        key = vals.getKey();
                        Log.d("stopS","current key is : " + key);
                        Log.d("stopS","current value of the key is  : " + vals.getValue().toString());
                        if(vals.getValue().contains(stop1)&&vals.getValue().contains(stop2)) {
                            Log.d("stopss", "Bingo!! same!");
                            flag = 1;
                            if (flag == 1) {
                                gb.setRouteIs(key);
                                flag = 0;
                                break;
                            }
                        }
                    }
                    Log.d("stopSw2ww","Loop ended!");
                    Log.d("stopSE","Root added is : "+gb.getRouteIs());
                    t2 = findViewById(R.id.stop22);
                    t1=findViewById(R.id.stop11);
                    t1.setVisibility(View.VISIBLE);
                    t1.setText("The suitable route is :");
                    t1.setTextSize(25f);
                    t2.setVisibility(View.VISIBLE);
                    t2.setTextSize(50f);
                    t2.setText(gb.getRouteIs());

                }



                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });

        }
    }
    Spinner askSpinner, stop1Spinner,stop2Spinner;
    Button button;
    String stop1 = new String();
    String stop2 = new String();
    ArrayList<String>  arrayList1 = new ArrayList<>();

    public void transport(){
        to = findViewById(R.id.to);
        from=findViewById(R.id.from);
        to.setVisibility(View.INVISIBLE);
        from.setVisibility(View.INVISIBLE);
        changetext=findViewById(R.id.changeText);
        changetext.setText("Please choose any of the option!");
        routetext=findViewById(R.id.routeText);
        routetext.setVisibility(View.INVISIBLE);
        t1 =findViewById(R.id.stop11);
        t2 =findViewById(R.id.stop22);
        t3 =findViewById(R.id.stop33);
        t1.setVisibility(View.INVISIBLE);
        t2.setVisibility(View.INVISIBLE);
        t3.setVisibility(View.INVISIBLE);
        askSpinner = findViewById(R.id.askSpinner);
        stop1Spinner = findViewById(R.id.stop1);
        stop2Spinner = findViewById(R.id.stop2);
        button.setVisibility(View.INVISIBLE);
        stop1Spinner.setVisibility(View.INVISIBLE);
        stop2Spinner.setVisibility(View.INVISIBLE);



        String[] ask = {"None","Routes","Stops"};
        String[] route = {"None","route1", "route 2","route 3"};
        String[] stop = {"None","Doiwala","Jogiwala","Rispana","Rishikesh", "Kothdwar","Dev Prayag","Gujruwala", "Tunnuwala","Shastri Nagar"};

        ArrayAdapter askAdapter = new ArrayAdapter(Main.this, android.R.layout.simple_spinner_item,ask);
        askAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        askSpinner.setAdapter(askAdapter);
        askSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (!adapterView.getItemAtPosition(i).equals("None")) {
                    gb.setAnswer(adapterView.getItemAtPosition(i).toString());

                    Log.d("tagA", gb.getAnswer());

                    if (gb.getAnswer().equals("Routes")) {
                        changetext.setText("Please choose any route to see the stops!");
                        stop1Spinner.setVisibility(View.INVISIBLE);
                        stop2Spinner.setVisibility(View.INVISIBLE);
                        button.setVisibility(View.VISIBLE);
                        ArrayAdapter routeAdapter = new ArrayAdapter(Main.this, android.R.layout.simple_dropdown_item_1line, route);
                        askSpinner.setAdapter(routeAdapter);
                        askSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                                if (!adapterView.getItemAtPosition(i).toString().equals("None")) {
                                    route(adapterView.getItemAtPosition(i).toString());
                                    Log.d("lololol", "The array is : "+arrayList.toString() + "");
                                }
                            }


                            @Override
                            public void onNothingSelected(AdapterView<?> adapterView) {

                            }
                        });
                    }
                    else if (gb.getAnswer().equals("Stops")) {
                        to.setVisibility(View.VISIBLE);
                        from.setVisibility(View.VISIBLE);
                        changetext.setText("Please choose any stops to see the suitable route bus to pick!");
                        stop1Spinner.setVisibility(View.VISIBLE);
                        stop2Spinner.setVisibility(View.VISIBLE);
                        button.setVisibility(View.VISIBLE);
                        ArrayAdapter stopS = new ArrayAdapter(Main.this, android.R.layout.simple_dropdown_item_1line, stop);
                        stop1Spinner.setAdapter(stopS);
                        stop2Spinner.setAdapter(stopS);
                        stop1Spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                                gb.set_stop1(adapterView.getItemAtPosition(i).toString());
                                stop1 = gb.get_stop1();
                                Log.d("tags", gb.get_stop1());
                                if (!stop1.equals(null) && !stop2.equals(null)) {
                                    stops(stop1, stop2);
                                }

                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> adapterView) {

                            }
                        });
                        stop2Spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                                gb.set_stop2(adapterView.getItemAtPosition(i).toString());
                                Log.d("tags", gb.get_stop2());
                                stop2 = gb.get_stop2();
                                stops(stop1, stop2);
                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> adapterView) {

                            }
                        });


                    }
                }

            }


            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {


            }
        });






    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        button = findViewById(R.id.okayBut);
        transport();
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                transport();
            }
        });


    }
}