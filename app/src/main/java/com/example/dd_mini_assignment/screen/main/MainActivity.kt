package com.example.dd_mini_assignment.screen.main

import com.example.dd_mini_assignment.databinding.ActivityMainBinding
import com.example.dd_mini_assignment.screen.base.BaseActivity
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity: BaseActivity<MainViewModel, ActivityMainBinding>() {

    override val viewModel: MainViewModel by viewModel()

    override fun getViewBinding(): ActivityMainBinding = ActivityMainBinding.inflate(layoutInflater)
}