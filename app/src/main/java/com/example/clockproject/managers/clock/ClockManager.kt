package com.example.clockproject.managers.clock

import com.example.clockproject.managers.settings.ISettingsManager
import com.example.clockproject.repository.ITimeRepository
import com.example.clockproject.subscribePro
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.internal.functions.Functions
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.BehaviorSubject
import java.util.concurrent.TimeUnit

class ClockManager(
    private val timeRepository: ITimeRepository,
    private val settingsManager: ISettingsManager,
    private val alarmDuration: Int,
    private val lightDuration: Int
) : IClockManager {

    private val compositeDisposable = CompositeDisposable()
    private val isAlarmStart = BehaviorSubject.createDefault(false)
    private var isSnoozeState = false
    private var dispoable: Disposable? = null

    init {
        compositeDisposable.add(
            Observable.interval(1000, TimeUnit.MILLISECONDS, AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .doOnNext(Functions.actionConsumer(timeRepository::addCurrentTime))
                .flatMap {
                    settingsManager.isActiveAlarm()
                        .filter(Functions.equalsWith(true))
                        .doOnNext { setIsAlarm() }
                }
                .subscribePro()
        )
    }

    private fun setIsAlarm() {
        val currentTime = timeRepository.getCurrentTime()
        val currentTimeText = currentTime.typesTimeData.toString()

        val alarmTime = timeRepository.getAlarmTime()
        val alarmTimeText = alarmTime.typesTimeData.toString()

        val isCurrentAlarmTime = currentTimeText == alarmTimeText

        if (isCurrentAlarmTime) {
            dispoable?.dispose()
            dispoable =
                Observable.interval(1000, TimeUnit.MILLISECONDS, AndroidSchedulers.mainThread())
                    .subscribeOn(Schedulers.io())
                    .doOnNext { isAlarmStart.onNext(true) }
                    .filter { it == alarmDuration.toLong() }
                    .takeUntil { it == alarmDuration.toLong() }
                    .doOnNext { isAlarmStart.onNext(false) }
                    .flatMap { Observable.fromCallable { this.isSnoozeState } }
                    .doOnNext { isSnoozeState ->
                        if (isSnoozeState) {
                            this.isSnoozeState = false
                            timeRepository.addSnooze()
                        }
                    }
                    .subscribePro()
        }

    }

    override fun isAlarmStart(): Observable<Boolean> {
        return isAlarmStart
            .hide()
            .subscribeOn(Schedulers.io())
    }

    override fun lightTimer(): Observable<Long> {
        return isAlarmStart()
            .subscribeOn(Schedulers.io())
            .doOnNext { isAlarmTime ->
                if (isAlarmTime) {
                    addSnoozeState()
                }
            }
            .flatMap {
                Observable.timer(
                    lightDuration.toLong(),
                    TimeUnit.MILLISECONDS,
                    AndroidSchedulers.mainThread()
                )
            }
    }

    private fun addSnoozeState() {
        isSnoozeState = true
    }

    override fun disposeAlarm() {
        dispoable?.dispose()
    }

    override fun dispose() {
        compositeDisposable.dispose()
        disposeAlarm()
    }
}