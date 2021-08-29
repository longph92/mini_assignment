package com.example.dd_mini_assignment.screen.base

import androidx.databinding.ViewDataBinding
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LifecycleRegistry
import androidx.recyclerview.widget.RecyclerView

abstract class BaseLifeCycleViewHolder(private val binding: ViewDataBinding): RecyclerView.ViewHolder(binding.root), LifecycleOwner {

    private val lifecycleRegistry = LifecycleRegistry(this)

    init {
        binding.lifecycleOwner = this
        lifecycleRegistry.currentState = Lifecycle.State.INITIALIZED
    }

    fun onStart() {
//        lifecycleRegistry.currentState = Lifecycle.State.CREATED
        lifecycleRegistry.handleLifecycleEvent(Lifecycle.Event.ON_RESUME)
    }

    fun onStop() {
//        lifecycleRegistry.currentState = Lifecycle.State.DESTROYED
        lifecycleRegistry.handleLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    }

    override fun getLifecycle(): Lifecycle {
        return lifecycleRegistry
    }

}