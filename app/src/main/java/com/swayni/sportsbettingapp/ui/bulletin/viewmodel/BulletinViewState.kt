package com.swayni.sportsbettingapp.ui.bulletin.viewmodel

import com.swayni.sportsbettingapp.core.base_viewmodel.BaseViewState
import com.swayni.sportsbettingapp.core.enums.UiSuccessState
import com.swayni.sportsbettingapp.data.model.EventsModel

data class BulletinViewState(
    val bulletinList: List<EventsModel>? = null,
    val oddCount: Int? = null,
    override val errorCode: Int? = null,
    override val errorMessage: String? = null,
    override val uiState: UiSuccessState = UiSuccessState.IDLE
) : BaseViewState