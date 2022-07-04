package dev.baseio.itunesdata.network

import dev.baseio.itunesdata.network.model.ITunesAlbum
import dev.baseio.itunesdata.network.model.ITunesAlbumMultipleEntries
import dev.baseio.itunesdomain.ITunes
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface ITunesApi {
    @GET("topalbums/{limit}/json")
    suspend fun fetchTopAlbums(@Path("limit") limit: String): Response<ITunesAlbumMultipleEntries>

    @GET("topalbums/{limit}/json")
    suspend fun fetchTopFirstAlbum(@Path("limit") limit: String): Response<ITunesAlbum>
}