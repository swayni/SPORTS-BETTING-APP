package com.swayni.sportsbettingapp.domain.usecase

import com.swayni.sportsbettingapp.core.data.model.ResultData
import com.swayni.sportsbettingapp.core.data.usecase.BaseUseCase
import com.swayni.sportsbettingapp.data.repository.IRepository
import com.swayni.sportsbettingapp.domain.model.SelectedModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class AddOddUseCase @Inject constructor(private val repository: IRepository) : BaseUseCase() {

    suspend fun execute(selectedModel: SelectedModel): Flow<ResultData<Boolean>> = invoke { repository.addOdd(selectedModel) }

}