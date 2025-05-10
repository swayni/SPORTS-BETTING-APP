package com.swayni.sportsbettingapp.ui.odds_list.presentation

import android.util.Log
import com.swayni.sportsbettingapp.R
import com.swayni.sportsbettingapp.core.base_ui.BaseFragment
import com.swayni.sportsbettingapp.core.enums.UiSuccessState
import com.swayni.sportsbettingapp.core.extensions.isVisible
import com.swayni.sportsbettingapp.core.extensions.observe
import com.swayni.sportsbettingapp.databinding.FragmentOddsListBinding
import com.swayni.sportsbettingapp.domain.model.SelectedModel
import com.swayni.sportsbettingapp.ui.odds_list.adapter.OddsListAdapter
import com.swayni.sportsbettingapp.ui.odds_list.viewmodel.OddsLisViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OddsListFragment : BaseFragment<FragmentOddsListBinding, OddsLisViewModel>(
    layoutId = R.layout.fragment_odds_list,
    viewModelClass = OddsLisViewModel::class.java
){

    private val adapter by lazy { OddsListAdapter(::deleteItem) }

    override fun initUI() {
        binding.recyclerview.adapter = adapter


        binding.toolbar.setNavigationOnClickListener {
            viewModel.navigateBack()
        }

        binding.toolbar.menu.findItem(R.id.delete).setOnMenuItemClickListener {
            viewModel.allDeleteOdds()
            return@setOnMenuItemClickListener true
        }

        viewModel.getOdds()
    }

    override fun createdObserve() {
        observe(viewModel.uiStateFlow){
            when(it.uiState){
                UiSuccessState.ERROR -> {
                    Log.e("TAG", "ERROR: ${it.errorCode} ${it.errorMessage}")
                }
                UiSuccessState.SUCCESS -> {
                    it.selectedOdds?.let { data ->
                        var rate = 1.0
                        data.forEach { odd ->
                            rate *= odd.price ?: 1.0
                        }
                        binding.bottomContainer.isVisible(rate > 1)
                        binding.rate.text = "$rate"
                        adapter.updateList(data)
                    }

                    it.allDelete?.let {
                        binding.rate.text = "0"
                        adapter.allDelete()
                    }
                }
                UiSuccessState.FAILURE -> {
                    Log.e("TAG", "FAILURE: ${it.errorCode} ${it.errorMessage}")
                }
                else ->{}
            }
        }
    }

    private fun deleteItem(selectedModel: SelectedModel) {
        viewModel.deleteOdds(selectedModel.id)
    }
}