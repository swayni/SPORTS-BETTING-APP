package com.swayni.sportsbettingapp.data

import com.swayni.sportsbettingapp.BuildConfig
import com.swayni.sportsbettingapp.data.model.EventModel
import com.swayni.sportsbettingapp.data.model.EventsModel
import com.swayni.sportsbettingapp.data.model.SportLeagueModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface Api {

    @GET("sports/")
    suspend fun getSportLeagueModel(
        @Query("apiKey") apiKey: String = BuildConfig.API_KEY,
        @Query("all") all: Boolean = true,
    ): Response<List<SportLeagueModel>>


    @GET("sports/{sport}/events")
    suspend fun getSportEvents(
        @Path("sport") sport: String,
        @Query("apiKey") apiKey: String = BuildConfig.API_KEY,
        @Query("dateFormat") dateFormat: String = "iso",
    ) : Response<List<EventsModel>>

    @GET("sports/{sport}/events/{eventId}/odds")
    suspend fun getSportEventDetail(
        @Path("sport") sport: String,
        @Path("eventId") eventId: String,
        @Query("apiKey") apiKey: String = BuildConfig.API_KEY,
        @Query("regions") regions: String = "us",
        @Query("oddsFormat") oddsFormat: String = "decimal",
        @Query("dateFormat") dateFormat: String = "iso",
        @Query("includeLinks") includeLinks: Boolean = false,
        @Query("includeSids") includeSids: Boolean = true,
        @Query("includeBetLimits") includeBetLimits: Boolean = false
    ) : Response<EventModel>
}