package com.example.clockproject.util.time

import androidx.core.text.isDigitsOnly
import com.example.clockproject.data.ClockState
import com.example.clockproject.data.time.TimeData
import java.text.SimpleDateFormat
import java.util.*

class TimeUtil : ITimeUtil {

    private fun getCurrHoursTime(time: Long): TimeData {
        val date = Date(time)

        val timeByPattern = getTimeByPattern("HH", date)
        val timeData = createTimeData(timeByPattern)
        return timeData
    }

    private fun getCurrMinutesTime(time: Long): TimeData {
        val date = Date(time)

        val timeByPattern = getTimeByPattern("mm", date)
        val timeData = createTimeData(timeByPattern)
        return timeData
    }

    private fun getCurrSecondsTime(time: Long): TimeData {
        val date = Date(time)

        val timeByPattern = getTimeByPattern("ss", date)
        val timeData = createTimeData(timeByPattern)
        return timeData
    }

    private fun createTimeData(timeByPattern: String): TimeData {
        return TimeData(if (timeByPattern.isDigitsOnly()) timeByPattern.toInt() else 0)
    }


    override fun getCurrPeriodTime(time: Long, timeType: ClockState.TimeType): TimeData {
        return when (timeType) {
            ClockState.TimeType.HOURS -> getCurrHoursTime(time)
            ClockState.TimeType.MINUTES -> getCurrMinutesTime(time)
            ClockState.TimeType.SECONDS -> getCurrSecondsTime(time)
            else -> TimeData()
        }
    }

    private fun getTimeByPattern(pattern: String, date: Date = Date()): String {
        val timeToFormat = date.time

        return SimpleDateFormat(pattern).format(timeToFormat)
    }

}