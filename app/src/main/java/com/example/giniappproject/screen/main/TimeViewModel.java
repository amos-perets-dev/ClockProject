package com.example.giniappproject.screen.main;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.PowerManager;
import android.provider.Settings;

import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveDataReactiveStreams;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import io.reactivex.Observable;

public class TimeViewModel extends ViewModel {

    private MutableLiveData<Boolean> startService = new MutableLiveData<>();

    private MutableLiveData<Intent> askPermissioin = new MutableLiveData<Intent>();

    /**
     * Call when need to start the service
     */
    public void startService(){
        startService.postValue(true);
    }

    /**
     * Call when need to stop the service
     */
    public void stopService(){
        startService.postValue(false);
    }

    /**
     * Observing changes of service
     *
     * @return {@link Observable} which emits service status of user when it's changed.
     * {@code true} if service need to start , else {@code false}
     */
    public Observable<Boolean> isStartService(LifecycleOwner lifecycleOwner){
        return Observable.fromPublisher(LiveDataReactiveStreams.toPublisher(lifecycleOwner, startService));
    }

    public Observable<Intent> askPermission(LifecycleOwner lifecycleOwner) {
        return Observable.fromPublisher(LiveDataReactiveStreams.toPublisher(lifecycleOwner, askPermissioin));
    }

    /**
     * Create permission battery optimizations
     */
    public void permission(String packageName, PowerManager pm) {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            Intent intent = new Intent();
            if (!pm.isIgnoringBatteryOptimizations(packageName)) {
                intent.setAction(Settings.ACTION_REQUEST_IGNORE_BATTERY_OPTIMIZATIONS);
                intent.setData(Uri.parse("package:" + packageName));
                askPermissioin.postValue(intent);
            }
        }
    }
}
