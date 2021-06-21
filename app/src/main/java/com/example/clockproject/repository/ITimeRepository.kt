package com.example.clockproject.repository

import com.example.clockproject.data.ClockState
import com.example.clockproject.data.time.TypesTimeData
import io.reactivex.Observable

interface ITimeRepository {

    /**
     * Get the time by current state
     *
     * @return [Observable][TypesTimeData]
     */
    fun getTimeByState(state: ClockState): Observable<TypesTimeData>

    fun setTime(timeType: ClockState.TimeType, clockState: ClockState, increase: Boolean, period: Int = 1): Boolean

    /**
     * Add the current time
     */
    fun addCurrentTime()

    /**
     * Get the alarm time
     *
     * @return [ClockState.Alarm]
     */
    fun getAlarmTime(): ClockState.Alarm

    /**
     * Get the current time clock
     *
     * @return [ClockState.Clock]
     */
    fun getCurrentTime(): ClockState.Clock

    /**
     * Call when the user click on the light button
     */
    fun addSnooze()
}