package com.swayni.sportsbettingapp.ui.bulletin.viewmodel

import com.swayni.sportsbettingapp.core.base_viewmodel.BaseAction
import com.swayni.sportsbettingapp.data.model.EventsModel

sealed class BulletinAction : BaseAction{

    data class Failed(val errorCode: Int, val message: String?) : BulletinAction()
    data class BulletinListSuccess(val bulletinList: List<EventsModel>) : BulletinAction()
    data class OddCountSuccess(val count: Int) : BulletinAction()
}