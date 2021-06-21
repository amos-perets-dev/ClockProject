package com.example.clockproject.screens.clock_state.clock

import android.os.Bundle
import android.view.View
import com.example.clockproject.util.view.CustomTextView
import com.example.clockproject.R
import com.example.clockproject.data.ClockState
import com.example.clockproject.screens.clock_state.BaseClockStateFragment
import com.example.clockproject.screens.clock_state.ClockStateViewModel
import kotlinx.android.synthetic.main.fragment_clock.view.*
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf


/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class ClockFragment : BaseClockStateFragment() {

    private val clockStateViewModel: ClockStateViewModel by viewModel { parametersOf(ClockState.Clock) }
//
    override fun getViewModel(): ClockStateViewModel = clockStateViewModel

    override fun getHoursTextView(): CustomTextView? = view?.text_view_hours_clock

    override fun getMinutesTextView(): CustomTextView? = view?.text_view_minutes_clock

    override fun getSecondsTextView(): CustomTextView? = view?.text_view_seconds_clock

    override fun getLayoutRes(): Int {
        return R.layout.fragment_clock
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        viewModel.timeTypeActive.observe(viewLifecycleOwner, this::setTimeTypeActive)
//        viewModel.timeTypeInactive.observe(viewLifecycleOwner, this::setTimeTypeInActive)

    }

}