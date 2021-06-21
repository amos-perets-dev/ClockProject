package com.example.clockproject.util.view

import android.content.Context
import android.util.AttributeSet
import android.view.View
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import java.util.concurrent.TimeUnit

class CustomTextView(context: Context, attrs: AttributeSet?) : androidx.appcompat.widget.AppCompatTextView(context, attrs) {
    private var disposablePulseView: Disposable? = null

    fun pulseView(){
        disposablePulseView = Observable.interval(100, TimeUnit.MILLISECONDS, AndroidSchedulers.mainThread())
            .doOnNext {
                visibility = if (visibility == View.VISIBLE) View.INVISIBLE else View.VISIBLE
            }
            .subscribe()
    }

    fun stopPulse(){
        disposablePulseView?.dispose()
        visibility = View.VISIBLE
    }

}