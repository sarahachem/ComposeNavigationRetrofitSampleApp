package com.example.dogs

import android.app.Application
import android.net.ConnectivityManager
import android.net.Network
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.example.dogs.apis.BreedsDeserializer
import com.example.dogs.apis.DogsApi
import com.example.dogs.model.BreedImagesResponse
import com.example.dogs.model.Breeds
import com.example.dogs.model.BreedsResponse
import com.example.dogs.model.DogBreed
import com.example.dogs.ui.DogsViewModel
import com.google.gson.GsonBuilder
import junit.framework.Assert.assertEquals
import java.io.FileReader
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.newSingleThreadContext
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner.Silent::class)
class DogsViewModelTest {
    @Rule
    @JvmField
    val rule = InstantTaskExecutorRule()

    @Mock
    private lateinit var app: Application

    @Mock
    private lateinit var api: DogsApi

    @Mock
    private lateinit var connectionManager: ConnectivityManager

    @InjectMocks
    private lateinit var viewModel: DogsViewModel

    @Mock
    lateinit var observer: Observer<in List<DogBreed>?>

    @Mock
    lateinit var imagesObserver: Observer<in Pair<String, List<String>?>?>

    @Mock
    lateinit var network: Network

    private val breeds: BreedsResponse =
        GsonBuilder().registerTypeAdapter(Breeds::class.java, BreedsDeserializer()).create()
            .fromJson(FileReader("./BreedsJson"), BreedsResponse::class.java)

    private val breedImages: BreedImagesResponse = GsonBuilder().create()
        .fromJson(FileReader("./BreedImagesJson"), BreedImagesResponse::class.java)

    private val subBreedImages: BreedImagesResponse = GsonBuilder().create()
        .fromJson(FileReader("./SubBreedImagesJson"), BreedImagesResponse::class.java)

    private val mainThreadSurrogate = newSingleThreadContext("UI thread")

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        Dispatchers.setMain(mainThreadSurrogate)
        `when`(api.getBreeds()).thenReturn(
            TestCall.buildSuccess(breeds)
        )
        `when`(api.getRandomBreedImages("pitbull", 10)).thenReturn(
            TestCall.buildSuccess(breedImages)
        )
        `when`(api.getRandomSubBreedImages("australian","shepherd", 10)).thenReturn(
            TestCall.buildSuccess(subBreedImages)
        )
        `when`(connectionManager.activeNetwork).thenReturn(network)
        viewModel = DogsViewModel(app, api, connectionManager)
        viewModel.dogBreedsLiveData.observeForever(observer)
        viewModel.dogImagesLiveData.observeForever(imagesObserver)
    }

    @Test
    fun testFetchBreeds() {
        runBlocking {
            launch(Dispatchers.Main) {
                viewModel.fetchBreeds()
                assertEquals(viewModel.dogBreedsLiveData.value, breeds.message.dogBreeds)
            }
        }
    }

    @Test
    fun testFetchBreedImages() {
        runBlocking {
            launch(Dispatchers.Main) {
                viewModel.getDogBreedImages("pitbull")
                assertEquals(viewModel.dogImagesLiveData.value, Pair("pitbull", breedImages.message))
            }
        }
    }

    @Test
    fun testFetchSubBreedImages() {
        runBlocking {
            launch(Dispatchers.Main) {
                viewModel.getDogSubBreedImages("australian", "shepherd")
                assertEquals(viewModel.dogImagesLiveData.value, Pair("australian-shepherd", subBreedImages.message))
            }
        }
    }

    @Test
    fun testRefreshSubBreed() {
        runBlocking {
            launch(Dispatchers.Main) {
                viewModel.refresh("australian-shepherd")
                assertEquals(viewModel.dogImagesLiveData.value, Pair("australian-shepherd", subBreedImages.message))
            }
        }
    }

    @Test
    fun testRefreshBreed() {
        runBlocking {
            launch(Dispatchers.Main) {
                viewModel.refresh("pitbull")
                assertEquals(viewModel.dogImagesLiveData.value, Pair("pitbull", breedImages.message))
            }
        }
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        mainThreadSurrogate.close()
    }
}
