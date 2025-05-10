package com.swayni.sportsbettingapp.data.local

import com.swayni.sportsbettingapp.core.data.model.ResultData
import com.swayni.sportsbettingapp.domain.model.SelectedModel
import kotlinx.coroutines.flow.Flow

interface ILocalDataSource {

    suspend fun getOddList() : Flow<ResultData<List<SelectedModel>>>

    suspend fun getOddListCount() : Flow<ResultData<Int>>

    suspend fun addOdd(selectedModel: SelectedModel) : Flow<ResultData<Boolean>>

    suspend fun deleteOdd(id : String) : Flow<ResultData<Boolean>>

    suspend fun updateOdd(selectedModel: SelectedModel) : Flow<ResultData<Boolean>>

    suspend fun deleteAllOdds() : Flow<ResultData<Boolean>>
}