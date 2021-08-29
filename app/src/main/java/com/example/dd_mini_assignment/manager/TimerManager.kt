package com.example.dd_mini_assignment.manager

import android.os.CountDownTimer
import androidx.databinding.BaseObservable
import androidx.databinding.ObservableField
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.dd_mini_assignment.extensions.tryCatch
import java.util.*

data class OnTick(
    var timeInMilliseconds: Long = 0
): BaseObservable() {

    fun set(time: Long) {
        timeInMilliseconds = time
        notifyChange()
    }
}

class TimerManager {
    private var currentTime = System.currentTimeMillis()
    private var timer: Timer? = null
    val onTick: OnTick = OnTick()

    fun run() {
        if (timer == null) {
            currentTime = getCurrentTimeMillis()
            timer = Timer().apply { start() }
        }
    }

    fun stop() {
        tryCatch {
            timer?.cancel()
            timer = null
        }
    }

    private fun getCurrentTimeMillis(): Long {
        return Calendar.getInstance().apply {
            val seconds = get(Calendar.SECOND)
            when {
                seconds >= 59 -> add(Calendar.MINUTE, 1)
                else -> add(Calendar.SECOND, 59 - seconds)
            }
        }.timeInMillis
    }

    inner class Timer : CountDownTimer(86400000, 1000) {
        override fun onTick(millisUntilFinished: Long) {
            currentTime = System.currentTimeMillis()
            onTick.set(currentTime)
        }

        override fun onFinish() {
            timer = null
        }
    }
}