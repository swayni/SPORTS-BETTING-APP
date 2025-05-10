package com.swayni.sportsbettingapp.ui.sport_menu.presentation

import android.util.Log
import androidx.appcompat.widget.SearchView
import com.swayni.sportsbettingapp.R
import com.swayni.sportsbettingapp.core.base_ui.BaseFragment
import com.swayni.sportsbettingapp.core.enums.UiSuccessState
import com.swayni.sportsbettingapp.core.extensions.isVisible
import com.swayni.sportsbettingapp.core.extensions.observe
import com.swayni.sportsbettingapp.databinding.FragmentMenuBinding
import com.swayni.sportsbettingapp.ui.sport_menu.adapter.SportLeagueAdapter
import com.swayni.sportsbettingapp.ui.sport_menu.viewmodel.SportLeagueViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SportLeagueFragment : BaseFragment<FragmentMenuBinding, SportLeagueViewModel>(
    layoutId = R.layout.fragment_menu,
    viewModelClass = SportLeagueViewModel::class.java
) {

    private val adapter by lazy { SportLeagueAdapter(::onItemClick) }

    override fun initUI() {
        viewModel.getOddsCount()
        viewModel.getSportLeague()

        binding.recyclerview.adapter = adapter

        (binding.toolbar.menu.findItem(R.id.search).actionView as SearchView).let { searchView->
            searchView.queryHint = getString(R.string.search)

            searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
                override fun onQueryTextSubmit(query: String?): Boolean {
                    return true
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    viewModel.searchSportOdds(newText)
                    return true
                }
            })
        }


        binding.toolbar.menu.findItem(R.id.basket).setOnMenuItemClickListener {
            viewModel.navigate(SportLeagueFragmentDirections.actionFragmentSportLeagueToFragmentOddsList())
            return@setOnMenuItemClickListener true
        }

        binding.bottomMenu.setOnItemSelectedListener {
            when(it.itemId){
                R.id.soccer -> {
                    viewModel.searchSportOdds("soccer")
                }
                R.id.basketball ->{
                    viewModel.searchSportOdds("basketball")
                }
                R.id.tennis ->{
                    viewModel.searchSportOdds("tennis")
                }
                R.id.all ->{
                    viewModel.searchSportOdds()
                }
            }
            true
        }
    }

    override fun createdObserve() {
        observe(viewModel.uiStateFlow){
            when(it.uiState){
                UiSuccessState.SUCCESS -> {
                    it.sportLeagueList?.let { leagueList->
                        adapter.updateList(leagueList)
                    }
                    it.oddCount?.let { count ->
                        binding.count.isVisible(count > 0)
                        binding.count.text = "$count"
                    }
                }
                UiSuccessState.FAILURE -> {
                    Log.d("TAG", "createdObserve: ${it.errorMessage}")
                }
                else->{}
            }
        }
    }

    private fun  onItemClick(sportKey : String){
        viewModel.navigate(SportLeagueFragmentDirections.actionFragmentSportLeagueToFragmentBulletin(sportKey))
    }
}