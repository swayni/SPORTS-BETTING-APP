package com.swayni.sportsbettingapp.data.model

import com.google.gson.annotations.SerializedName


data class Bookmakers (
  @SerializedName("key") var key : String? = null,
  @SerializedName("title") var title : String? = null,
  @SerializedName("sid") var sid : String? = null,
  @SerializedName("markets") var markets : ArrayList<Markets> = arrayListOf()
)