package com.example.clockproject.clock_app

import android.content.Context
import com.example.clockproject.R
import com.example.clockproject.clock_app.configuration.ClockConfiguration
import com.example.clockproject.clock_app.configuration.IClockConfiguration
import com.example.clockproject.data.ClockState
import com.example.clockproject.managers.clock.ClockManager
import com.example.clockproject.managers.clock.IClockManager
import com.example.clockproject.managers.settings.ISettingsManager
import com.example.clockproject.managers.settings.SettingsManager
import com.example.clockproject.managers.state.HandlerStateManager
import com.example.clockproject.managers.state.IHandlerStateManager
import com.example.clockproject.repository.ITimeRepository
import com.example.clockproject.repository.TimeRepository
import com.example.clockproject.screens.clock_state.ClockStateViewModel
import com.example.clockproject.screens.host.HostClockViewModel
import com.example.clockproject.util.time.ITimeUtil
import com.example.clockproject.util.time.TimeUtil
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.core.module.Module
import org.koin.dsl.module
import java.util.*

class ClockModules {
    fun createModules(context: Context): List<Module> {

        val appModules = createAppModules(context)
        val homePageModule = createHomePageModule()

        return listOf(appModules, homePageModule)
    }

    private fun createAppModules(context: Context): Module {
        val resources = context.resources

        return module {
            factory<ITimeUtil> {
                TimeUtil()
            }

            single<ITimeRepository> {
                val timeLong = Date().time
                val clockStateClock = initClockStateClock(timeLong, get())

                val defaultGapAlarmLong =
                    (resources.getInteger(R.integer.default_gap_alarm) * (1000 * 60)) + timeLong
                val clockStateAlarm = initClockStateAlarm(defaultGapAlarmLong, get())

                val snoozeTime =
                    resources.getInteger(R.integer.snooze_time)
                TimeRepository(clockStateClock, clockStateAlarm, snoozeTime)
            }
            single<ISettingsManager> {
                SettingsManager(get())
            }
            single<IClockManager> {
                val alarmDuration =
                    (resources.getInteger(R.integer.alarm_duration))
                val lightDuration =
                    (resources.getInteger(R.integer.light_duration))
                ClockManager(get(), get(), alarmDuration,lightDuration)
            }
            single<IHandlerStateManager> {
                HandlerStateManager()
            }
            factory<IClockConfiguration> {
                ClockConfiguration(resources)
            }
        }
    }

    private fun initClockStateClock(timeLong: Long, timeUtil: ITimeUtil): ClockState.Clock {

        val clock = ClockState.Clock
        val clockTypesData = clock.typesTimeData
        clockTypesData.hours = timeUtil.getCurrPeriodTime(timeLong, ClockState.TimeType.HOURS)
        clockTypesData.minutes = timeUtil.getCurrPeriodTime(timeLong, ClockState.TimeType.MINUTES)
        clockTypesData.seconds = timeUtil.getCurrPeriodTime(timeLong, ClockState.TimeType.SECONDS)
        return clock
    }

    private fun initClockStateAlarm(defaultGapAlarm: Long, timeUtil: ITimeUtil): ClockState.Alarm {
        val alarm = ClockState.Alarm
        val alarmTypesData = alarm.typesTimeData
        alarmTypesData.hours =
            timeUtil.getCurrPeriodTime(defaultGapAlarm, ClockState.TimeType.HOURS)
        alarmTypesData.minutes =
            timeUtil.getCurrPeriodTime(defaultGapAlarm, ClockState.TimeType.MINUTES)
        return alarm
    }

    private fun createHomePageModule(): Module {
        return module {
            viewModel { (state: ClockState) -> ClockStateViewModel(get(), state, get()) }
            viewModel { HostClockViewModel(get(), get(), get()) }
        }
    }
}
