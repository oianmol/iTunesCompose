package dev.baseio.itunesdomain

import dev.baseio.itunesdomain.models.DMiTunesAlbum
import kotlinx.coroutines.flow.Flow

interface ITunesRepository {
    suspend fun fetchAndSaveTopAlbums(limit: Int)
    suspend fun toggleFav(id: String): Boolean
    fun getAllAlbums(search: String?): Flow<List<DMiTunesAlbum>>
}