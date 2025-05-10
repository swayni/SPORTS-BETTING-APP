package com.swayni.sportsbettingapp.core.data.model

import com.swayni.sportsbettingapp.data.model.ErrorInfo

sealed class ResultData<out T : Any>{
    data class Success<out T : Any>(val data: T) : ResultData<T>()
    data class Error(val errorInfo: ErrorInfo?) : ResultData<Nothing>()
}