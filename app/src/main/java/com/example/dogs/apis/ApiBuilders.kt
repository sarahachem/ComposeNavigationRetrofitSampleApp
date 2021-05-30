package com.example.dogs.apis

import com.example.dogs.model.Breeds
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object ApiBuilders {
    private val customGson = GsonBuilder().registerTypeAdapter(Breeds::class.java, BreedsDeserializer()).create()

    fun getDogsApi(): DogsApi {
        return Retrofit.Builder()
            .client(
                OkHttpClient.Builder()
                    .connectTimeout(30, TimeUnit.SECONDS)
                    .readTimeout(30, TimeUnit.SECONDS)
                    .build()
            )
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(customGson))
            .build()
            .create(DogsApi::class.java)
    }
}