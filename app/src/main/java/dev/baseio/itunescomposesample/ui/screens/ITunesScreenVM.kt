package dev.baseio.itunescomposesample.ui.screens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.baseio.itunesdomain.NetworkInfoProvider
import dev.baseio.itunesdomain.exceptions.NoNetworkException
import dev.baseio.itunesdomain.models.DMiTunesAlbum
import dev.baseio.itunesdomain.usecases.UseCaseFavToggleAlbum
import dev.baseio.itunesdomain.usecases.UseCaseFetchTopAlbums
import dev.baseio.itunesdomain.usecases.UseCaseGetAlbums
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ITunesScreenVM @Inject constructor(
    private val useCaseFetchTopAlbums: UseCaseFetchTopAlbums,
    private val useCaseFavToggleAlbum: UseCaseFavToggleAlbum,
    private val useCaseGetAlbums: UseCaseGetAlbums,
    private val networkInfoProvider: NetworkInfoProvider
) :
    ViewModel() {

    private var getAlbumsJob: Job? = null
    private var networkChange: Job? = null
    var iTunesDataSet = MutableStateFlow<List<DMiTunesAlbum>>(emptyList())
        private set
    var iTunesSearchDataSet = MutableStateFlow<List<DMiTunesAlbum>>(emptyList())
        private set

    var itunesAlbumSearch = MutableStateFlow<String?>(null)
        private set

    var iTunesViewState = MutableStateFlow<ITunesViewState>(ITunesViewState.Empty)
        private set

    var iTunesHeaderViewState =
        MutableStateFlow<ITunesHeaderViewState>(ITunesHeaderViewState.SearchIconMode)
        private set

    init {
        registerForLocalData()
        fetchTopAlbums()
    }

    private fun registerNetwork() {
        networkChange?.cancel()
        networkChange = networkInfoProvider.listenToChanges().onEach {
            onNetworkAvailable(it)
        }.launchIn(viewModelScope)
    }

    private fun onNetworkAvailable(isAvailable: Boolean) {
        if (isAvailable) {
            fetchTopAlbums()
        }
    }

    private fun registerForLocalData() {
        useCaseGetAlbums(null).onEach { list ->
            iTunesDataSet.value = list
        }.launchIn(viewModelScope)
    }

    private fun registerForSearchData(){
        getAlbumsJob?.cancel()
        getAlbumsJob = useCaseGetAlbums(itunesAlbumSearch.value).onEach { list ->
            iTunesSearchDataSet.value = list
        }.launchIn(viewModelScope)
    }

    private fun fetchTopAlbums() {
        if (networkInfoProvider.hasNetwork()) {
            iTunesViewState.value = ITunesViewState.Loading(true)
            viewModelScope.launch(CoroutineExceptionHandler { _, throwable ->
                iTunesViewState.value = ITunesViewState.Exception(throwable)
            }) {
                useCaseFetchTopAlbums(100)
                iTunesViewState.value = ITunesViewState.Loading(false)
            }
        } else {
            iTunesViewState.value = ITunesViewState.Exception(NoNetworkException())
            registerNetwork()
        }

    }

    fun onFavPressed(albumId: String) {
        viewModelScope.launch {
            useCaseFavToggleAlbum(albumId)
        }
    }

    fun searchModeSwitch() {
        itunesAlbumSearch.value = null
        when (iTunesHeaderViewState.value) {
            is ITunesHeaderViewState.ActiveSearchMode -> {
                iTunesHeaderViewState.value = ITunesHeaderViewState.SearchIconMode
            }
            else -> {
                iTunesHeaderViewState.value = ITunesHeaderViewState.ActiveSearchMode

            }
        }
    }

    fun onSearch(search: String) {
        itunesAlbumSearch.value = search
        registerForSearchData()
    }

    sealed class ITunesHeaderViewState {
        object ActiveSearchMode : ITunesHeaderViewState()
        object SearchIconMode : ITunesHeaderViewState()
    }

    sealed class ITunesViewState {
        object Empty : ITunesViewState()
        data class Loading(val isLoading: Boolean) : ITunesViewState()
        data class Exception(val throwable: Throwable) : ITunesViewState()
    }
}
