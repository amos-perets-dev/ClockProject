package com.example.giniappproject.db;

import android.util.Log;

import com.example.giniappproject.db.realm.RealmTransaction;
import com.example.giniappproject.model.ITimeValidation;
import com.example.giniappproject.model.TimeValidationImpl;

import io.reactivex.Observable;
import io.reactivex.functions.Consumer;
import io.realm.RealmObject;
import io.realm.RealmResults;

public class Repository implements IRepository {

    private RealmTransaction realmTransaction = new RealmTransaction();

    private static Repository repository;

    private ITimeValidation timeValidation = new TimeValidationImpl();

    private Repository(){}

    @Override
    public <T extends RealmObject> void insertTime(T object) {
        if (timeValidation.isTimeValid(object.toString())){
            Log.d("TEST_GAME", "2 insertTime");
            realmTransaction.insert(object);
        }
    }

    @Override
    public <T extends RealmObject> Observable<T> getTimeData(Class<T> realmClass) {
        return realmTransaction.getObservableData(realmClass)
                .doOnNext(new Consumer<RealmResults<T>>() {
                    @Override
                    public void accept(RealmResults<T> ts) throws Exception {
                        Log.d("TEST_GAME", "SIZE LIST: " + ts.size());
                    }
                })
                .flatMap(Observable::fromIterable);
    }

    public static Repository create() {
        if (repository == null) {
            repository = new Repository();
        }
        return repository;
    }

}
