package dev.baseio.itunesdomain.usecases

import dev.baseio.itunesdomain.ITunesRepository

class UseCaseFavToggleAlbum(private val iTunesRepository: ITunesRepository) {
    suspend operator fun invoke(id: String): Boolean {
        return iTunesRepository.toggleFav(id)
    }
}