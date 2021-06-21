package com.example.clockproject.screens.clock_state.alarm

import android.os.Bundle
import android.view.View
import com.example.clockproject.util.view.CustomTextView
import com.example.clockproject.R
import com.example.clockproject.data.ClockState
import com.example.clockproject.screens.clock_state.BaseClockStateFragment
import com.example.clockproject.screens.clock_state.ClockStateViewModel
import kotlinx.android.synthetic.main.fragment_alarm.view.*
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf


class AlarmFragment() : BaseClockStateFragment() {

    private val clockStateViewModel: ClockStateViewModel by viewModel { parametersOf(ClockState.Alarm) }

    override fun getViewModel(): ClockStateViewModel = clockStateViewModel

    override fun getHoursTextView(): CustomTextView? = view?.text_view_hours_alarm

    override fun getMinutesTextView(): CustomTextView? = view?.text_view_minutes_alarm

    override fun getSecondsTextView(): CustomTextView? = null

    override fun getLayoutRes(): Int {
        return R.layout.fragment_alarm
    }
}