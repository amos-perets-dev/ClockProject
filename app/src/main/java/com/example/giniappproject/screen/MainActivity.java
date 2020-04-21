package com.example.giniappproject.screen;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.PowerManager;
import android.provider.Settings;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.giniappproject.ClockService;
import com.example.giniappproject.R;
import com.example.giniappproject.Time;
import com.example.giniappproject.db.IRepository;
import com.example.giniappproject.db.Repository;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import io.reactivex.functions.Consumer;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            Intent intent = new Intent();
            String packageName = getPackageName();
            PowerManager pm = (PowerManager) getSystemService(POWER_SERVICE);
            if (!pm.isIgnoringBatteryOptimizations(packageName)) {
                intent.setAction(Settings.ACTION_REQUEST_IGNORE_BATTERY_OPTIMIZATIONS);
                intent.setData(Uri.parse("package:" + packageName));
                startActivity(intent);
            }
        }

        final Intent intentToService = new Intent(this, ClockService.class);

        IRepository iRepository = Repository.create();

        iRepository
                .getTimeData(Time.class)
                .subscribe(new Consumer<Time>() {
                    @Override
                    public void accept(Time time) throws Exception {
//                        Log.d("TEST_GAMe", "Notify Time: " + time);
                    }
                });


        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

//                Calendar cal = Calendar.getInstance();
//                Date currentLocalTime = cal.getTime();
//                DateFormat date = new SimpleDateFormat("HH:mm");
//
//                String localTime = date.format(currentLocalTime);
//
//                if (isTimeValid(localTime)){
//                    iRepository
//                            .insertTime(new Time(localTime));
//                    Log.d("TEST_GAME", "TIME: " + localTime);
//
//                }
//

                startService(intentToService);
            }
        });

        fab.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {

                boolean isTimeValid = isTimeValid("4545");

                Log.d("TEST_GAMe", "TIME 4545: " + isTimeValid);


                return true;
            }
        });
    }

    private boolean isTimeValid(String currentTime){
        return currentTime.contains("1");
    }

}
