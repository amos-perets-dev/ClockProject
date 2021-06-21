package com.example.clockproject.managers.settings

import com.example.clockproject.data.ClockState
import io.reactivex.Observable

interface ISettingsManager {
    /**
     * Get if the alarm active or not
     *
     * @return [Observable][Boolean]
     */
    fun isActiveAlarm(): Observable<Boolean>

    /**
     * Call when the user click on set(edit) mode
     *
     * @param clockState - [ClockState] current clock state
     */
    fun setEditMode(clockState: ClockState)

    /**
     * Call when the user move among the times types
     */
    fun setTimeType(state: ClockState)

    /**
     * Get the current time type need to be edit mode
     *
     * @return [Observable][ClockState.TimeType]
     */
    fun getTimeType(): Observable<ClockState.TimeType>

    fun onClickUp(state: ClockState)

    fun onClickDown(state: ClockState)

    fun dispose()
}