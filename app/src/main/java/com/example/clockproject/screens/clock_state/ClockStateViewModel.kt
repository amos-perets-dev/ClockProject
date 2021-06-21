package com.example.clockproject.screens.clock_state

import androidx.lifecycle.MutableLiveData
import com.example.clockproject.clock_app.base.BaseViewModel
import com.example.clockproject.data.ClockState
import com.example.clockproject.data.time.TypesTimeData
import com.example.clockproject.managers.settings.ISettingsManager
import com.example.clockproject.repository.ITimeRepository
import com.example.clockproject.repository.TimeRepository
import com.example.clockproject.subscribePro
import io.reactivex.schedulers.Schedulers

open class ClockStateViewModel(
    settingsManager: ISettingsManager,
    clockState: ClockState,
    timeRepository: ITimeRepository
) : BaseViewModel() {

    val timeTypeActive = MutableLiveData<ClockState.TimeType>()
    val timeTypeInactive = MutableLiveData<ClockState.TimeType>()
    val typesTimeData = MutableLiveData<TypesTimeData>()

    init {

        compositeDisposable.addAll(
            settingsManager
                .getTimeType()
                .subscribeOn(Schedulers.io())
                .scan { prevType, currType ->
                    timeTypeInactive.postValue(prevType)
                    currType
                }
                .doOnNext(timeTypeActive::postValue)
                .subscribePro(),

            timeRepository
                .getTimeByState(clockState)
                .subscribeOn(Schedulers.io())
                .doOnNext {
                    typesTimeData.postValue(it)
                }
                .subscribePro()
        )

    }
}