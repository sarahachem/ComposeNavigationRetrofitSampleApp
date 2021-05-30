package com.example.dogs

import com.example.dogs.apis.BreedsDeserializer
import com.example.dogs.model.Breeds
import com.example.dogs.model.BreedsResponse
import com.example.dogs.model.DogBreed
import com.google.gson.GsonBuilder
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

import java.io.FileReader

@RunWith(JUnit4::class)
class DogsApiResponseTest {
    lateinit var breedsResponse: BreedsResponse
    lateinit var result1: DogBreed
    lateinit var result2: DogBreed
    lateinit var result3: DogBreed
    lateinit var result4: DogBreed
    lateinit var breeds: Breeds

    @Before
    fun setup() {
        result1 = DogBreed(name = "affenpinscher", subBreeds = emptyList())
        result2 = DogBreed(name = "african", subBreeds = listOf("shepherd"))
        result3 = DogBreed(name = "mastiff", subBreeds = listOf("bull", "english", "tibetan"))
        result4 = DogBreed(name = "pitbull", subBreeds = emptyList())
        breeds = Breeds(listOf(result1, result2, result3, result4))
        breedsResponse = BreedsResponse(message = breeds, status = "success")
    }

    @Test
    fun getBreedsResponseTest() {
        val savedBreedsResponse =
            GsonBuilder().registerTypeAdapter(Breeds::class.java, BreedsDeserializer()).create()
                .fromJson(
                    FileReader("./BreedsJson"),
                    BreedsResponse::class.java
                )
        assertEquals(breedsResponse.message.dogBreeds, savedBreedsResponse.message.dogBreeds)
    }
}
