package com.swayni.sportsbettingapp.data.model

import com.google.gson.annotations.SerializedName

data class SportLeagueModel(
    @SerializedName("key") val key : String,
    @SerializedName("group") val group : String,
    @SerializedName("title") val title : String,
    @SerializedName("description") val description : String,
    @SerializedName("active") val active : Boolean,
    @SerializedName("has_outrights") val hasOutrights : Boolean
)