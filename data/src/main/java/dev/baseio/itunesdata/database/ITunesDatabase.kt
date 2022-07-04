package dev.baseio.itunesdata.database

import androidx.room.Database
import androidx.room.RoomDatabase
import dev.baseio.itunesdata.database.dao.ItunesAlbumDao
import dev.baseio.itunesdata.database.entities.DBITunesAlbum

@Database(
    entities = [DBITunesAlbum::class],
    version = 1,
    exportSchema = false
)
abstract class ITunesDatabase : RoomDatabase() {
    abstract fun itunesAlbumDao(): ItunesAlbumDao
}