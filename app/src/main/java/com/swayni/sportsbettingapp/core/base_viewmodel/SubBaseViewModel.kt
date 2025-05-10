package com.swayni.sportsbettingapp.core.base_viewmodel

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlin.properties.Delegates

abstract class SubBaseViewModel<ViewState : BaseViewState, ViewAction: BaseAction>(
    initialState: ViewState, dispatcher: CoroutineDispatcher) : BaseViewModel(dispatcher) {

    private val _uiStateFlow = MutableStateFlow(initialState)
    val uiStateFlow = _uiStateFlow.asStateFlow()

    var state by Delegates.observable(initialState) { _, old, new ->
        viewModelScope.launch {
            if (new != old) {
                _uiStateFlow.emit(new)
            }
        }
    }

    fun sendAction(viewAction: ViewAction) {
        state = onReduceState(viewAction)
    }

    abstract fun onReduceState(viewAction: ViewAction): ViewState
}