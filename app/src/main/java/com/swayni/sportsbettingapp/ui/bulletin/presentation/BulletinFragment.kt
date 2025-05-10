package com.swayni.sportsbettingapp.ui.bulletin.presentation

import android.util.Log
import androidx.appcompat.widget.SearchView
import com.swayni.sportsbettingapp.R
import com.swayni.sportsbettingapp.core.base_ui.BaseFragment
import com.swayni.sportsbettingapp.core.enums.UiSuccessState
import com.swayni.sportsbettingapp.core.extensions.isVisible
import com.swayni.sportsbettingapp.core.extensions.observe
import com.swayni.sportsbettingapp.data.model.EventsModel
import com.swayni.sportsbettingapp.databinding.FragmentBulletinBinding
import com.swayni.sportsbettingapp.ui.bulletin.adapter.BulletinAdapter
import com.swayni.sportsbettingapp.ui.bulletin.viewmodel.BulletinViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BulletinFragment : BaseFragment<FragmentBulletinBinding, BulletinViewModel>(
    layoutId = R.layout.fragment_bulletin,
    viewModelClass = BulletinViewModel::class.java
) {

    private val sportKey by lazy { BulletinFragmentArgs.fromBundle(requireArguments()).sportKey }

    private val adapter by lazy { BulletinAdapter(::clickItemBulletin) }

    override fun initUI() {
        viewModel.getSportOdds(sportKey)
        viewModel.getOddCount()

        binding.bulletinAdapter.adapter = adapter

        binding.toolbar.setNavigationOnClickListener {
            viewModel.navigateBack()
        }
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
            viewModel.navigate(BulletinFragmentDirections.actionFragmentBulletinToFragmentOddsList())
            return@setOnMenuItemClickListener true
        }
    }

    override fun createdObserve() {
        observe(viewModel.uiStateFlow) {
            when (it.uiState) {
                UiSuccessState.ERROR -> {
                    Log.e("TAG", "ERROR: ${it.errorCode} ${it.errorMessage}")
                }
                UiSuccessState.SUCCESS -> {
                    it.bulletinList?.let { bulletinList->
                        adapter.updateList(bulletinList)
                    }
                    it.oddCount?.let { count ->
                        binding.count.isVisible(count > 0)
                        binding.count.text = "$count"
                    }
                }
                UiSuccessState.FAILURE -> {
                    Log.e("TAG", "FAILURE: ${it.errorCode} ${it.errorMessage}")
                }
                else -> {}
            }
        }
    }

    private fun clickItemBulletin(data : EventsModel){
        viewModel.navigate(BulletinFragmentDirections.actionFragmentBulletinToFragmentDetail(data.sportKey, data.id))
    }
}