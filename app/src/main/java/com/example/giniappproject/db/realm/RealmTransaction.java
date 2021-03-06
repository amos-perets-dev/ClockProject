package com.example.giniappproject.db.realm;

import com.example.giniappproject.Time;

import io.reactivex.Observable;
import io.realm.Realm;
import io.realm.RealmObject;
import io.realm.RealmResults;

public class RealmTransaction {

    private Realm realm = Realm.getDefaultInstance();

    public <T extends RealmObject> void insert(final T object ){
        realm.executeTransaction(realm -> realm.copyToRealmOrUpdate(object));
    }

    public <T extends RealmObject> Observable<RealmResults<T>> getObservableData(final Class<T> realmClass) {
        return realm.where(realmClass)
                .findAllAsync()
                .asFlowable()
                .toObservable()
                .filter(RealmResults::isLoaded)
                .filter(RealmResults::isValid);
    }

}
