package com.swayni.sportsbettingapp.data.repository

import com.swayni.sportsbettingapp.core.data.model.ResultData
import com.swayni.sportsbettingapp.data.local.ILocalDataSource
import com.swayni.sportsbettingapp.data.model.EventModel
import com.swayni.sportsbettingapp.data.model.EventsModel
import com.swayni.sportsbettingapp.data.model.SportLeagueModel
import com.swayni.sportsbettingapp.data.remote.IRemoteDataSource
import com.swayni.sportsbettingapp.domain.model.SelectedModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class Repository @Inject constructor(
    private val remoteDataSource: IRemoteDataSource,
    private val localDataSource: ILocalDataSource) : IRepository  {

    override suspend fun getSportLeague(): Flow<ResultData<List<SportLeagueModel>>> = remoteDataSource.getSportLeague()

    override suspend fun getSportOdds(
        sport: String
    ): Flow<ResultData<List<EventsModel>>> = remoteDataSource.getEvents(sport = sport)

    override suspend fun getEventDetail(
        sport: String,
        eventId: String
    ): Flow<ResultData<EventModel>> = remoteDataSource.getEventDetail(sport = sport, eventId = eventId)

    override suspend fun getOddList(): Flow<ResultData<List<SelectedModel>>> = localDataSource.getOddList()

    override suspend fun getOddListCount(): Flow<ResultData<Int>> = localDataSource.getOddListCount()

    override suspend fun addOdd(selectedModel: SelectedModel): Flow<ResultData<Boolean>> = localDataSource.addOdd(selectedModel)

    override suspend fun deleteOdd(id: String): Flow<ResultData<Boolean>>  = localDataSource.deleteOdd(id)

    override suspend fun updateOdd(selectedModel: SelectedModel): Flow<ResultData<Boolean>> = localDataSource.updateOdd(selectedModel)

    override suspend fun deleteAllOdds(): Flow<ResultData<Boolean>> = localDataSource.deleteAllOdds()

}