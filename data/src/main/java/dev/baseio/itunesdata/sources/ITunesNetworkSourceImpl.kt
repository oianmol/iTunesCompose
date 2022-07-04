package dev.baseio.itunesdata.sources

import dev.baseio.itunesdata.network.ITunesApi
import dev.baseio.itunesdata.network.model.ITunesAlbum
import dev.baseio.itunesdata.network.model.ITunesAlbumMultipleEntries
import dev.baseio.itunesdomain.datasources.ITunesNetworkSource
import dev.baseio.itunesdomain.models.DMiTunesAlbum
import dev.baseio.itunesdomain.models.EntityMapper
import javax.inject.Inject

class ITunesNetworkSourceImpl @Inject constructor(
    private val itunesApi: ITunesApi,
    private val iTunesAlbumMapper: EntityMapper<ITunesAlbum, List<DMiTunesAlbum>>,
    private val iTunesAlbumMultipleEntriesMapper: EntityMapper<ITunesAlbumMultipleEntries, List<DMiTunesAlbum>>
) : ITunesNetworkSource {
    override suspend fun fetchTopFirstAlbum(path: String): List<DMiTunesAlbum> {
        itunesApi.fetchTopFirstAlbum(path).body()?.let { result ->
            return iTunesAlbumMapper.mapToDomain(result)
        }
        return emptyList()
    }

    override suspend fun fetchTopAlbums(path: String): List<DMiTunesAlbum> {
        itunesApi.fetchTopAlbums(path).body()?.let { result ->
            return iTunesAlbumMultipleEntriesMapper.mapToDomain(result)
        }
        return emptyList()
    }
}