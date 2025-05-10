package com.swayni.sportsbettingapp.ui.detail.viewmodel

import com.swayni.sportsbettingapp.core.base_viewmodel.BaseAction
import com.swayni.sportsbettingapp.data.model.EventModel
import com.swayni.sportsbettingapp.domain.model.SelectedModel

sealed class SportOddsDetailAction : BaseAction {
    data class Failed(val errorCode: Int, val message: String?) : SportOddsDetailAction()
    data class Success(val eventDetail: EventModel) : SportOddsDetailAction()
    data class SelectedSuccess(val selectedList: HashMap<String, SelectedModel>) : SportOddsDetailAction()
}