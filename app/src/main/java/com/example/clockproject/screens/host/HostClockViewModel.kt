package com.example.clockproject.screens.host

import androidx.lifecycle.MutableLiveData
import com.example.clockproject.clock_app.base.BaseViewModel
import com.example.clockproject.data.ClockState
import com.example.clockproject.managers.clock.IClockManager
import com.example.clockproject.managers.settings.ISettingsManager
import com.example.clockproject.managers.state.IHandlerStateManager
import com.example.clockproject.subscribePro
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class HostClockViewModel(
    private val handlerStateManager: IHandlerStateManager,
    private val settingsManager: ISettingsManager,
    private val clockManager: IClockManager
) : BaseViewModel() {

    val clockState = MutableLiveData<ClockState>()
    val alarmIconState = MutableLiveData<Boolean>()
    val isAlarm = MutableLiveData<Boolean>()
    val isShowLight = MutableLiveData<Boolean>()

    private var lightTimerDisposable: Disposable? = null

    init {
        compositeDisposable.addAll(
            settingsManager
                .isActiveAlarm()
                .subscribeOn(Schedulers.io())
                .doOnNext(alarmIconState::postValue)
                .subscribePro(),

            clockManager
                .isAlarmStart()
                .subscribeOn(Schedulers.io())
                .doOnNext(isAlarm::postValue)
                .subscribePro()
        )

        clockState.postValue(handlerStateManager.initState())
    }

    fun onClickChangeMode() {
        clockManager.disposeAlarm()
        settingsManager.dispose()
        val changeState = handlerStateManager.changeState()
        clockState.postValue(changeState)
    }

    fun onClickUp() {
        clockManager.disposeAlarm()
        clockState.value?.let { settingsManager.onClickUp(it) }
    }

    fun onClickDown() {
        clockManager.disposeAlarm()
        clockState.value?.let { settingsManager.onClickDown(it) }
    }

    /**
     * Enter the clock settings
     */
    fun onLongClickSet() {
        clockManager.disposeAlarm()
        clockState.value?.let { settingsManager.setEditMode(it) }
    }

    /**
     * Move among the times types
     */
    fun onClickSet() {
        clockManager.disposeAlarm()
        clockState.value?.let { settingsManager.setTimeType(it) }
    }

    fun onClickLight() {
        isShowLight.postValue(true)

        lightTimerDisposable?.dispose()
        lightTimerDisposable = clockManager
            .lightTimer()
            .subscribeOn(Schedulers.io())
            .doOnNext { isShowLight.postValue(false) }
            .subscribePro()

    }

    override fun onCleared() {
        clockManager.dispose()
        lightTimerDisposable?.dispose()
        super.onCleared()
    }
}