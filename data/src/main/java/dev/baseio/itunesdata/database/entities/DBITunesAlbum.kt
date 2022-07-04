package dev.baseio.itunesdata.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "albums")
data class DBITunesAlbum(
    @PrimaryKey val id: String,
    @ColumnInfo(name = "isFav") val isFav: Boolean = false,
    val price: String,
    val albumArt: String,
    val artistName: String,
    val label: String,
)