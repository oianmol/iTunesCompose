package dev.baseio.itunesdomain.datasources

import dev.baseio.itunesdomain.models.DMiTunesAlbum
import kotlinx.coroutines.flow.Flow

interface ITunesLocalSource {
    fun setFav(albumId: String,value:Boolean)
    fun isFav(albumId: String): Boolean
    fun save(dataSet: List<DMiTunesAlbum>?)
    fun searchAsFlow(search: String?): Flow<List<DMiTunesAlbum>>
}