package dev.baseio.itunesdomain.usecases

import dev.baseio.itunesdomain.ITunesRepository
import dev.baseio.itunesdomain.models.DMiTunesAlbum
import kotlinx.coroutines.flow.Flow

class UseCaseGetAlbums(private val iTunesRepository: ITunesRepository) {
    operator fun invoke(search: String?): Flow<List<DMiTunesAlbum>> {
        return iTunesRepository.getAllAlbums(search)
    }
}