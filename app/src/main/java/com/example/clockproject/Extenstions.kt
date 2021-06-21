package com.example.clockproject

import android.annotation.SuppressLint
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.MotionEvent
import android.view.View
import com.example.clockproject.clock_app.configuration.IClockConfiguration
import io.reactivex.Observable
import io.reactivex.disposables.Disposable
import org.koin.java.KoinJavaComponent.inject

private val configuration: IClockConfiguration by inject(IClockConfiguration::class.java)

fun <T> Observable<T>.subscribePro(): Disposable {
    return subscribe({}, Throwable::printStackTrace)
}

fun View.setOnCustomLongClickListener(longClick: () -> Unit, onClick: () -> Unit) {
    setOnTouchListener(object : View.OnTouchListener {

        private val handler = Handler(Looper.getMainLooper())
        private var prevTouch = 0L
        private var duration = configuration.getCustomLongClickDuration()

        @SuppressLint("ClickableViewAccessibility")
        override fun onTouch(v: View?, event: MotionEvent?): Boolean {
            if (event?.action == MotionEvent.ACTION_DOWN) {
                prevTouch = System.currentTimeMillis()
                handler.postDelayed(longClick::invoke, duration)
            } else if (event?.action == MotionEvent.ACTION_UP) {
                handler.removeCallbacksAndMessages(null)
                val touchTime = System.currentTimeMillis() - prevTouch
                if (touchTime <= 200) {
                    onClick.invoke()
                }
            }
            return true
        }
    })
}


