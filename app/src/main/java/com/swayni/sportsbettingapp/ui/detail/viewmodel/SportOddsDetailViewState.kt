package com.swayni.sportsbettingapp.ui.detail.viewmodel

import com.swayni.sportsbettingapp.core.base_viewmodel.BaseViewState
import com.swayni.sportsbettingapp.core.enums.UiSuccessState
import com.swayni.sportsbettingapp.data.model.EventModel
import com.swayni.sportsbettingapp.domain.model.SelectedModel

data class SportOddsDetailViewState(
    val eventModel: EventModel?= null,
    val selectedMap: HashMap<String, SelectedModel>? = null,
    override val errorCode: Int? = null,
    override val errorMessage: String? = null,
    override val uiState: UiSuccessState = UiSuccessState.IDLE,
) : BaseViewState