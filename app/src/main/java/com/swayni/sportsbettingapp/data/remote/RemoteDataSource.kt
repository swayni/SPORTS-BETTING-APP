package com.swayni.sportsbettingapp.data.remote

import com.swayni.sportsbettingapp.core.data.model.ResultData
import com.swayni.sportsbettingapp.data.Api
import com.swayni.sportsbettingapp.data.model.ErrorInfo
import com.swayni.sportsbettingapp.data.model.EventModel
import com.swayni.sportsbettingapp.data.model.EventsModel
import com.swayni.sportsbettingapp.data.model.SportLeagueModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class RemoteDataSource @Inject constructor(private  val api: Api) : IRemoteDataSource {


    override suspend fun getSportLeague(): Flow<ResultData<List<SportLeagueModel>>> =
        flow {
        try {
            val response = api.getSportLeagueModel()
            when(response.code()){
                200 ->{
                    response.body()?.let {
                        emit(ResultData.Success(it))
                    }
                }
                else ->{
                    emit(ResultData.Error(errorInfo = ErrorInfo(response.code(), response.message())))
                }
            }
        }catch (e : Exception){
            emit(ResultData.Error(errorInfo = ErrorInfo(400, e.message.toString())))
        }
    }

    override suspend fun getEvents(
        sport: String
    ): Flow<ResultData<List<EventsModel>>> = flow {
        try {
            val response = api.getSportEvents(sport)
            when (response.code()){
                200 ->{
                    response.body()?.let {
                        emit(ResultData.Success(it))
                    }
                }
                else -> {
                    emit(ResultData.Error(errorInfo = ErrorInfo(response.code(), response.message())))
                }
            }
        }catch (e : Exception){
            emit(ResultData.Error(errorInfo = ErrorInfo(400, e.message.toString())))
        }
    }

    override suspend fun getEventDetail(
        sport: String,
        eventId: String
    ): Flow<ResultData<EventModel>>  = flow {
        try {
            val response = api.getSportEventDetail(sport, eventId)
            when (response.code()){
                200 ->{
                    response.body()?.let {
                        emit(ResultData.Success(it))
                    }
                }
                else -> {
                    emit(ResultData.Error(errorInfo = ErrorInfo(response.code(), response.message())))
                }
            }
        }catch (e : Exception){
            emit(ResultData.Error(errorInfo = ErrorInfo(400, e.message.toString())))
        }
    }

}