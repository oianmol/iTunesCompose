package dev.baseio.itunescomposesample.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dev.baseio.itunesdata.repo.ITunesRepositoryImpl
import dev.baseio.itunesdata.sources.ITunesLocalSourceImpl
import dev.baseio.itunesdata.sources.ITunesNetworkSourceImpl
import dev.baseio.itunesdomain.datasources.ITunesLocalSource
import dev.baseio.itunesdomain.ITunesRepository
import dev.baseio.itunesdomain.datasources.ITunesNetworkSource
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindITunesRepository(itunesRepo: ITunesRepositoryImpl): ITunesRepository

    @Binds
    @Singleton
    abstract fun bindITunesLocalSource(source: ITunesLocalSourceImpl): ITunesLocalSource

    @Binds
    @Singleton
    abstract fun bindITunesNetworkSource(itunesNetworkSource: ITunesNetworkSourceImpl): ITunesNetworkSource

}