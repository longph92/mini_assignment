package com.example.dd_mini_assignment.extensions

import android.content.Context
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat
import androidx.databinding.ObservableArrayList
import androidx.lifecycle.*
import androidx.lifecycle.Observer
import io.reactivex.rxjava3.disposables.Disposable
import kotlinx.coroutines.flow.collect
import java.text.SimpleDateFormat
import java.util.*

fun Disposable?.safeDispose() {
    if (this?.isDisposed == false) {
        this.dispose()
    }
}

fun <T> ArrayList<T>.safeClear() {
    if (isNotEmpty()) {
        clear()
    }
}

fun <T> ObservableArrayList<T>.addAllNew(data: List<T>) {
    if (isNotEmpty()) {
        clear()
    }
    this.addAll(data)
}

fun Context.color(@ColorRes id: Int): Int {
    return ContextCompat.getColor(this, id)
}

fun <T> LifecycleOwner.observe(liveData: LiveData<T>, action: (t: T) -> Unit) {
    liveData.observe(this, Observer { it?.let { t -> action(t) } })
}

suspend inline fun <T> LiveData<T>.observeState(crossinline coroutine: (T) -> Unit) {
    this@observeState.distinctUntilChanged().asFlow().collect {
        coroutine(it)
    }
}

inline fun tryCatch(crossinline func: () -> Unit) {
    try {
        func()
    } catch (ex: java.lang.Exception) {
        ex.printStackTrace()
    }
}

fun Date.toFormatted(pattern: String): String {
    val format = SimpleDateFormat(pattern, Locale.US)
    return format.format(this)
}

fun Date.addMinutes(minutes: Int): Date {
    return Calendar.getInstance().apply {
        time = this@addMinutes
        add(Calendar.MINUTE, minutes)
    }.time
}