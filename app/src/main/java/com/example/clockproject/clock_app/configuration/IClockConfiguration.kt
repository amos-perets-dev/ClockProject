package com.example.clockproject.clock_app.configuration

interface IClockConfiguration {
    /**
     * Get the duration time to long click
     *
     * @return [Long] duration time
     */
    fun getCustomLongClickDuration(): Long
}