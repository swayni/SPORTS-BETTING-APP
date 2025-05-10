package com.swayni.sportsbettingapp.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.swayni.sportsbettingapp.data.db.model.SportOddModel

@Dao
interface SportOddDao {

    @Query("SELECT * FROM SportOddModel")
    fun getAll(): List<SportOddModel>

    @Query("SELECT * FROM SportOddModel WHERE id = :id")
    fun getById(id: String): SportOddModel

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(sportOddModel: SportOddModel)

    @Update
    fun update(sportOddModel: SportOddModel)

    @Query("DELETE FROM SportOddModel WHERE id = :id")
    fun deleteById(id: String)

    @Query("DELETE FROM SportOddModel")
    fun deleteAll()
}