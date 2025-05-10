package com.swayni.sportsbettingapp.data.repository

import com.swayni.sportsbettingapp.core.data.model.ResultData
import com.swayni.sportsbettingapp.data.model.EventModel
import com.swayni.sportsbettingapp.data.model.EventsModel
import com.swayni.sportsbettingapp.data.model.SportLeagueModel
import com.swayni.sportsbettingapp.domain.model.SelectedModel
import kotlinx.coroutines.flow.Flow

interface IRepository {

    suspend fun getSportLeague(): Flow<ResultData<List<SportLeagueModel>>>

    suspend fun getSportOdds(sport: String): Flow<ResultData<List<EventsModel>>>
    suspend fun getEventDetail(sport: String, eventId: String): Flow<ResultData<EventModel>>

    suspend fun getOddList() : Flow<ResultData<List<SelectedModel>>>
    suspend fun getOddListCount() : Flow<ResultData<Int>>
    suspend fun addOdd(selectedModel: SelectedModel) : Flow<ResultData<Boolean>>
    suspend fun deleteOdd(id : String) : Flow<ResultData<Boolean>>
    suspend fun updateOdd(selectedModel: SelectedModel) : Flow<ResultData<Boolean>>
    suspend fun deleteAllOdds() : Flow<ResultData<Boolean>>
}