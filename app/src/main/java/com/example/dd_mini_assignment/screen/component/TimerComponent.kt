package com.example.dd_mini_assignment.screen.component

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.BindingAdapter
import com.example.dd_mini_assignment.R
import com.example.dd_mini_assignment.databinding.ComponentTimerBinding
import com.example.dd_mini_assignment.domain.model.OrderModel
import com.example.dd_mini_assignment.domain.model.OrderState
import com.example.dd_mini_assignment.extensions.toFormatted
import com.example.dd_mini_assignment.screen.order.OrderViewModel
import android.media.RingtoneManager
import androidx.databinding.Observable
import androidx.lifecycle.*
import com.example.dd_mini_assignment.manager.OnTick

import java.util.concurrent.TimeUnit


@BindingAdapter("app:bindingTimerComponent")
fun bindingTimerComponent(component: TimerComponent, order: OrderModel) {
    component.setOrderData(order)
}

class TimerComponent @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : ConstraintLayout(context, attrs) {

    private val binding = ComponentTimerBinding.inflate(LayoutInflater.from(context), this, true)
    private var viewModel: OrderViewModel? = null
    private var orderModel: OrderModel? = null
    private var componentCurrentState = OrderState.IN_COMING

    init {
        binding.button.setOnClickListener {
            orderModel?.let(::onButtonClicked)
        }
    }

    fun setOrderData(data: OrderModel) {
        orderModel = data
        orderModel?.let(::updateButton)

        binding.component.setOrder(data)
        binding.root.post {
            binding.index.text = String.format("#%s", data.id)
            binding.at.text = resources.getString(R.string.label_at_time_format, data.createdAt.toFormatted("hh:mm a"))
            updateTimeText(System.currentTimeMillis(), data)
        }
    }

    fun setViewModel(viewModel: OrderViewModel) {
        this.viewModel = viewModel
    }

    fun setLifeCycle(lifecycle: Lifecycle) {
        lifecycle.addObserver(object: LifecycleObserver {
            @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
            fun onStart() {
                println("observeTimer ${orderModel?.id} => ON_CREATE")
                viewModel?.timerManager()?.onTick?.addOnPropertyChangedCallback(observeTimer)
            }
            @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
            fun onDestroy() {
                println("observeTimer ${orderModel?.id} => ON_DESTROY")
                stopObserve()
            }
        })
    }

    private fun stopObserve() {
        viewModel?.timerManager()?.onTick?.removeOnPropertyChangedCallback(observeTimer)
    }

    private val observeTimer = object : Observable.OnPropertyChangedCallback() {
        override fun onPropertyChanged(observable: Observable, i: Int) {
            val time = (observable as? OnTick)?.timeInMilliseconds
            if (time != null) {
                orderModel?.let { updateTimes(time, it) }
            }
        }
    }

    private fun updateTimes(currentTime: Long, order: OrderModel) {
        when {
            currentTime > order.expiredAtInMilliseconds -> doOnOrderExpired(order)
            currentTime >= order.createdAtInMilliseconds && currentTime < order.expiredAtInMilliseconds ->
                doOnOrderInProcess(currentTime, order, currentTime >= order.alertedAtInMilliseconds)
        }
    }

    private fun updateTimeText(currentTime: Long, order: OrderModel) {
        val timeDiff = order.expiredAtInMilliseconds - currentTime
        val seconds = TimeUnit.SECONDS.convert(timeDiff, TimeUnit.MILLISECONDS).toInt()
        val min = TimeUnit.MINUTES.convert(timeDiff, TimeUnit.MILLISECONDS).toInt() + 1

        val time = when {
            seconds < 0 -> "0 s"
            seconds < 60 -> String.format("%d s", seconds)
            else -> String.format("%d mins", min)
        }
        binding.time.post { binding.time.text = time }
    }

    private fun updateProgress(currentTime: Long, order: OrderModel) {
//        val total = order.expiredAtInMilliseconds - order.createdAtInMilliseconds
//        val timeDiff = currentTime - order.createdAtInMilliseconds
//        val progress = (timeDiff/total.toDouble() * 100).toInt()

        binding.component.updateProgress(currentTime)
//        binding.progress.post { binding.progress.progress = progress }
    }

    private fun isSkipCurrentState(state: OrderState): Boolean {
        if (state == componentCurrentState) return true
        componentCurrentState = state
        return false
    }

    private fun updateButton(order: OrderModel) {
        if (isSkipCurrentState(order.currentState)) return

        when (order.currentState) {
            OrderState.IN_ORDER -> {
                binding.button.backgroundTintList = context.resources.getColorStateList(R.color.blue, context.theme)
                binding.button.setText(R.string.btn_accept)
            }
            OrderState.EXPIRED -> {
                binding.button.backgroundTintList = context.resources.getColorStateList(R.color.red, context.theme)
                binding.button.setText(R.string.btn_expired)
            }
        }
    }

    private fun onButtonClicked(order: OrderModel) {
        when (order.currentState) {
            OrderState.IN_ORDER -> {
                stopObserve()
                viewModel?.acceptOrder(order)
            }
            OrderState.EXPIRED -> {
                stopObserve()
                viewModel?.removeOrder(order)
            }
        }
    }

    private fun updateOrder(order: OrderModel) {
        if (isSkipCurrentState(order.currentState)) return
        viewModel?.doOnOrderUpdate(order)
    }

    private fun doOnOrderInProcess(currentTime: Long, order: OrderModel, isAlert: Boolean) {
        order.currentState = OrderState.IN_ORDER
        updateButton(order)
        updateOrder(order)
        updateProgress(currentTime, order)
        updateTimeText(currentTime, order)

        if (isAlert && !order.isPlayRingTone) {
            order.isPlayRingTone = true
            doOnOrderAlert()
        }
    }

    private fun doOnOrderExpired(order: OrderModel) {
        order.currentState = OrderState.EXPIRED
        stopObserve()
        updateButton(order)
        updateOrder(order)
        binding.component.timeOut()
        binding.time.post { binding.time.text = "0 s" }
        binding.progress.post { binding.progress.progress = 100 }
    }

    private fun doOnOrderAlert() {
        val notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        RingtoneManager.getRingtone(context, notification).play()
    }
}