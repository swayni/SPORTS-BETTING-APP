package com.swayni.sportsbettingapp.ui.menu.viewmodel

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.swayni.sportsbettingapp.core.base_viewmodel.SubBaseViewModel
import com.swayni.sportsbettingapp.core.data.model.ResultData
import com.swayni.sportsbettingapp.core.enums.UiSuccessState
import com.swayni.sportsbettingapp.data.model.SportLeagueModel
import com.swayni.sportsbettingapp.di.quality.DefaultDispatcher
import com.swayni.sportsbettingapp.di.quality.IoDispatcher
import com.swayni.sportsbettingapp.domain.usecase.GetOddsCountUseCase
import com.swayni.sportsbettingapp.domain.usecase.GetSportLeagueUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SportLeagueViewModel @Inject constructor(
    private val getSportLeagueUseCase: GetSportLeagueUseCase,
    private val getOddsCountUseCase: GetOddsCountUseCase,
    @IoDispatcher private val dispatcher: CoroutineDispatcher,
    @DefaultDispatcher private val defaultDispatcher: CoroutineDispatcher
): SubBaseViewModel<SportLeagueViewState, SportLeagueAction>(SportLeagueViewState(), defaultDispatcher) {

    private val fullList = mutableListOf<SportLeagueModel>()

    fun getOddsCount(){
        viewModelScope.launch (dispatcher) {
            getOddsCountUseCase.execute().collect{
                when(it){
                    is ResultData.Error -> sendAction(SportLeagueAction.Failed(it.errorInfo?.errorCode ?: 100, it.errorInfo?.errorMessage))
                    is ResultData.Success -> sendAction(SportLeagueAction.OddCountSuccess(it.data))
                }
            }
        }
    }

    fun getSportLeague() {
        viewModelScope.launch (dispatcher) {
            getSportLeagueUseCase.execute()
                .onStart { startLoading() }
                .onCompletion { stopLoading() }
                .collect {
                    Log.e("TAG", "getSportLeague: $it")
                    when (it) {
                        is ResultData.Success ->{
                            fullList.clear()
                            fullList.addAll(it.data)
                            sendAction(SportLeagueAction.SportLeagueSuccess(fullList))
                        }
                        is ResultData.Error -> sendAction(SportLeagueAction.Failed(it.errorInfo?.errorCode ?: 400, it.errorInfo?.errorMessage))
                    }
                }
        }
    }

    fun searchSportOdds(query: String? = null){
        viewModelScope.launch (defaultDispatcher) {
            if (query.isNullOrEmpty()){
                sendAction(SportLeagueAction.SportLeagueSuccess(fullList))
            }else {
                search(fullList, query.lowercase()).collect { result ->
                    sendAction(SportLeagueAction.SportLeagueSuccess(result))
                }
            }
        }
    }

    private suspend fun search(list: List<SportLeagueModel>, query: String): Flow<List<SportLeagueModel>> = flow {
        val result = mutableListOf<SportLeagueModel>()
        list.forEach {
            if (it.key.lowercase().contains(query) ||
                it.title.lowercase().contains(query) ||
                it.group.lowercase().contains(query)){
                    result.add(it)
            }
        }
        emit(result)
    }

    override fun onReduceState(viewAction: SportLeagueAction): SportLeagueViewState = when(viewAction){
        is SportLeagueAction.Failed -> {
            when(viewAction.errorCode){
                100, 400 ->{
                    state.copy(
                        uiState = UiSuccessState.FAILURE,
                        sportLeagueList = null,
                        oddCount = null,
                        errorCode = viewAction.errorCode,
                        errorMessage = viewAction.message
                    )
                }
                else ->{
                    state.copy(
                        uiState = UiSuccessState.ERROR,
                        sportLeagueList = null,
                        oddCount = null,
                        errorCode = viewAction.errorCode,
                        errorMessage = viewAction.message
                    )
                }
            }
        }
        is SportLeagueAction.SportLeagueSuccess -> state.copy(
            uiState = UiSuccessState.SUCCESS,
            sportLeagueList = viewAction.sportLeagueList,
            oddCount = null,
            errorCode = null,
            errorMessage = null
        )

        is SportLeagueAction.OddCountSuccess -> state.copy(
            uiState = UiSuccessState.SUCCESS,
            oddCount = viewAction.count,
            sportLeagueList = null,
            errorCode = null,
            errorMessage = null
        )
    }
}