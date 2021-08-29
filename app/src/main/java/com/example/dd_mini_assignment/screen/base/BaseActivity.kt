package com.example.dd_mini_assignment.screen.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.databinding.ViewDataBinding

abstract class BaseActivity<V : BaseViewModel, B : ViewDataBinding> : AppCompatActivity() {

    protected abstract val viewModel: V
    protected lateinit var binding: B

    abstract fun getViewBinding(): B

    open fun initialize() {}
    open fun observe() {}
    open fun viewBinding() {}

    override fun onCreate(savedInstanceState: Bundle?) {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        super.onCreate(savedInstanceState)
        binding = getViewBinding().apply { lifecycleOwner = this@BaseActivity }
        setContentView(binding.root)
        initialize()
        observe()
        viewBinding()
    }
}