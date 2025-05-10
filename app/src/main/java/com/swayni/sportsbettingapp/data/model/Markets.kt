package com.swayni.sportsbettingapp.data.model

import com.google.gson.annotations.SerializedName


data class Markets (
  @SerializedName("key") var key: String? = null,
  @SerializedName("last_update") var lastUpdate : String? = null,
  @SerializedName("sid") var sid: String? = null,
  @SerializedName("outcomes") var outcomes: ArrayList<Outcomes> = arrayListOf()
)