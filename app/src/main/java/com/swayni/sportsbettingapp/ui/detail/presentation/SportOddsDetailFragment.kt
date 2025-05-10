package com.swayni.sportsbettingapp.ui.detail.presentation

import android.util.Log
import com.swayni.sportsbettingapp.R
import com.swayni.sportsbettingapp.core.base_ui.BaseFragment
import com.swayni.sportsbettingapp.core.enums.SelectedAction
import com.swayni.sportsbettingapp.core.enums.UiSuccessState
import com.swayni.sportsbettingapp.core.extensions.isVisible
import com.swayni.sportsbettingapp.core.extensions.loadImage
import com.swayni.sportsbettingapp.core.extensions.observe
import com.swayni.sportsbettingapp.core.utils.apiToUiString
import com.swayni.sportsbettingapp.databinding.FragmentSportOddsDetailBinding
import com.swayni.sportsbettingapp.domain.model.SelectedModel
import com.swayni.sportsbettingapp.ui.detail.adapter.DetailOddsAdapter
import com.swayni.sportsbettingapp.ui.detail.viewmodel.SportOddsDetailViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SportOddsDetailFragment : BaseFragment<FragmentSportOddsDetailBinding, SportOddsDetailViewModel>(
    layoutId = R.layout.fragment_sport_odds_detail,
    viewModelClass = SportOddsDetailViewModel::class.java
) {
    private val sportKey by lazy { SportOddsDetailFragmentArgs.fromBundle(requireArguments()).sportKey }
    private val eventId by lazy { SportOddsDetailFragmentArgs.fromBundle(requireArguments()).eventId }

    private val adapter by lazy { DetailOddsAdapter(::selectedOdd) }

    override fun initUI() {
        viewModel.getOdds()
        viewModel.getEventDetail(sportKey, eventId)

        binding.backButton.setOnClickListener {
            viewModel.navigateBack()
        }

        binding.recyclerview.adapter = adapter

        binding.btnBasket.setOnClickListener {
            viewModel.navigate(SportOddsDetailFragmentDirections.actionFragmentSportOddsDetailToFragmentOddsList())
        }
    }

    override fun createdObserve() {
        observe(viewModel.uiStateFlow){
            when(it.uiState){
                UiSuccessState.SUCCESS -> {
                    it.eventModel?.let { event ->
                        setImage(event.sportKey)
                        binding.homeName.text = event.homeTeam
                        binding.awayName.text = event.awayTeam
                        binding.time.text = apiToUiString(event.commenceTime, true)

                        adapter.updateSportOdds(event)
                    }
                    it.selectedMap?.let { selectedMap ->
                        adapter.updateSelectedMap(selectedMap)
                        binding.count.isVisible(selectedMap.size > 0)
                        binding.count.text = "${selectedMap.size}"
                    }
                }
                UiSuccessState.ERROR -> {
                    Log.e("TAG", "ERROR: ${it.errorCode} ${it.errorMessage}")
                }
                UiSuccessState.FAILURE -> {
                    Log.e("TAG", "FAILURE: ${it.errorCode} ${it.errorMessage}")
                }
                else -> {}
            }
        }
    }

    private fun selectedOdd(data : SelectedModel?, action : SelectedAction){
        data?.let {
            when(action){
                SelectedAction.ADD -> viewModel.addOdd(it)
                SelectedAction.UPDATE -> viewModel.updateOdd(it)
                SelectedAction.DELETE -> viewModel.deleteOdd(it)
            }
        }
    }

    private fun setImage(sportKey : String){
        binding.background.loadImage("https://www.freeimages.com/photo/football-1437517")
        if (sportKey.contains("soccer")){
            binding.homeImage.loadImage(R.drawable.ic_soccer)
            binding.awayImage.loadImage(R.drawable.ic_soccer)
        }
        else if (sportKey.contains("basketball")){
            binding.homeImage.loadImage(R.drawable.ic_basketball)
            binding.awayImage.loadImage(R.drawable.ic_basketball)
        }
        else if (sportKey.contains("tennis")){
            binding.homeImage.loadImage(R.drawable.ic_tennis)
            binding.awayImage.loadImage(R.drawable.ic_tennis)
        }
        else if(sportKey.contains("football")){
            binding.homeImage.loadImage(R.drawable.ic_sports_football)
            binding.awayImage.loadImage(R.drawable.ic_sports_football)
        }
        else if (sportKey.contains("cricket")){
            binding.homeImage.loadImage(R.drawable.ic_sports_cricket)
            binding.awayImage.loadImage(R.drawable.ic_sports_cricket)
        }
        else if (sportKey.contains("golf")){
            binding.homeImage.loadImage(R.drawable.ic_sports_golf)
            binding.awayImage.loadImage(R.drawable.ic_sports_golf)
        }
        else if (sportKey.contains("hockey")){
            binding.homeImage.loadImage(R.drawable.ic_sports_hockey)
            binding.awayImage.loadImage(R.drawable.ic_sports_hockey)
        }
        else {
            binding.homeImage.loadImage(R.drawable.ic_sports_baseball)
            binding.awayImage.loadImage(R.drawable.ic_sports_baseball)
        }
    }
}