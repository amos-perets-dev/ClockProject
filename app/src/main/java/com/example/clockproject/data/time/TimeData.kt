package com.example.clockproject.data.time

class TimeData(var timeValue: Int = 0) {

    val timeTextParse: String
        get() {
            return if (timeValue < 10) {
                if (timeValue == 0) {
                    "00"
                } else {
                    "0$timeValue"
                }
            } else {
                timeValue.toString()
            }
        }
}