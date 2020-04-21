package com.example.giniappproject.db;

import android.util.Log;

import com.example.giniappproject.Time;
import com.example.giniappproject.db.realm.RealmTransaction;
import com.example.giniappproject.model.ITimeValidation;
import com.example.giniappproject.model.TimeValidationImpl;

import io.reactivex.Observable;
import io.realm.RealmObject;

public class Repository implements IRepository {

    private RealmTransaction realmTransaction = new RealmTransaction();

    private static Repository repository;

    private ITimeValidation timeValidation = new TimeValidationImpl();

    private Repository(){}

    @Override
    public <T extends RealmObject> void insertTime(T object) {
        if (timeValidation.isTimeValid(object.toString())){
            Log.d("TEST_CLOCK", "test insert time: " + ((Time)object).getTime());
            realmTransaction.insert(object);
        }
    }

    @Override
    public <T extends RealmObject> Observable<T> getTimeData(Class<T> realmClass) {
        return realmTransaction.getObservableData(realmClass)
                .flatMap(Observable::fromIterable);
    }

    public static Repository create() {
        if (repository == null) {
            repository = new Repository();
        }
        return repository;
    }

}
