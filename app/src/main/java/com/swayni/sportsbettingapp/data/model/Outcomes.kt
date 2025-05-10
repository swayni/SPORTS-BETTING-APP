package com.swayni.sportsbettingapp.data.model

import com.google.gson.annotations.SerializedName

data class Outcomes (
  @SerializedName("name") var name : String? = null,
  @SerializedName("price") var price : Double? = null,
  @SerializedName("point") var point : Double? = null,
  @SerializedName("sid") var sid : String? = null
)
