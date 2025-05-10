package com.swayni.sportsbettingapp.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.swayni.sportsbettingapp.data.db.dao.SportOddDao
import com.swayni.sportsbettingapp.data.db.model.SportOddModel

@Database(entities = [SportOddModel::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    abstract fun sportOddDao(): SportOddDao

}