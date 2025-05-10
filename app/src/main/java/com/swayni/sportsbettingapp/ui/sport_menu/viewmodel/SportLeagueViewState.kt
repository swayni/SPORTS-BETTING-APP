package com.swayni.sportsbettingapp.ui.sport_menu.viewmodel

import com.swayni.sportsbettingapp.core.base_viewmodel.BaseViewState
import com.swayni.sportsbettingapp.core.enums.UiSuccessState
import com.swayni.sportsbettingapp.data.model.SportLeagueModel

data class SportLeagueViewState(
    val sportLeagueList: List<SportLeagueModel>? = null,
    val oddCount : Int ?= null,
    override val errorCode: Int? = null,
    override val errorMessage: String? = null,
    override val uiState: UiSuccessState = UiSuccessState.IDLE
) : BaseViewState
