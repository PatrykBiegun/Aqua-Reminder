package com.example.aquareminder;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private TextView welcome;
    private EditText inputName;
    private EditText inputWeight;
    private String Weight, Calories, Name;
    private EditText inputCalories;
    SharedPreferences sp;
    private Button check;
    private int waterquantity = 1600,i = 1600,  waterconst ;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        waterquantity = 1600;

        welcome = findViewById(R.id.welcome);
        inputName = findViewById(R.id.inputName);
        inputWeight = findViewById(R.id.inputWeight);
        inputCalories = findViewById(R.id.inputCalories);


        check = findViewById((R.id.check));
        Name = inputName.getText().toString();

        sp = getSharedPreferences(  "MyUserPrefs", Context.MODE_PRIVATE);

        SharedPreferences preferences = getSharedPreferences("MyUserPrefs", MODE_PRIVATE);
        String remember_value = preferences.getString("remember", "");
        if(remember_value.equals("true")){
            openWaterpanel();
        }else if(remember_value.equals("false")){
            Toast.makeText(MainActivity.this, "Edytuj swoje dane", Toast.LENGTH_SHORT).show();
        }


        Name = inputName.getText().toString();
        waterquantity = 1800;
        check = (Button) findViewById(R.id.check);
        check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Weight = inputWeight.getText().toString();
                Calories = inputCalories.getText().toString();
                Name = inputName.getText().toString();
                SharedPreferences.Editor editor = sp.edit();

                if(Integer.parseInt ( Calories )  > 3000 || Integer.parseInt ( Weight )  > 100 ){
                    waterquantity = 3000;
                }else
                    if(Integer.parseInt ( Calories ) >  Integer.parseInt ( Weight ) * 30 && Integer.parseInt ( Calories ) > waterquantity){
                          {waterquantity = Integer.parseInt ( Calories );}
                    }else
                        if(Integer.parseInt ( Weight ) * 30 > waterquantity )
                          {waterquantity = Integer.parseInt ( Weight ) * 30;}

                      for (i = waterquantity; i % 200 != 0; i=i+1){
                        waterquantity = i+1;
                      }

                waterconst = waterquantity;

                if(!Weight.isEmpty() && !Calories.isEmpty() && !Name.isEmpty()){
                editor.putString("Name", Name);
                editor.putString("Calories", Calories);
                editor.putString("Weight", Weight);
                editor.putInt ("watercraft", waterquantity);
                editor.putInt ("waterconst", waterconst);
                editor.putString("remember", "true");

                editor.commit();
                Toast.makeText(MainActivity.this, "Informacje zapisane", Toast.LENGTH_LONG).show();
                openWaterpanel();
            }else{
                    Toast.makeText(MainActivity.this, "Musisz uzupełnić swoje dane!", Toast.LENGTH_SHORT).show();
                }

            }
        });

    }

    public void openWaterpanel(){
        Intent intent = new Intent(this, Waterpanel.class);
        startActivity(intent);
    }



}

