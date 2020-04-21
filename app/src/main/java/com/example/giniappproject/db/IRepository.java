package com.example.giniappproject.db;

import io.reactivex.Observable;
import io.realm.RealmObject;

public interface IRepository {

    <T extends RealmObject> void insertTime(final T object );

    <T extends RealmObject> Observable<T> getTimeData(final Class<T> realmClass);
}
