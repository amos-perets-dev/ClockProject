package com.example.clockproject.clock_app.base

import androidx.lifecycle.ViewModel
import com.example.clockproject.repository.ITimeRepository
import io.reactivex.disposables.CompositeDisposable

open class BaseViewModel() : ViewModel() {
    protected val compositeDisposable = CompositeDisposable()

    override fun onCleared() {
        compositeDisposable.clear()
        super.onCleared()
    }
}