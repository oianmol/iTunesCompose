package dev.baseio.itunescomposesample.di

import android.content.Context
import android.net.ConnectivityManager
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import dev.baseio.itunescomposesample.NetworkInfoProviderImpl
import dev.baseio.itunesdata.database.ITunesDatabase
import dev.baseio.itunesdata.network.ITunesApi
import dev.baseio.itunesdata.network.NetworkClient
import dev.baseio.itunesdomain.NetworkInfoProvider
import kotlinx.coroutines.Dispatchers
import javax.inject.Singleton
import kotlin.coroutines.CoroutineContext

@InstallIn(SingletonComponent::class)
@Module
class AppModule {
    @Provides
    @Singleton
    fun provideITunesApi(): ITunesApi =
        NetworkClient.buildNetworkClient().create(ITunesApi::class.java)

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context) =
        Room.databaseBuilder(context, ITunesDatabase::class.java, "itunes-db").build()

    @Provides
    @Singleton
    fun provideCoroutineContext(): CoroutineContext = Dispatchers.IO

    @Provides
    @Singleton
    fun provideConnectivityManager(@ApplicationContext context: Context) =
        context.getSystemService(ConnectivityManager::class.java) as ConnectivityManager

    @Provides
    @Singleton
    fun provideNetworkInfoProvider(connectivityManager: ConnectivityManager): NetworkInfoProvider =
        NetworkInfoProviderImpl(connectivityManager)
}