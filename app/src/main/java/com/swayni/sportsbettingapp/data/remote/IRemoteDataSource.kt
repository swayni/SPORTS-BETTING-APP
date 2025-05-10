package com.swayni.sportsbettingapp.data.remote

import com.swayni.sportsbettingapp.core.data.model.ResultData
import com.swayni.sportsbettingapp.data.model.EventModel
import com.swayni.sportsbettingapp.data.model.EventsModel
import com.swayni.sportsbettingapp.data.model.SportLeagueModel
import kotlinx.coroutines.flow.Flow

interface IRemoteDataSource {

    suspend fun getSportLeague(): Flow<ResultData<List<SportLeagueModel>>>

    suspend fun getEvents(sport: String): Flow<ResultData<List<EventsModel>>>

    suspend fun getEventDetail(sport: String, eventId: String): Flow<ResultData<EventModel>>
}