package com.example.clockproject.screens.clock_state

import android.os.Bundle
import android.view.View
import com.example.clockproject.util.view.CustomTextView
import com.example.clockproject.clock_app.base.BaseFragment
import com.example.clockproject.data.ClockState
import com.example.clockproject.data.time.TypesTimeData

abstract class BaseClockStateFragment : BaseFragment() {

    abstract fun getHoursTextView(): CustomTextView?

    abstract fun getMinutesTextView(): CustomTextView?

    abstract fun getSecondsTextView(): CustomTextView?

    private var textViewHours: CustomTextView? = null
    private var textViewMinutes: CustomTextView? = null
    private var textViewSeconds: CustomTextView? = null

    private val baseViewModel: ClockStateViewModel by lazy {
        getViewModel()
    }

    abstract fun getViewModel(): ClockStateViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        textViewHours = getHoursTextView()
        textViewMinutes = getMinutesTextView()
        textViewSeconds = getSecondsTextView()

        baseViewModel.timeTypeActive.observe(viewLifecycleOwner, this::setTimeTypeActive)
        baseViewModel.timeTypeInactive.observe(viewLifecycleOwner, this::setTimeTypeInActive)
        baseViewModel.typesTimeData.observe(viewLifecycleOwner, this::setTimes)

    }

    /**
     * Set the time type to active
     *
     * @param timeType - current type [ClockState.TimeType]
     */
    private fun setTimeTypeActive(timeType: ClockState.TimeType) {
        setTimeType(timeType, true)
    }

    /**
     * Set the time type to inactive
     *
     * @param timeType - prev type [ClockState.TimeType]
     */
    private fun setTimeTypeInActive(timeType: ClockState.TimeType) {
        setTimeType(timeType, false)
    }

    /**
     * Set the time
     *
     * @param [TypesTimeData] current data time
     */
    private fun setTimes(typesTimeData: TypesTimeData) {
        val hours = typesTimeData.hours.timeTextParse
        textViewHours?.text = hours

        val minutes = typesTimeData.minutes.timeTextParse
        textViewMinutes?.text = minutes

        val seconds = typesTimeData.seconds.timeTextParse
        textViewSeconds?.text = seconds
    }

    private fun setTimeType(timeType: ClockState.TimeType, isPulseView: Boolean) {

        val textView = when (timeType) {
            ClockState.TimeType.HOURS -> textViewHours
            ClockState.TimeType.MINUTES -> textViewMinutes
            ClockState.TimeType.SECONDS -> textViewSeconds
            ClockState.TimeType.NONE -> null
        }

        setStateView(isPulseView, textView)
    }

    private fun setStateView(isPulseView: Boolean, textView: CustomTextView?) {
        if (textView == null) return
        if (isPulseView) {
            textView.pulseView()
        } else {
            textView.stopPulse()
        }
    }
}
