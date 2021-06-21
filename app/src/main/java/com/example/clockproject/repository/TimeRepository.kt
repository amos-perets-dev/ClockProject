package com.example.clockproject.repository

import com.example.clockproject.data.ClockState
import com.example.clockproject.data.time.TypesTimeData
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.BehaviorSubject
import kotlin.collections.HashMap

class TimeRepository(
    clockStateClock: ClockState.Clock,
    clockStateAlarm: ClockState.Alarm,
    private val snoozeTime: Int
) :
    ITimeRepository {

    private val timesStateData = hashMapOf<ClockState, ClockState>()
    private val timesByState = BehaviorSubject.create<HashMap<ClockState, ClockState>>()
    private var alarmTime: ClockState.Alarm
    private var currentTime: ClockState.Clock

    init {

        timesStateData[ClockState.Clock] = clockStateClock
        currentTime = clockStateClock

        timesStateData[ClockState.Alarm] = clockStateAlarm
        alarmTime = clockStateAlarm

        timesByState.onNext(timesStateData)
    }

    override fun getTimeByState(state: ClockState): Observable<TypesTimeData> {
        return timesByState
            .hide()
            .subscribeOn(Schedulers.io())
            .map { it[state] }
            .map(ClockState::typesTimeData)
    }

    override fun addCurrentTime() {
        val clockState = timesStateData[ClockState.Clock] ?: return

        val isFinishSecondsCycle = setTime(ClockState.TimeType.SECONDS, clockState, true)

        if (isFinishSecondsCycle) {
            val isFinishMinutesCycle = setTime(ClockState.TimeType.MINUTES, clockState, true)
            if (isFinishMinutesCycle) {
                setTime(ClockState.TimeType.HOURS, clockState, true)
            }
        }
    }

    override fun addSnooze() {
        val clockState = timesStateData[ClockState.Alarm] ?: return
        val isFinishMinutesCycle =
            setTime(ClockState.TimeType.MINUTES, clockState, true, snoozeTime)
        if (isFinishMinutesCycle) {
            setTime(ClockState.TimeType.HOURS, clockState, true)
        }
    }

    override fun getAlarmTime(): ClockState.Alarm {
        return alarmTime
    }

    override fun getCurrentTime(): ClockState.Clock {
        return currentTime
    }

    override fun setTime(
        timeType: ClockState.TimeType,
        clockState: ClockState,
        increase: Boolean,
        period: Int
    ): Boolean {
        val currentClockState = timesStateData[clockState] ?: return false
        val typesTimeData = currentClockState.typesTimeData
        var isFinishCycle = false
        when (timeType) {
            ClockState.TimeType.HOURS -> {
                val hoursValue = typesTimeData.hours.timeValue
                typesTimeData.addHours(if (increase) hoursValue + period else hoursValue - period)
            }
            ClockState.TimeType.MINUTES -> {
                val minutesValue = typesTimeData.minutes.timeValue
                isFinishCycle =
                    typesTimeData.addMinutes(if (increase) minutesValue + period else minutesValue - period)
            }
            ClockState.TimeType.SECONDS -> {
                val secondsValue = typesTimeData.seconds.timeValue
                isFinishCycle =
                    typesTimeData.addSeconds(if (increase) secondsValue + period else secondsValue - period)
            }
        }
        currentClockState.typesTimeData = typesTimeData
        timesStateData[clockState] = currentClockState
        timesByState.onNext(timesStateData)
        if (clockState is ClockState.Clock) {
            currentTime = timesStateData[clockState] as ClockState.Clock
        } else {
            alarmTime = timesStateData[clockState] as ClockState.Alarm
        }
        return isFinishCycle
    }
}