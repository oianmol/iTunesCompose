package dev.baseio.itunesdata.sources

import dev.baseio.itunesdata.database.ITunesDatabase
import dev.baseio.itunesdata.database.entities.DBITunesAlbum
import dev.baseio.itunesdomain.datasources.ITunesLocalSource
import dev.baseio.itunesdomain.models.DMiTunesAlbum
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.mapLatest
import javax.inject.Inject

class ITunesLocalSourceImpl @Inject constructor(private val iTunesDatabase: ITunesDatabase) :
    ITunesLocalSource {

    override fun searchAsFlow(search: String?): Flow<List<DMiTunesAlbum>> {
        search?.takeIf { it.isNotEmpty() }?.let { nnSearch->
            return iTunesDatabase.itunesAlbumDao().searchAllAsFlow("%${nnSearch}%").mapLatest { list ->
                list.map { dbiTunesAlbum -> dbiTunesAlbum.toDMiTunesAlbum() }
            }
        }?:run{
            return iTunesDatabase.itunesAlbumDao().getAllAsFlow().mapLatest {
                it.map { dbiTunesAlbum -> dbiTunesAlbum.toDMiTunesAlbum() }
            }
        }
    }

    override fun setFav(albumId: String, value: Boolean) {
        iTunesDatabase.itunesAlbumDao()
            .updateFav(id = albumId, isFav = value)
    }

    override fun isFav(albumId: String): Boolean {
        return iTunesDatabase.itunesAlbumDao().getIsFavById(albumId) == true
    }

    override fun save(dataSet: List<DMiTunesAlbum>?) {
        val dbItunesData = dataSet?.map {
            it.toDBITunesAlbum()
        } ?: emptyList()
        iTunesDatabase.itunesAlbumDao().insertOrUpdate(dbItunesData)
    }

    private fun DMiTunesAlbum.toDBITunesAlbum(): DBITunesAlbum {
        return DBITunesAlbum(
            this.id,
            isFav = isFav(this.id),
            price = this.price,
            albumArt = this.albumArt,
            artistName = artistName,
            label = label
        )
    }
}

private fun DBITunesAlbum.toDMiTunesAlbum(): DMiTunesAlbum {
    return DMiTunesAlbum(
        id = this.id,
        price = this.price,
        albumArt = this.albumArt,
        artistName,
        label,
        isFav
    )
}
