package dev.baseio.itunescomposesample.di

import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dev.baseio.itunesdata.mappers.ITunesAlbumMapper
import dev.baseio.itunesdata.mappers.ITunesAlbumMultipleMapper
import dev.baseio.itunesdata.network.model.ITunesAlbum
import dev.baseio.itunesdata.network.model.ITunesAlbumMultipleEntries
import dev.baseio.itunesdomain.models.DMiTunesAlbum
import dev.baseio.itunesdomain.models.EntityMapper
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class MapperModule {

    @Provides
    @Singleton
    fun bindITunesAlbumMultipleMapper(): EntityMapper<ITunesAlbumMultipleEntries, List<DMiTunesAlbum>> =
        ITunesAlbumMultipleMapper()

    @Provides
    @Singleton
    fun bindITunesAlbumMapper(): EntityMapper<ITunesAlbum, List<DMiTunesAlbum>> =
        ITunesAlbumMapper()
}