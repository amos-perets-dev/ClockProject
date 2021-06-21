package com.example.clockproject.util.time

import com.example.clockproject.data.ClockState
import com.example.clockproject.data.time.TimeData

interface ITimeUtil {

    fun getCurrPeriodTime(time: Long, timeType: ClockState.TimeType): TimeData

}