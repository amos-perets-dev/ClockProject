package com.example.giniappproject;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;

import com.example.giniappproject.db.IRepository;
import com.example.giniappproject.db.Repository;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;

public class ClockService extends Service {

    private IRepository repository = Repository.create();

    private TimeHelper timeHelper = new TimeHelper();

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        Observable
                .interval(1, TimeUnit.SECONDS, AndroidSchedulers.mainThread())
                .map(ignored -> timeHelper.getCurrentTime())
                .doOnNext(new Consumer<String>() {
                    @Override
                    public void accept(String time) throws Exception {
                        Log.d("TEST_GAME", "TIME: " + time);
                    }
                })
                .map(Time::new)
                .subscribe(new Consumer<Time>() {
                    @Override
                    public void accept(Time time) throws Exception {
                        repository.insertTime(time);
                    }
                });

        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public boolean stopService(Intent name) {
        return super.stopService(name);
    }
}
