package com.swayni.sportsbettingapp.core.data.usecase

abstract class BaseUseCase {

    suspend operator fun <R> invoke(block: suspend () -> R): R = block()

}