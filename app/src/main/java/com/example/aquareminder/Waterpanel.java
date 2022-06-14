package com.example.aquareminder;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;

import java.util.Calendar;

public class Waterpanel extends AppCompatActivity {


    private Button edit,again;
    private int watercount, waterconst;
    SharedPreferences sp;
    LottieAnimationView water_glass;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_waterpanel);
        edit = findViewById(R.id.edit);
        again = findViewById ( R.id.again );
        createNotificationsChannel ();

        TextView t1, t2;
        sp = getSharedPreferences(  "MyUserPrefs", Context.MODE_PRIVATE);




        t1 = findViewById(R.id.t1);
        t2 = findViewById(R.id.watercalc);
        water_glass = findViewById (R.id.lottieGlass);
        SharedPreferences sp = getApplicationContext().getSharedPreferences("MyUserPrefs", Context.MODE_PRIVATE);
        String name = sp.getString("Name", "");
        String weight = sp.getString("weight", "");
        String calories = sp.getString("Calories", "");
        int waterquantity = sp.getInt ( "watercraft", -1 );
        int waterconst = sp.getInt ( "waterconst", -1 );

        watercount = waterquantity;
        if(waterquantity == 0 || watercount == 0){
        watercount = waterconst;
           sendNote ();
        }


        t1.setText("Witaj " + name);
        t2.setText("Na dzień dzisiejszy zotało ci do wypicia jeszcze: " + (float)waterquantity / 200 + " szklanek wody \n \n czyli dokłanie " + waterquantity + "ml");

        Calendar calendar = Calendar.getInstance ();
        calendar.set ( Calendar.HOUR_OF_DAY, 8);
        calendar.set ( Calendar.MINUTE,30);
        calendar.set ( Calendar.SECOND,00 );

        if (Calendar.getInstance ().after ( calendar )) {
            calendar.add ( Calendar.DAY_OF_MONTH , 1 );
        }
        Intent intent = new Intent (Waterpanel.this,ReminderBrodcast.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast ( Waterpanel.this,0,intent,PendingIntent.FLAG_CANCEL_CURRENT );

        AlarmManager alarmManager =(AlarmManager) getSystemService (ALARM_SERVICE);
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis (),AlarmManager.INTERVAL_DAY,pendingIntent);
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            alarmManager.setExactAndAllowWhileIdle ( AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis (),pendingIntent );

        }




        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openEditor();
            }
        });


        water_glass.setOnClickListener (v -> {

            if(watercount > 0)watercount = watercount - 200;
            SharedPreferences.Editor editor = sp.edit();
            editor.putInt ("watercraft", watercount);
            editor.commit();
            if(watercount!=0) {
                t2.setText ( "Na dzień dzisiejszy zotało ci do wypicia jeszcze: " + (float) watercount / 200 + " szklanek wody \n \n czyli dokłanie " + watercount + "ml" );
            }else {
                t2.setText ( "Gratuluję! Cel na dzisiaj spełnony, jesteś świetnie nawodniony \n Klikniaj dalej aby zresetować licznik"  );
                again.setVisibility ( View.VISIBLE );
            }


            water_glass.playAnimation ();
             if(watercount > 0) {
                 Toast.makeText ( this, "Woda wypita!" , Toast.LENGTH_LONG).show ();
                 sendNote ();
             }

        });

        again.setOnClickListener ( new View.OnClickListener () {
            @Override
            public void onClick(View view) {
                SharedPreferences sp = getApplicationContext().getSharedPreferences("MyUserPrefs", Context.MODE_PRIVATE);
                int waterconst = sp.getInt ( "waterconst", -1 );

                watercount = waterconst;
                openWaterpanel();
            }
        } );



    }
    public void sendNote(){

        Intent intent = new Intent (Waterpanel.this,ReminderBrodcast.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast ( Waterpanel.this,0,intent,0 );

        AlarmManager alarmManager =(AlarmManager) getSystemService (ALARM_SERVICE);

        long timeatButtonClick = System.currentTimeMillis ();

        long oneHour = 1000 * 1800 ;

        alarmManager.set ( alarmManager.RTC_WAKEUP,
                timeatButtonClick + oneHour, pendingIntent);


    }





    public void openEditor(){
        Intent intent = new Intent(this, MainActivity.class);
        SharedPreferences preferences = getSharedPreferences("MyUserPrefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("remember", "false");
        editor.apply();
        startActivity(intent);
    }

    private void createNotificationsChannel(){
        CharSequence name= "LemubitReminderChannel";
        String description = "Channel for water reminder";
        int importance = NotificationManager.IMPORTANCE_DEFAULT;
        NotificationChannel channel = new NotificationChannel ( "notifyLemubit",name, importance );
        channel.setDescription( description );

        NotificationManager notificationManager = getSystemService ( NotificationManager.class );
        notificationManager.createNotificationChannel ( channel );
    }

    public void openWaterpanel(){
        Intent intent = new Intent(this, Waterpanel.class);
        startActivity(intent);
    }



}

