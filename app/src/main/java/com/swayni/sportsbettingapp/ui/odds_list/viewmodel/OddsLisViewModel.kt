package com.swayni.sportsbettingapp.ui.odds_list.viewmodel

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.swayni.sportsbettingapp.core.base_viewmodel.SubBaseViewModel
import com.swayni.sportsbettingapp.core.data.model.ResultData
import com.swayni.sportsbettingapp.core.enums.UiSuccessState
import com.swayni.sportsbettingapp.di.quality.DefaultDispatcher
import com.swayni.sportsbettingapp.di.quality.IoDispatcher
import com.swayni.sportsbettingapp.domain.usecase.DeleteAllOddsUseCase
import com.swayni.sportsbettingapp.domain.usecase.DeleteOddsUseCase
import com.swayni.sportsbettingapp.domain.usecase.GetOddsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OddsLisViewModel @Inject constructor(
    private val getOddListUseCase: GetOddsUseCase,
    private val deleteAllOddsUseCase: DeleteAllOddsUseCase,
    private val deleteOddsUseCase: DeleteOddsUseCase,
    @IoDispatcher private val dispatcher: CoroutineDispatcher,
    @DefaultDispatcher private val defaultDispatcher: CoroutineDispatcher
) : SubBaseViewModel<OddsListViewState, OddsListAction>(OddsListViewState(), defaultDispatcher) {

    fun getOdds(){
        viewModelScope.launch (dispatcher) {
            getOddListUseCase.execute()
                .onCompletion{
                    stopLoading()
                }
                .collect{
                    when(it){
                        is ResultData.Error -> sendAction(OddsListAction.Failed(errorCode = it.errorInfo?.errorCode ?: 100, it.errorInfo?.errorMessage))
                        is ResultData.Success -> {
                            sendAction(OddsListAction.SelectedOddsSuccess(it.data))
                        }
                    }
                }
        }
    }

    fun deleteOdds(id : String) {
        viewModelScope.launch (dispatcher) {
            deleteOddsUseCase.execute(id).onStart {
                startLoading()
            }.collect{
                when(it){
                    is ResultData.Error -> sendAction(OddsListAction.Failed(errorCode = it.errorInfo?.errorCode ?: 100, it.errorInfo?.errorMessage))
                    is ResultData.Success -> getOdds()
                }
            }
        }
    }

    fun allDeleteOdds(){
        viewModelScope.launch (dispatcher) {
            deleteAllOddsUseCase.execute().onStart {
                startLoading()
            }.onCompletion {
                stopLoading()
            }.collect{
                when(it){
                    is ResultData.Error -> sendAction(OddsListAction.Failed(errorCode = it.errorInfo?.errorCode ?: 100, it.errorInfo?.errorMessage))
                    is ResultData.Success -> sendAction(OddsListAction.AllDelete)
                }
            }
        }
    }

    override fun onReduceState(viewAction: OddsListAction): OddsListViewState = when(viewAction){
        OddsListAction.AllDelete ->
            state.copy(
                uiState = UiSuccessState.SUCCESS,
                allDelete = true,
                selectedOdds = null,
                errorCode = null,
                errorMessage = null
            )
        is OddsListAction.Failed ->
            when(viewAction.errorCode){
                400 -> state.copy(
                    selectedOdds = null,
                    allDelete = null,
                    uiState = UiSuccessState.FAILURE,
                    errorCode = 400,
                    errorMessage = viewAction.message
                )
                else -> state.copy(
                    selectedOdds = null,
                    allDelete = null,
                    uiState = UiSuccessState.ERROR,
                    errorCode = viewAction.errorCode,
                    errorMessage = viewAction.message)
            }
        is OddsListAction.SelectedOddsSuccess ->
            state.copy(
                uiState = UiSuccessState.SUCCESS,
                allDelete = null,
                selectedOdds = viewAction.selectedOdds,
                errorCode = null,
                errorMessage = null
            )
    }
}