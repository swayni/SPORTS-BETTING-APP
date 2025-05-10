package com.swayni.sportsbettingapp.domain.usecase

import com.swayni.sportsbettingapp.core.data.usecase.BaseUseCase
import com.swayni.sportsbettingapp.data.repository.IRepository
import javax.inject.Inject

class GetEventDetailUseCase @Inject constructor(private val repository: IRepository) : BaseUseCase() {

    suspend fun execute(sport: String, eventId: String) = invoke { repository.getEventDetail(sport = sport, eventId = eventId) }

}