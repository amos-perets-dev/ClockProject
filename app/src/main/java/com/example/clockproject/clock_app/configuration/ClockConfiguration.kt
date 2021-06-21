package com.example.clockproject.clock_app.configuration

import android.content.res.Resources
import com.example.clockproject.R

class ClockConfiguration(private val resources: Resources) : IClockConfiguration {
    override fun getCustomLongClickDuration() =
        resources.getInteger(R.integer.custom_long_click_long).toLong()
}