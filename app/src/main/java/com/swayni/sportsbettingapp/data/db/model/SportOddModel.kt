package com.swayni.sportsbettingapp.data.db.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.swayni.sportsbettingapp.domain.model.SelectedModel

@Entity
data class SportOddModel (
    @PrimaryKey var id: String = "",
    @ColumnInfo("home_team") val homeTeam: String? = null,
    @ColumnInfo("away_team") val awayTeam: String? = null,
    @ColumnInfo("league") var league: String? = null,
    @ColumnInfo("time") var time: String? = null,
    @ColumnInfo("bookmaker_name") var bookmaker: String? = null,
    @ColumnInfo("market_key") var marketKey: String? = null,
    @ColumnInfo("outcomes_name") var outcomesName: String? = null,
    @ColumnInfo("outcomes_price") var outcomesPrice: Double? = null,
    @ColumnInfo("outcomes_point") var outcomesPoint: Double? = null,
    @ColumnInfo("outcomes_pid") var outcomesPid: String? = null,
) {

    fun toSelectedModel() : SelectedModel {
        return SelectedModel(
            id = id,
            homeTeam = homeTeam,
            awayTeam = awayTeam,
            league = league,
            time = time,
            bookmaker = bookmaker,
            marketKey = marketKey,
            name = outcomesName,
            price = outcomesPrice,
            point = outcomesPoint,
            sid = outcomesPid
        )
    }
}