package com.example.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [AnimeFavoriteEntity::class], version = 1, exportSchema = false)
abstract class AppDataBase : RoomDatabase() {

    abstract fun favoriteQuoteDao(): FavoriteAnimeDao

    companion object {

        @Volatile
        private var instance: AppDataBase? = null

        fun getDatabase(context: Context): AppDataBase =
            instance ?: synchronized(this){
                Room.databaseBuilder(context.applicationContext, AppDataBase::class.java, "favorites")
                    .fallbackToDestructiveMigration().build().also {
                        instance = it
                    }
            }
    }
}
