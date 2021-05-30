package com.example.dogs.model

data class BreedsResponse(
    var message: Breeds,
    val status: String
)

data class Breeds(val dogBreeds: List<DogBreed>)