package com.swayni.sportsbettingapp.domain.model

data class SelectedModel(
    val id : String,
    val homeTeam : String? = null,
    val awayTeam : String? = null,
    val league : String? = null,
    val time : String? = null,
    val bookmaker : String? = null,
    val marketKey: String? = null,
    var name : String? = null,
    var price : Double? = null,
    var point : Double? = null,
    var sid : String? = null
)
