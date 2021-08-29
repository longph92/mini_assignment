package com.example.dd_mini_assignment.domain.usecase.base

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers

abstract class SingleUseCase<T> : UseCase() {

    internal abstract fun buildUseCaseSingle(): Single<T>

    fun execute(
        onSuccess: ((t: T) -> Unit),
        onError: ((t: Throwable) -> Unit)? = null,
        onFinished: () -> Unit = {}
    ) {
        disposeLast()
        lastDisposable = buildUseCaseSingle()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doAfterTerminate(onFinished)
            .subscribe(onSuccess, {
                onError?.invoke(it)
            })
            .also {
                compositeDisposable.add(it)
            }
    }

    fun forceExecute(
        onSuccess: ((t: T) -> Unit),
        onError: ((t: Throwable) -> Unit)? = null,
        onFinished: () -> Unit = {}
    ) {
        lastDisposable = buildUseCaseSingle()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doAfterTerminate(onFinished)
            .subscribe(onSuccess, {
                onError?.invoke(it)
            })
            .also {
                compositeDisposable.add(it)
            }
    }
}