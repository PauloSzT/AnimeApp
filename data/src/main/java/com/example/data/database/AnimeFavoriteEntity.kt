package com.example.data.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorite")
class AnimeFavoriteEntity(
    @PrimaryKey
    val id: Int,
    @ColumnInfo(name = "coverImage")
    val coverImage: String,
    @ColumnInfo(name = "title")
    val title: String
)
