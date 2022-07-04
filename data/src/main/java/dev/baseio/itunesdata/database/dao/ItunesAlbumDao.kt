package dev.baseio.itunesdata.database.dao

import androidx.room.*
import dev.baseio.itunesdata.database.entities.DBITunesAlbum
import kotlinx.coroutines.flow.Flow

@Dao
interface ItunesAlbumDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertOrUpdate(albums: List<DBITunesAlbum>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertOrUpdate(albums: DBITunesAlbum)

    @Query("SELECT isFav from albums where id=:id")
    fun getIsFavById(id: String): Boolean?

    @Query("select * from albums")
    fun getAllAsFlow(): Flow<List<DBITunesAlbum>>

    @Query("select * from albums where label like :search or artistName like :search")
    fun searchAllAsFlow(search:String): Flow<List<DBITunesAlbum>>

    @Query("update albums set isFav =:isFav where id=:id")
    fun updateFav(id: String, isFav: Boolean)
}