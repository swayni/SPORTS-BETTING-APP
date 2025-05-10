package com.swayni.sportsbettingapp.data.model

import com.google.gson.annotations.SerializedName

data class EventsModel(
    @SerializedName("id") val id: String,
    @SerializedName("sport_key") val sportKey: String,
    @SerializedName("sport_title") val sportTitle: String,
    @SerializedName("commence_time") val commenceTime: String,
    @SerializedName("home_team") val homeTeam: String,
    @SerializedName("away_team") val awayTeam: String
)