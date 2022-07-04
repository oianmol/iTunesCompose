package dev.baseio.itunescomposesample.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dev.baseio.itunesdomain.ITunesRepository
import dev.baseio.itunesdomain.usecases.UseCaseFavToggleAlbum
import dev.baseio.itunesdomain.usecases.UseCaseFetchTopAlbums
import dev.baseio.itunesdomain.usecases.UseCaseGetAlbums
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class UseCaseModule {

    @Provides
    @Singleton
    fun provideUseCaseGetAlbums(iTunesRepository: ITunesRepository) =
        UseCaseGetAlbums(iTunesRepository)

    @Provides
    @Singleton
    fun provideUseCaseFetchTopAlbums(iTunesRepository: ITunesRepository) =
        UseCaseFetchTopAlbums(iTunesRepository)

    @Provides
    @Singleton
    fun provideUseCaseFavToggleAlbum(iTunesRepository: ITunesRepository) =
        UseCaseFavToggleAlbum(iTunesRepository)
}