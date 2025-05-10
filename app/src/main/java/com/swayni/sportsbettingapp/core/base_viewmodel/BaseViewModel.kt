package com.swayni.sportsbettingapp.core.base_viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavDirections
import com.swayni.sportsbettingapp.core.enums.NavigationAction
import com.swayni.sportsbettingapp.core.extensions.Event
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

abstract class BaseViewModel(private val dispatcher: CoroutineDispatcher): ViewModel() {

    private val _uiLoading = MutableStateFlow(false)
    val uiLoading = _uiLoading.asStateFlow()

    private val _navigation = MutableSharedFlow<Event<NavigationAction>>()
    val navigation = _navigation.asSharedFlow()

    fun navigate(navDirections: NavDirections) {
        viewModelScope.launch (dispatcher) {
            _navigation.emit(Event(NavigationAction.ToDirection(navDirections)))
        }
    }

    fun navigateBack() {
        viewModelScope.launch (dispatcher) {
            _navigation.emit(Event(NavigationAction.Back))
        }
    }

    fun startLoading(){
        viewModelScope.launch (dispatcher) {
            _uiLoading.emit(true)
        }
    }

    fun stopLoading(){
        viewModelScope.launch (dispatcher) {
            _uiLoading.emit(false)
        }
    }
}