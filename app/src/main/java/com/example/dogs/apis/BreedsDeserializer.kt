package com.example.dogs.apis

import com.example.dogs.model.Breeds
import com.example.dogs.model.DogBreed
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import java.lang.reflect.Type

class BreedsDeserializer : JsonDeserializer<Breeds> {

    override fun deserialize(
        json: JsonElement,
        typeOfT: Type?,
        context: JsonDeserializationContext?
    ): Breeds {
        return deserializeBreeds(json.asJsonObject)
    }

    private fun deserializeBreeds(breedsObject: JsonObject): Breeds {
        return Breeds(breedsObject.entrySet()
            .map { DogBreed(it.key, it.value.asJsonArray.map { it.asString }) })
    }
}
