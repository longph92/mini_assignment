package com.example.dd_mini_assignment.domain.usecase.base

import com.example.dd_mini_assignment.extensions.safeDispose
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.disposables.Disposable

abstract class UseCase {

    protected var lastDisposable: Disposable? = null
    protected val compositeDisposable = CompositeDisposable()

    fun disposeLast() {
        lastDisposable.safeDispose()
    }

    fun dispose() {
        compositeDisposable.clear()
    }
}