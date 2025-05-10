package com.swayni.sportsbettingapp.data.local

import com.swayni.sportsbettingapp.core.data.model.ResultData
import com.swayni.sportsbettingapp.data.db.dao.SportOddDao
import com.swayni.sportsbettingapp.data.db.model.SportOddModel
import com.swayni.sportsbettingapp.data.model.ErrorInfo
import com.swayni.sportsbettingapp.domain.model.SelectedModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class LocalDataSource @Inject constructor(private val sportOddDao: SportOddDao) : ILocalDataSource {

    override suspend fun getOddList(): Flow<ResultData<List<SelectedModel>>> = flow {
        try {
            val result = mutableListOf<SelectedModel>()
            sportOddDao.getAll().forEach {
                result.add(it.toSelectedModel())
            }
            emit(ResultData.Success(result))
        }catch (e : Exception){
            emit(ResultData.Error(ErrorInfo(100, e.message.toString())))
        }
    }

    override suspend fun getOddListCount(): Flow<ResultData<Int>> = flow {
        try {
            val result = mutableListOf<SelectedModel>()
            sportOddDao.getAll().forEach {
                result.add(it.toSelectedModel())
            }
            emit(ResultData.Success(result.size))
        }catch (e : Exception){
            emit(ResultData.Error(ErrorInfo(100, e.message.toString())))
        }
    }

    override suspend fun addOdd(selectedModel: SelectedModel): Flow<ResultData<Boolean>> = flow {
        try {
            sportOddDao.insert(
                SportOddModel(
                    id = selectedModel.id,
                    homeTeam = selectedModel.homeTeam,
                    awayTeam = selectedModel.awayTeam,
                    league = selectedModel.league,
                    time = selectedModel.time,
                    bookmaker = selectedModel.bookmaker,
                    marketKey = selectedModel.marketKey,
                    outcomesName = selectedModel.name,
                    outcomesPrice = selectedModel.price,
                    outcomesPoint = selectedModel.point,
                    outcomesPid = selectedModel.sid
                )
            )
            emit(ResultData.Success(true))
        }catch (e : Exception){
            emit(ResultData.Error(ErrorInfo(100, e.message.toString())))
        }
    }

    override suspend fun deleteOdd(id: String): Flow<ResultData<Boolean>> = flow {
        try {
            sportOddDao.deleteById(id)
            emit(ResultData.Success(true))
        }catch (e : Exception){
            emit(ResultData.Error(ErrorInfo(100, e.message.toString())))
        }
    }

    override suspend fun updateOdd(selectedModel: SelectedModel): Flow<ResultData<Boolean>> = flow{
        try {
            sportOddDao.update(
                SportOddModel(
                    id = selectedModel.id,
                    homeTeam = selectedModel.homeTeam,
                    awayTeam = selectedModel.awayTeam,
                    league = selectedModel.league,
                    time = selectedModel.time,
                    bookmaker = selectedModel.bookmaker,
                    marketKey = selectedModel.marketKey,
                    outcomesName = selectedModel.name,
                    outcomesPrice = selectedModel.price,
                    outcomesPoint = selectedModel.point,
                    outcomesPid = selectedModel.sid
                )
            )
            emit(ResultData.Success(true))
        }catch (e : Exception){
            emit(ResultData.Error(ErrorInfo(100, e.message.toString())))
        }
    }

    override suspend fun deleteAllOdds(): Flow<ResultData<Boolean>> = flow {
        try {
            sportOddDao.deleteAll()
            emit(ResultData.Success(true))
        }catch (e : Exception){
            emit(ResultData.Error(ErrorInfo(100, e.message.toString())))
        }
    }
}