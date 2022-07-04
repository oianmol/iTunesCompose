package dev.baseio.itunesdata.repo

import dev.baseio.itunesdomain.datasources.ITunesLocalSource
import dev.baseio.itunesdomain.ITunesRepository
import dev.baseio.itunesdomain.datasources.ITunesNetworkSource
import dev.baseio.itunesdomain.models.DMiTunesAlbum
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

class ITunesRepositoryImpl @Inject constructor(
    private val coroutineContext: CoroutineContext,
    private val iTunesNetworkSource: ITunesNetworkSource,
    private val itunesLocalSource: ITunesLocalSource,
) : ITunesRepository {

    override fun getAllAlbums(search: String?): Flow<List<DMiTunesAlbum>> {
        return itunesLocalSource.searchAsFlow(search)
    }

    override suspend fun fetchAndSaveTopAlbums(limit: Int) {
        withContext(coroutineContext) {
            val dataSet = when (limit) {
                1 -> {
                    iTunesNetworkSource.fetchTopFirstAlbum("limit=$limit").map {
                        it.copy(isFav = itunesLocalSource.isFav(it.id))
                    }
                }
                else -> {
                    iTunesNetworkSource.fetchTopAlbums("limit=$limit").map {
                        it.copy(isFav = itunesLocalSource.isFav(it.id))
                    }
                }
            }
            itunesLocalSource.save(dataSet)
        }
    }

    override suspend fun toggleFav(id: String): Boolean {
        return withContext(coroutineContext) {
            itunesLocalSource.setFav(id, !itunesLocalSource.isFav(id))
            itunesLocalSource.isFav(id)
        }
    }
}