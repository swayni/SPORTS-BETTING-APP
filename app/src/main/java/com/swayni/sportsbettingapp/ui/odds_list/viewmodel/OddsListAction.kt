package com.swayni.sportsbettingapp.ui.odds_list.viewmodel

import com.swayni.sportsbettingapp.core.base_viewmodel.BaseAction
import com.swayni.sportsbettingapp.domain.model.SelectedModel

sealed class OddsListAction : BaseAction {
    data class Failed(val errorCode: Int, val message: String?) : OddsListAction()
    data class SelectedOddsSuccess(val selectedOdds: List<SelectedModel>) : OddsListAction()
    data object AllDelete : OddsListAction()
}