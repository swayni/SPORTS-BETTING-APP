package com.swayni.sportsbettingapp.ui.sport_menu.viewmodel

import com.swayni.sportsbettingapp.core.base_viewmodel.BaseAction
import com.swayni.sportsbettingapp.data.model.SportLeagueModel

sealed class SportLeagueAction : BaseAction {

    data class Failed(val errorCode: Int, val message: String?) : SportLeagueAction()
    data class SportLeagueSuccess(val sportLeagueList: List<SportLeagueModel>) : SportLeagueAction()
    data class OddCountSuccess(val count: Int) : SportLeagueAction()
}