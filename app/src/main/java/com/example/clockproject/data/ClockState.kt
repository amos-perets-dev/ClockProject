package com.example.clockproject.data

import androidx.annotation.StringRes
import com.example.clockproject.R
import com.example.clockproject.data.time.TypesTimeData

sealed class ClockState {

    @StringRes
    open val title: Int = R.string.clock_title_state_clock
    open var currIndexClockState: Int = 0
    open val timeTypesList: List<TimeType> = arrayListOf()
    open var typesTimeData: TypesTimeData = TypesTimeData()

    object Clock : ClockState() {
        override val timeTypesList: List<TimeType>
            get() = listOf(
                TimeType.HOURS,
                TimeType.MINUTES,
                TimeType.SECONDS
            )
        override val title: Int get() = R.string.clock_title_state_clock
    }

    object Alarm : ClockState() {
        override val timeTypesList: List<TimeType>
            get() = listOf(
                TimeType.HOURS,
                TimeType.MINUTES
            )
        override val title: Int get() = R.string.clock_title_state_alarm

    }

    enum class TimeType {
        HOURS,
        MINUTES,
        SECONDS,
        NONE
    }

}
