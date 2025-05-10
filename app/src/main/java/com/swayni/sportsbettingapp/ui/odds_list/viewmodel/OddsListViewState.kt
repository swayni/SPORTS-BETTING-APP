package com.swayni.sportsbettingapp.ui.odds_list.viewmodel

import com.swayni.sportsbettingapp.core.base_viewmodel.BaseViewState
import com.swayni.sportsbettingapp.core.enums.UiSuccessState
import com.swayni.sportsbettingapp.domain.model.SelectedModel

data class OddsListViewState(
    val allDelete: Boolean? = null,
    val selectedOdds: List<SelectedModel>? = null,
    override val errorCode: Int? = null,
    override val errorMessage: String? = null,
    override val uiState: UiSuccessState = UiSuccessState.IDLE
) : BaseViewState