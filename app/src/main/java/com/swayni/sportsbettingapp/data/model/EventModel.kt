package com.swayni.sportsbettingapp.data.model

import com.google.gson.annotations.SerializedName


data class EventModel (
  @SerializedName("id"            ) var id           : String,
  @SerializedName("sport_key"     ) var sportKey     : String,
  @SerializedName("sport_title"   ) var sportTitle   : String,
  @SerializedName("commence_time" ) var commenceTime : String,
  @SerializedName("home_team"     ) var homeTeam     : String,
  @SerializedName("away_team"     ) var awayTeam     : String,
  @SerializedName("bookmakers"    ) var bookmakers   : ArrayList<Bookmakers> = arrayListOf()
)