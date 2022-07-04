package dev.baseio.itunescomposesample

import app.cash.turbine.test
import dev.baseio.itunescomposesample.ui.screens.ITunesScreenVM
import dev.baseio.itunesdomain.ITunesRepository
import dev.baseio.itunesdomain.NetworkInfoProvider
import dev.baseio.itunesdomain.exceptions.NoNetworkException
import dev.baseio.itunesdomain.models.DMiTunesAlbum
import dev.baseio.itunesdomain.usecases.UseCaseFavToggleAlbum
import dev.baseio.itunesdomain.usecases.UseCaseFetchTopAlbums
import dev.baseio.itunesdomain.usecases.UseCaseGetAlbums
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.every
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class ITunesScreenVMShould {

    @MockK
    lateinit var iTunesRepository: ITunesRepository

    @MockK
    lateinit var networkInfoProvider: NetworkInfoProvider

    private val useCaseGetAlbums by lazy { UseCaseGetAlbums(iTunesRepository) }
    private val useCaseFetchTopAlbums by lazy {
        UseCaseFetchTopAlbums(iTunesRepository)
    }
    private val useCaseFavToggleAlbum by lazy {
        UseCaseFavToggleAlbum(iTunesRepository)
    }

    private val viewModel by lazy {
        ITunesScreenVM(
            useCaseFetchTopAlbums,
            useCaseFavToggleAlbum,
            useCaseGetAlbums,
            networkInfoProvider
        )
    }


    @OptIn(ExperimentalCoroutinesApi::class)
    @Before
    fun setUp() {
        MockKAnnotations.init(this, true)
        Dispatchers.setMain(StandardTestDispatcher())
    }


    @OptIn(ExperimentalCoroutinesApi::class)
    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `throw no network exception when no internet`() {
        runTest {
            every {
                networkInfoProvider.hasNetwork()
            } returns false

            every {
                networkInfoProvider.listenToChanges()
            } returns emptyFlow()

            every {
                iTunesRepository.getAllAlbums(any())
            } returns emptyFlow()

            launch {
                viewModel.iTunesViewState.test {
                    val state = awaitItem()
                    assert(state is ITunesScreenVM.ITunesViewState.Exception && state.throwable is NoNetworkException)
                    awaitComplete()
                }
            }
        }
    }

    @Test
    fun `return a list of albums when network becomes available`() {
        runTest {
            every {
                networkInfoProvider.hasNetwork()
            } returns false andThen true

            every {
                networkInfoProvider.listenToChanges()
            } returns flowOf(true)

            coEvery {
                iTunesRepository.fetchAndSaveTopAlbums(any())
            } returns Unit

            every {
                iTunesRepository.getAllAlbums(any())
            } returns flowOf(
                listOf(
                    DMiTunesAlbum(
                        id = "1",
                        price = "$4.5",
                        albumArt = "Some Album",
                        artistName = "Some Artist", label = "Some label", false
                    )
                )
            )
            launch {
                viewModel.iTunesDataSet.test {
                    var state = awaitItem()
                    assert(state.isEmpty())
                    state = awaitItem()
                    assert(state.isNotEmpty())
                    awaitComplete()
                }
            }

            launch {
                viewModel.iTunesViewState.test {
                    val state = awaitItem()
                    assert(state is ITunesScreenVM.ITunesViewState.Exception && state.throwable is NoNetworkException)
                    assert(awaitItem() is ITunesScreenVM.ITunesViewState.Loading) // loading true
                    assert(awaitItem() is ITunesScreenVM.ITunesViewState.Loading) // loading false
                    awaitComplete()
                }
            }
        }
    }
}