package com.swayni.sportsbettingapp.ui.main.presentation

import com.swayni.sportsbettingapp.R
import com.swayni.sportsbettingapp.core.base_ui.BaseActivity
import com.swayni.sportsbettingapp.databinding.ActivityMainBinding
import com.swayni.sportsbettingapp.ui.main.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding, MainViewModel>(
    layoutId = R.layout.activity_main,
    viewModelClass = MainViewModel::class.java
) {

    override fun init() {}
}