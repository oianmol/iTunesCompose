package dev.baseio.itunesdomain.datasources

import dev.baseio.itunesdomain.models.DMiTunesAlbum

interface ITunesNetworkSource {
    suspend fun fetchTopFirstAlbum(path: String): List<DMiTunesAlbum>
    suspend fun fetchTopAlbums(path: String): List<DMiTunesAlbum>

}