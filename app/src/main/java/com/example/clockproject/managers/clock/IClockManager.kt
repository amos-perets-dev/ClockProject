package com.example.clockproject.managers.clock

import io.reactivex.Observable

interface IClockManager {
    fun dispose()
    fun disposeAlarm()

    /**
     * Get the alarm time state
     *
     * @return [Observable][Boolean]
     */
    fun isAlarmStart(): Observable<Boolean>

    /**
     * Call when the user click on the light button
     *
     * @return light timer
     */
    fun lightTimer(): Observable<Long>
}