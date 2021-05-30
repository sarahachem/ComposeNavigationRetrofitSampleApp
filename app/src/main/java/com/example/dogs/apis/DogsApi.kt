package com.example.dogs.apis

import com.example.dogs.model.BreedImagesResponse
import com.example.dogs.model.BreedsResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

const val BASE_URL = "https://dog.ceo/api/"

interface DogsApi {

    @GET("breeds/list/all/")
    fun getBreeds(): Call<BreedsResponse>

    @GET("breed/{breedName}/images/random/{number}")
    fun getRandomBreedImages(
        @Path("breedName") breedName: String,
        @Path("number") number: Int
    ): Call<BreedImagesResponse>

    @GET("breed/{breedName}/{subBreedName}/images/random/{number}")
    fun getRandomSubBreedImages(
        @Path("breedName") breedName: String,
        @Path("subBreedName") subBreadName: String,
        @Path("number") number: Int
    ): Call<BreedImagesResponse>
}
