package com.example.clockproject.data.time

class TypesTimeData(
    var hours: TimeData = TimeData(),
    var minutes: TimeData = TimeData(),
    var seconds: TimeData = TimeData()
) {

    fun addHours(value: Int) {
        hours.timeValue = (24 + value) % 24
    }

    fun addMinutes(value: Int): Boolean {
        minutes.timeValue = (60 + value) % 60
        return minutes.timeValue == 0
    }

    fun addSeconds(value: Int): Boolean {
        seconds.timeValue = (60 + value) % 60
        return seconds.timeValue == 0
    }

    override fun toString(): String {
        return "${hours.timeTextParse}${minutes.timeTextParse}${seconds.timeTextParse}"
    }

}