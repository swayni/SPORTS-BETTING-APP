package com.swayni.sportsbettingapp.domain.model

import com.swayni.sportsbettingapp.data.model.Markets

data class DetailOddsModel(
    val bookmaker: String,
    val markets: Markets
)
