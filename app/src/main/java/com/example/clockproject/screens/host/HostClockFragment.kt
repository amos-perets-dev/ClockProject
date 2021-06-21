package com.example.clockproject.screens.host

import android.content.Context
import android.os.Build
import android.os.Bundle
import android.os.VibrationEffect
import android.os.Vibrator
import android.view.View
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.example.clockproject.R
import com.example.clockproject.clock_app.base.BaseFragment
import com.example.clockproject.data.ClockState
import com.example.clockproject.screens.clock_state.alarm.AlarmFragment
import com.example.clockproject.screens.clock_state.clock.ClockFragment
import com.example.clockproject.setOnCustomLongClickListener
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_main_clock.view.*
import org.koin.android.viewmodel.ext.android.viewModel


/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class HostClockFragment : BaseFragment() {

    private val viewModel by viewModel<HostClockViewModel>()
    private var viewPagerClocks: ViewPager2? = null
    private var textViewModeTitle: AppCompatTextView? = null
    private var alarmIcon: AppCompatImageView? = null
    private var buttonSet: AppCompatButton? = null
    private var timeBorder: View? = null

    override fun getLayoutRes(): Int = R.layout.fragment_main_clock

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewPagerClocks = view.view_pager_clocks
        textViewModeTitle = view.text_view_mode_title
        alarmIcon = view.image_view_alarm_icon
        buttonSet = view.button_set
        timeBorder = view.time_border
        val buttonMode = view.button_mode
        val buttonUp = view.button_up
        val buttonDown = view.button_down
        val buttonLight = view.button_light

        initViewPager()

        buttonMode.setOnClickListener { viewModel.onClickChangeMode() }

        buttonLight.setOnClickListener { viewModel.onClickLight() }

        buttonUp.setOnClickListener { viewModel.onClickUp() }

        buttonDown.setOnClickListener { viewModel.onClickDown() }

        viewModel.clockState.observe(viewLifecycleOwner, this::setState)
        viewModel.alarmIconState.observe(viewLifecycleOwner, this::setAlarmIcon)
        viewModel.isAlarm.observe(viewLifecycleOwner, this::setIsAlarm)
        viewModel.isShowLight.observe(viewLifecycleOwner, this::setLight)

        buttonSet?.setOnCustomLongClickListener(
            {
                viewModel.onLongClickSet()
            }, {
                viewModel.onClickSet()
            })

    }

    /**
     * Set the background color
     *
     * @param isShowLight [Boolean]- is show light[R.drawable.time_border_light] or [R.drawable.time_border]
     */
    private fun setLight(isShowLight: Boolean) {
        timeBorder?.setBackgroundResource(
            if (isShowLight) {
                R.drawable.time_border_light
            } else {
                R.drawable.time_border
            }
        )
    }

    /**
     * Set the alarm state
     */
    private fun setIsAlarm(isAlarm: Boolean) {
        if (isAlarm.not()) return
        val vibrator = requireContext().getSystemService(Context.VIBRATOR_SERVICE) as Vibrator?

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            vibrator?.vibrate(VibrationEffect.createOneShot(500, VibrationEffect.DEFAULT_AMPLITUDE))
        } else {
            //deprecated in API 26
            vibrator?.vibrate(500)
        }

        val snackbar = view?.let {
            Snackbar.make(
                it,
                "***ALARM***",
                Snackbar.LENGTH_LONG
            )
        }
        snackbar?.show()
    }

    private fun setState(state: ClockState) {
        textViewModeTitle?.text = getString(state.title)
        viewPagerClocks?.currentItem = state.currIndexClockState
    }

    private fun setAlarmIcon(isShow: Boolean) {
        alarmIcon?.visibility = if (isShow) View.VISIBLE else View.INVISIBLE
    }

    private fun initViewPager() {
        viewPagerClocks?.adapter = PagerAdapter(
            this, listOf(
                ClockFragment(),
                AlarmFragment()
            )
        )
    }

}