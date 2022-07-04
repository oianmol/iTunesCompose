package dev.baseio.itunescomposesample

import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import dev.baseio.itunesdomain.NetworkInfoProvider
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import javax.inject.Inject

class NetworkInfoProviderImpl @Inject constructor(private val connectivityManager: ConnectivityManager) :
    NetworkInfoProvider {

    override fun hasNetwork(): Boolean {
        val caps = connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
        return caps?.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET) == true
    }

    override fun listenToChanges(): Flow<Boolean> {
        return callbackFlow {
            val networkCallback = object : ConnectivityManager.NetworkCallback() {
                // network is available for use
                override fun onAvailable(network: Network) {
                    super.onAvailable(network)
                    this@callbackFlow.trySend(true)
                }

                // lost network connection
                override fun onLost(network: Network) {
                    super.onLost(network)
                    this@callbackFlow.trySend(false)
                }
            }
            connectivityManager.requestNetwork(
                NetworkRequest.Builder()
                    .addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
                    .addTransportType(NetworkCapabilities.TRANSPORT_WIFI)
                    .addTransportType(NetworkCapabilities.TRANSPORT_CELLULAR)
                    .build(), networkCallback
            )
            this.awaitClose {
                connectivityManager.unregisterNetworkCallback(networkCallback)
            }
        }

    }

}
