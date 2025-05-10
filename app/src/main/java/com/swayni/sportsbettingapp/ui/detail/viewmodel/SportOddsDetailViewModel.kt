package com.swayni.sportsbettingapp.ui.detail.viewmodel

import androidx.lifecycle.viewModelScope
import com.swayni.sportsbettingapp.core.base_viewmodel.SubBaseViewModel
import com.swayni.sportsbettingapp.core.data.model.ResultData
import com.swayni.sportsbettingapp.core.enums.UiSuccessState
import com.swayni.sportsbettingapp.di.quality.DefaultDispatcher
import com.swayni.sportsbettingapp.di.quality.IoDispatcher
import com.swayni.sportsbettingapp.domain.model.SelectedModel
import com.swayni.sportsbettingapp.domain.usecase.AddOddUseCase
import com.swayni.sportsbettingapp.domain.usecase.DeleteOddsUseCase
import com.swayni.sportsbettingapp.domain.usecase.GetEventDetailUseCase
import com.swayni.sportsbettingapp.domain.usecase.GetOddsUseCase
import com.swayni.sportsbettingapp.domain.usecase.UpdateOddUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SportOddsDetailViewModel @Inject constructor(
    private val getEventDetailUseCase : GetEventDetailUseCase,
    private val getOddsUseCase: GetOddsUseCase,
    private val addOddUseCase: AddOddUseCase,
    private val deleteOddUseCase: DeleteOddsUseCase,
    private val updateOddUseCase: UpdateOddUseCase,
    @IoDispatcher private val dispatcher: CoroutineDispatcher,
    @DefaultDispatcher private val defaultDispatcher: CoroutineDispatcher
) : SubBaseViewModel<SportOddsDetailViewState, SportOddsDetailAction>(SportOddsDetailViewState(), defaultDispatcher) {

    fun getEventDetail(sportKey: String, eventId: String){
        viewModelScope.launch (dispatcher) {
            getEventDetailUseCase.execute(sportKey, eventId)
                .onStart {
                    startLoading()
                }
                .onCompletion {
                    stopLoading()
                }.collect{
                    when(it){
                        is ResultData.Error -> sendAction(SportOddsDetailAction.Failed(errorCode = it.errorInfo?.errorCode ?: 400, it.errorInfo?.errorMessage))
                        is ResultData.Success -> sendAction(SportOddsDetailAction.Success(it.data))
                    }
                }
        }
    }

    fun getOdds(){
        viewModelScope.launch (dispatcher) {
            getOddsUseCase.execute().onStart {
                startLoading()
            }.onCompletion {
                stopLoading()
            }.collect{
                when(it){
                    is ResultData.Error -> sendAction(SportOddsDetailAction.Failed(errorCode = it.errorInfo?.errorCode ?: 400, it.errorInfo?.errorMessage))
                    is ResultData.Success -> {
                        val map = HashMap<String, SelectedModel>()
                        it.data.forEach { selectedModel ->
                            map[selectedModel.id] = selectedModel
                        }
                        sendAction(SportOddsDetailAction.SelectedSuccess(map))
                    }
                }
            }
        }
    }

    fun addOdd(selectedModel: SelectedModel){
        viewModelScope.launch (dispatcher) {
            addOddUseCase.execute(selectedModel).onStart {
                startLoading()
            }.collect{
                when(it){
                    is ResultData.Error -> sendAction(SportOddsDetailAction.Failed(errorCode = it.errorInfo?.errorCode ?: 400, it.errorInfo?.errorMessage))
                    is ResultData.Success -> getOdds()
                }
            }
        }
    }

    fun deleteOdd(selectedModel: SelectedModel){
        viewModelScope.launch (dispatcher) {
            deleteOddUseCase.execute(selectedModel.id).onStart {
                startLoading()
            }.collect{
                when(it){
                    is ResultData.Error -> sendAction(SportOddsDetailAction.Failed(errorCode = it.errorInfo?.errorCode ?: 400, it.errorInfo?.errorMessage))
                    is ResultData.Success -> getOdds()
                }
            }
        }
    }

    fun updateOdd(selectedModel: SelectedModel){
        viewModelScope.launch (dispatcher) {
            updateOddUseCase.execute(selectedModel).onStart {
                startLoading()
            }.collect{
                when(it){
                    is ResultData.Error -> sendAction(SportOddsDetailAction.Failed(errorCode = it.errorInfo?.errorCode ?: 400, it.errorInfo?.errorMessage))
                    is ResultData.Success -> getOdds()
                }
            }
        }
    }

    override fun onReduceState(viewAction: SportOddsDetailAction): SportOddsDetailViewState = when(viewAction){
        is SportOddsDetailAction.Failed -> {
            when(viewAction.errorCode){
                400 -> state.copy(
                    uiState = UiSuccessState.FAILURE,
                    selectedMap = null,
                    eventModel = null,
                    errorCode = 400,
                    errorMessage = viewAction.message,
                )
                else -> state.copy(
                    uiState = UiSuccessState.ERROR,
                    selectedMap = null,
                    errorCode = viewAction.errorCode,
                    errorMessage = viewAction.message,
                    eventModel = null)
            }
        }

        is SportOddsDetailAction.Success -> state.copy(
            uiState = UiSuccessState.SUCCESS,
            eventModel = viewAction.eventDetail,
            selectedMap = null,
            errorCode = null,
            errorMessage = null
        )

        is SportOddsDetailAction.SelectedSuccess ->  state.copy(
            uiState = UiSuccessState.SUCCESS,
            eventModel = null,
            selectedMap = viewAction.selectedList,
            errorCode = null,
            errorMessage = null
        )
    }
}