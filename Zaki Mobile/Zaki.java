package com.example.hrag.zaki;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ToggleButton;

public class Zaki extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zaki);

        Button light1 =  findViewById(R.id.light1);
        Button light2 = findViewById(R.id.light2);
        Button door = findViewById(R.id.door);
        boolean in=true;


        light1.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){

                click("LIGHT1");

            }
        });
        light2.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                click("LIGHT2");

            }
        });
        door.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                click("PULSE");

            }
        });




    }
    protected void click(final String r){
        final  Client client = new Client();
        final InterClient interClient = new InterClient();
        final ToggleButton intra  = findViewById(R.id.intra);
        Thread thread = new Thread(new Runnable() {

            @Override
            public void run() {
                String res="";
                try  {

                    if(intra.isChecked()){
                        res= interClient.request(r);
                        Log.d("checked?", "checked ");
                    }else {
                        Log.d("checked?", "no ");
                        res = client.request(r);
                    }
                    Log.d("done",res);
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });

        thread.start();

    }
}
