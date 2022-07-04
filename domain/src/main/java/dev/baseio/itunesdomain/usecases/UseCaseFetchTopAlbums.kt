package dev.baseio.itunesdomain.usecases

import dev.baseio.itunesdomain.ITunesRepository

class UseCaseFetchTopAlbums(private val repository: ITunesRepository) {
    suspend operator fun invoke(limit:Int) {
        return repository.fetchAndSaveTopAlbums(limit)
    }
}