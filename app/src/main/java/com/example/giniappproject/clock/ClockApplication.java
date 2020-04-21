package com.example.giniappproject.clock;

import android.app.Application;

import io.realm.Realm;
import io.realm.RealmConfiguration;

public class ClockApplication extends Application {


    @Override
    public void onCreate() {
        super.onCreate();

        Realm.init(this);

        RealmConfiguration realmConfiguration = new RealmConfiguration.Builder()
                .name("clock_project.realm")
                .encryptionKey(new byte[64])
                .schemaVersion(1)
                .build();


        Realm.setDefaultConfiguration(realmConfiguration);

    }
}
