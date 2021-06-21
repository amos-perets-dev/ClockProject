package com.example.clockproject.managers.state

import com.example.clockproject.data.ClockState

class HandlerStateManager() : IHandlerStateManager {

    private var indexCurrState = 0
    private var currState: ClockState = ClockState.Clock
    private val clockStateList = listOf(ClockState.Clock, ClockState.Alarm)

    override fun changeState(): ClockState {
        indexCurrState++
        val index = indexCurrState % clockStateList.size
        val clockState = clockStateList[index]
        clockState.currIndexClockState = index
        currState = clockState
        return clockState
    }

    override fun initState() = ClockState.Clock


}