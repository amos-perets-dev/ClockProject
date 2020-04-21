package com.example.giniappproject;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import androidx.annotation.Nullable;

import com.example.giniappproject.db.IRepository;
import com.example.giniappproject.db.Repository;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.disposables.Disposables;

public class ClockService extends Service {

    private IRepository repository = Repository.create();

    private TimeHelper timeHelper = new TimeHelper();

    private Disposable disposable = Disposables.disposed();

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        disposable = Observable
                .interval(1, TimeUnit.SECONDS, AndroidSchedulers.mainThread())
                .map(ignored -> timeHelper.getCurrentTime())
                .map(Time::new)
                .subscribe(repository::insertTime);

        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        disposable.dispose();
        stopSelf();
        super.onDestroy();
    }

}
