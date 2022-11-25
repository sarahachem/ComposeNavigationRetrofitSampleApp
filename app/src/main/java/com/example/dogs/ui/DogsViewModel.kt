package com.example.dogs.ui

import android.app.Application
import android.net.ConnectivityManager
import androidx.annotation.VisibleForTesting
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.dogs.apis.DogsApi
import com.example.dogs.model.DogBreed
import com.example.dogs.apis.checkSuccessful
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.withContext
import java.net.SocketTimeoutException

class DogsViewModel(
    app: Application,
    private val dogsApi: DogsApi,
    private val connectivityManager: ConnectivityManager
) : AndroidViewModel(app) {

    private val _expandedCardIdsList = MutableStateFlow(listOf<Int>())
    val expandedCardIdsList: StateFlow<List<Int>> get() = _expandedCardIdsList
    var errorLiveData = mutableLiveDataOf("")
    var isLoadingDogBreedsLiveData = mutableLiveDataOf(true)
    var dogBreedsLiveData = mutableLiveDataOf<List<DogBreed>?>(null)
    val dogImagesLiveData = mutableLiveDataOf<Pair<String, List<String>?>?>(null)

    suspend fun fetchBreeds() {
        try {
            withContext(Dispatchers.IO) {
                if (hasInternetConnection()) {
                    isLoadingDogBreedsLiveData.postValue(true)
                    tryToGetDogBreeds()
                } else {
                    errorLiveData.postValue("No internet connection")
                }
            }
        } catch (e: SocketTimeoutException) {
            onConnectionTimeout()
        }
        if (isLoadingDogBreedsLiveData.value == true) {
            isLoadingDogBreedsLiveData.postValue(false)
        }
    }

    private fun tryToGetDogBreeds() {
        kotlin.runCatching {
            dogsApi.getBreeds().execute()
                .checkSuccessful()
        }.onFailure {
            errorLiveData.postValue(it.message)
        }.onSuccess {
            val response = it.body()
            response?.message?.let { breeds ->
                dogBreedsLiveData.postValue((breeds).dogBreeds)
            }
        }
    }

    suspend fun onDogBreedCLicked(name: String) {
        try {
            if (hasInternetConnection()) {
                kotlin.runCatching {
                    getDogBreedImages(name)
                }.onFailure {
                    errorLiveData.postValue(it.message)
                }
            } else {
                dogImagesLiveData.postValue(null)
                errorLiveData.postValue("no internet connection")
            }
        } catch (e: SocketTimeoutException) {
            onConnectionTimeout()
        }
    }

    @VisibleForTesting
    suspend fun getDogBreedImages(name: String) {
        withContext(Dispatchers.IO) {
            kotlin.runCatching {
                dogsApi
                    .getRandomBreedImages(name, 10)
                    .execute().checkSuccessful()
            }.onFailure {
                errorLiveData.postValue(it.message)
            }.onSuccess {
                dogImagesLiveData.postValue(Pair(name, it.body()?.message))
            }
        }
    }

    @VisibleForTesting
    suspend fun getDogSubBreedImages(breed: String, subBreed: String) {
        withContext(Dispatchers.IO) {
            kotlin.runCatching {
                dogsApi
                    .getRandomSubBreedImages(breed, subBreed, 10)
                    .execute().checkSuccessful()
            }.onFailure {
                errorLiveData.postValue(it.message)
            }.onSuccess {
                dogImagesLiveData.postValue(Pair("$breed-$subBreed", it.body()?.message))
            }
        }
    }

    private fun hasInternetConnection() = connectivityManager.activeNetwork != null

    private fun onConnectionTimeout() {
        errorLiveData.postValue("Connection timeout")
        isLoadingDogBreedsLiveData.postValue(null)
    }

    fun onCardArrowClicked(cardId: Int) {
        _expandedCardIdsList.value = _expandedCardIdsList.value.toMutableList().also { list ->
            if (list.contains(cardId)) list.remove(cardId) else list.add(cardId)
        }
    }

    suspend fun refresh(name: String) {
        val breed = name.split("-")[0]
        val subBreed = name.split("-").getOrNull(1)
        subBreed?.let {
            onDogSubBreedCLicked(breed, subBreed)
        } ?: onDogBreedCLicked(breed)
    }

    suspend fun onDogSubBreedCLicked(breed: String, subBreed: String) {
        try {
            if (hasInternetConnection()) {
                kotlin.runCatching {
                    getDogSubBreedImages(breed, subBreed)
                }.onFailure {
                    errorLiveData.postValue(it.message)
                }
            } else {
                dogImagesLiveData.postValue(null)
                errorLiveData.postValue("no internet connection")
            }
        } catch (e: SocketTimeoutException) {
            onConnectionTimeout()
        }
    }
}

class DogsViewModelFactory(
    private val app: Application,
    private val dogsApi: DogsApi,
    private val connectivityManager: ConnectivityManager
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return (DogsViewModel(app, dogsApi, connectivityManager) as T)
    }
}

fun <T> mutableLiveDataOf(value: T): MutableLiveData<T> =
    MutableLiveData<T>().apply { this.value = value }