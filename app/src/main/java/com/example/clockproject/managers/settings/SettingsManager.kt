package com.example.clockproject.managers.settings

import com.example.clockproject.data.ClockState
import com.example.clockproject.repository.ITimeRepository
import io.reactivex.Observable
import io.reactivex.Scheduler
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.BehaviorSubject

class SettingsManager(private val timeRepository: ITimeRepository) : ISettingsManager {

    private var isActiveAlarm = BehaviorSubject.createDefault(false)
    private var timeType = BehaviorSubject.create<ClockState.TimeType>()

    private var isEditMode = false
    private var currIndexTimeType = 0

    /**
     * Call when the user deactivate the alarm
     */
    private fun clearActiveAlarm() {
        isActiveAlarm.onNext(false)
    }

    /**
     * Call when the user activate the alarm
     */
    private fun addActiveAlarm() {
        isActiveAlarm.onNext(true)
    }

    override fun setEditMode(clockState: ClockState) {
        this.isEditMode = isEditMode.not()
        if (this.isEditMode) {
            setTimeType(clockState)
        } else {
            clearEditMode()
        }
    }

    override fun setTimeType(state: ClockState) {
        if (this.isEditMode) {
            val timeTypesListSize = state.timeTypesList.size
            val timeTypes =
                state.timeTypesList[currIndexTimeType % timeTypesListSize]
            timeType.onNext(timeTypes)
            currIndexTimeType++
        }
    }

    /**
     * Clear the edit mode(clock)
     */
    private fun clearEditMode() {
        currIndexTimeType = 0
        this.isEditMode = false
        timeType.onNext(ClockState.TimeType.NONE)
    }

    override fun isActiveAlarm(): Observable<Boolean> {
        return isActiveAlarm
            .hide()
            .subscribeOn(Schedulers.io())
            .distinctUntilChanged()
    }

    override fun getTimeType(): Observable<ClockState.TimeType> {
        return timeType
            .hide()
            .subscribeOn(Schedulers.io())
            .distinctUntilChanged()
    }

    override fun dispose() {
        clearEditMode()
    }

    override fun onClickUp(state: ClockState) {
        if (isEditMode) {
            val value = timeType.value
            if (value != null) {
                timeRepository.setTime(value, state, true)
            }
        } else {
            if (state is ClockState.Alarm) {
                addActiveAlarm()
            }
        }
    }

    override fun onClickDown(state: ClockState) {
        if (isEditMode) {
            val value = timeType.value
            if (value != null) {
                timeRepository.setTime(value, state, false)
            }
        } else {
            if (state is ClockState.Alarm) {
                clearActiveAlarm()
            }
        }
    }
}