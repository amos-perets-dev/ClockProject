package com.example.clockproject.managers.state

import com.example.clockproject.data.ClockState

interface IHandlerStateManager {
    /**
     * Call when the user click on mode button, change mode
     *
     * @return [ClockState] next state
     */
    fun changeState(): ClockState

    /**
     * Init the clock state
     *
     * @return [ClockState] current state
     */
    fun initState(): ClockState
}