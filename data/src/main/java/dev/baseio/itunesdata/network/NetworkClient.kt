package dev.baseio.itunesdata.network

import dev.baseio.itunesdomain.ITunes
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object NetworkClient {
    fun buildNetworkClient(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(ITunes.API_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build();
    }
}