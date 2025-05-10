package com.swayni.sportsbettingapp.domain.usecase

import com.swayni.sportsbettingapp.core.data.model.ResultData
import com.swayni.sportsbettingapp.core.data.usecase.BaseUseCase
import com.swayni.sportsbettingapp.data.repository.IRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class DeleteAllOddsUseCase @Inject constructor(private val repository: IRepository) : BaseUseCase() {

    suspend fun execute() : Flow<ResultData<Boolean>> = invoke { repository.deleteAllOdds() }

}