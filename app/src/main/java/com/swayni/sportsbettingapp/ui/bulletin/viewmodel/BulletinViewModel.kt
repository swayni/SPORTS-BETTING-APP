package com.swayni.sportsbettingapp.ui.bulletin.viewmodel

import androidx.lifecycle.viewModelScope
import com.swayni.sportsbettingapp.core.base_viewmodel.SubBaseViewModel
import com.swayni.sportsbettingapp.core.data.model.ResultData
import com.swayni.sportsbettingapp.core.enums.UiSuccessState
import com.swayni.sportsbettingapp.data.model.EventsModel
import com.swayni.sportsbettingapp.di.quality.DefaultDispatcher
import com.swayni.sportsbettingapp.di.quality.IoDispatcher
import com.swayni.sportsbettingapp.domain.usecase.BulletinListUseCase
import com.swayni.sportsbettingapp.domain.usecase.GetOddsCountUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BulletinViewModel @Inject constructor(
    private val bulletinListUseCase: BulletinListUseCase,
    private val getOddsCountUseCase: GetOddsCountUseCase,
    @IoDispatcher private val dispatcher: CoroutineDispatcher,
    @DefaultDispatcher private val defaultDispatcher: CoroutineDispatcher
) : SubBaseViewModel<BulletinViewState, BulletinAction>(BulletinViewState(), defaultDispatcher) {

    private val fullList = mutableListOf<EventsModel>()

    fun getSportOdds(sportKey : String) {
        viewModelScope.launch(dispatcher){
            bulletinListUseCase.execute(sportKey)
                .onStart {
                    startLoading()
                }
                .onCompletion {
                    stopLoading()
                }
                .collect{
                    when(it){
                        is ResultData.Error -> sendAction(BulletinAction.Failed(errorCode = it.errorInfo?.errorCode ?: 400, it.errorInfo?.errorMessage))
                        is ResultData.Success -> {
                            val termList = it.data.sortedBy { sportOdds -> sportOdds.commenceTime }
                            fullList.clear()
                            fullList.addAll(termList)
                            sendAction(BulletinAction.BulletinListSuccess(fullList))
                        }
                    }
                }
        }
    }

    fun getOddCount(){
        viewModelScope.launch(dispatcher) {
            getOddsCountUseCase.execute().collect{
                when(it){
                    is ResultData.Error -> sendAction(BulletinAction.Failed(errorCode = it.errorInfo?.errorCode ?: 100, it.errorInfo?.errorMessage))
                    is ResultData.Success -> sendAction(BulletinAction.OddCountSuccess(it.data))
                }
            }
        }
    }

    fun searchSportOdds(query: String?){
        viewModelScope.launch (defaultDispatcher) {
            if (query.isNullOrEmpty()){
                sendAction(BulletinAction.BulletinListSuccess(fullList))
            }else {
                search(fullList, query.lowercase()).collect { result ->
                    sendAction(BulletinAction.BulletinListSuccess(result))
                }
            }
        }
    }

    private suspend fun search(list: List<EventsModel>, query: String): Flow<List<EventsModel>> = flow {
        val result = mutableListOf<EventsModel>()
        list.forEach {
            if (it.homeTeam.lowercase().contains(query) || it.awayTeam.lowercase().contains(query)){
                result.add(it)
            }
        }
        emit(result)
    }

    override fun onReduceState(viewAction: BulletinAction): BulletinViewState = when(viewAction){
        is BulletinAction.BulletinListSuccess -> {
            state.copy(
                bulletinList = viewAction.bulletinList,
                oddCount = null,
                uiState = UiSuccessState.SUCCESS,
                errorCode = null,
                errorMessage = null)
        }
        is BulletinAction.Failed -> {
            when(viewAction.errorCode){
                100, 400 -> state.copy(
                    bulletinList = null,
                    oddCount = null,
                    uiState = UiSuccessState.FAILURE,
                    errorCode = viewAction.errorCode,
                    errorMessage = viewAction.message
                )
                else -> state.copy(
                    bulletinList = null,
                    oddCount = null,
                    uiState = UiSuccessState.ERROR,
                    errorCode = viewAction.errorCode,
                    errorMessage = viewAction.message)
            }
        }

        is BulletinAction.OddCountSuccess -> state.copy(
            bulletinList = null,
            oddCount = viewAction.count,
            uiState = UiSuccessState.SUCCESS,
            errorCode = null,
            errorMessage = null)
    }
}