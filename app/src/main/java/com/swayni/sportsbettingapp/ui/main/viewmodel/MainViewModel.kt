package com.swayni.sportsbettingapp.ui.main.viewmodel

import com.swayni.sportsbettingapp.core.base_viewmodel.BaseViewModel
import com.swayni.sportsbettingapp.di.quality.MainDispatcher
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(@MainDispatcher private val dispatcher: CoroutineDispatcher) : BaseViewModel(dispatcher)