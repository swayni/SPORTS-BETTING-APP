package com.swayni.sportsbettingapp.core.base_viewmodel

import com.swayni.sportsbettingapp.core.enums.UiSuccessState

interface BaseViewState {
    val errorCode : Int?
    val errorMessage: String?
    val uiState: UiSuccessState
}