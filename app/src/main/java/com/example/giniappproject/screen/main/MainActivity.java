package com.example.giniappproject.screen.main;

import android.content.Intent;
import android.os.Bundle;
import android.os.PowerManager;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.lifecycle.ViewModelProviders;

import com.example.giniappproject.ClockService;
import com.example.giniappproject.R;
import com.jakewharton.rxbinding2.view.RxView;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.internal.functions.Functions;

public class MainActivity extends AppCompatActivity {

    private Intent intentToService;

    /**
     * Disposing all the subscription
     */
    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    private AppCompatButton startServiceButton;
    private AppCompatButton stopServiceButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TimeViewModel timeViewModel = initViewModel();

        timeViewModel.permission(getPackageName(), ((PowerManager) getSystemService(POWER_SERVICE)));

        initService();
        findViews();

        compositeDisposable.addAll(

        RxView.clicks(startServiceButton)
                .subscribe(Functions.actionConsumer(timeViewModel::startService)),

        RxView.clicks(stopServiceButton)
                .subscribe(Functions.actionConsumer(timeViewModel::stopService)),

        timeViewModel
                .askPermission(this)
                .subscribe(this::startActivity),

        timeViewModel
                .isStartService(this)
                .doOnNext(this::changeButtonVisibility)
                .subscribe(this::startOrStopService)
        );

    }

    /**
     * Bind the views
     */
    private void findViews(){
        startServiceButton = findViewById(R.id.start_service);
        stopServiceButton = findViewById(R.id.stop_service);
    }

    /**
     * Init the view model
     * @return {@link TimeViewModel}
     */
    private TimeViewModel initViewModel(){
        return ViewModelProviders.of(this).get(TimeViewModel.class);
    }

    /**
     * Init the service
     */
    private void initService(){
        intentToService = new Intent(this, ClockService.class);
    }

    /**
     * Change the buttons visibility by the service state
     */
    private void changeButtonVisibility(boolean isStartService){
        if (isStartService){
            startServiceButton.setVisibility(View.GONE);
            stopServiceButton.setVisibility(View.VISIBLE);
        } else {
            startServiceButton.setVisibility(View.VISIBLE);
            stopServiceButton.setVisibility(View.GONE);
        }
    }

    private void startOrStopService(boolean isStartService){
        if (isStartService){
            startService(intentToService);
        } else {
            stopService(new Intent(this, ClockService.class));
        }
    }

    @Override
    protected void onDestroy() {
        compositeDisposable.dispose();
        super.onDestroy();
    }

}
