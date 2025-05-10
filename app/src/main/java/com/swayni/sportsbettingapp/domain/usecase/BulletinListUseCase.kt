package com.swayni.sportsbettingapp.domain.usecase

import com.swayni.sportsbettingapp.core.data.usecase.BaseUseCase
import com.swayni.sportsbettingapp.data.repository.IRepository
import javax.inject.Inject

class BulletinListUseCase @Inject constructor(private val repository: IRepository) : BaseUseCase() {

    suspend fun execute(sport: String) = invoke { repository.getSportOdds(sport = sport) }

}