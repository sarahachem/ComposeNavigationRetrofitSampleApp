package com.example.dogs.ui

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import com.example.dogs.compose.theme.SixteenDp
import com.example.dogs.compose.theme.TwentyFourDp
import com.example.dogs.model.DogBreed

@ExperimentalComposeUiApi
@ExperimentalAnimationApi
@ExperimentalFoundationApi
@ExperimentalMaterialApi
@Composable
fun DogBreedsCards(
    state: LazyListState,
    breeds: List<DogBreed>,
    expandedCardIds: List<Int>,
    onCardArrowClicked: (Int) -> Unit,
    onDogBreedCLicked: (String) -> Unit,
    onDogSubBreedCLicked: (String, String) -> Unit
) {
    LazyColumn(
        state = state,
        modifier = Modifier
            .fillMaxSize()
    ) {
        itemsIndexed(breeds) { index, item ->
            ExpandableDogCard(
                modifier = Modifier.padding(
                    start = TwentyFourDp,
                    end = TwentyFourDp,
                    top = SixteenDp
                ),
                card = ExpandableCardInfo(index, item.name),
                onCardArrowClick = { onCardArrowClicked(index) },
                onCardClicked = { onDogBreedCLicked(it) },
                expandableContent = item.subBreeds,
                expandable = item.subBreeds.isNotEmpty(),
                expanded = expandedCardIds.contains(index),
                onContentClicked = { breed, subBreed -> onDogSubBreedCLicked(breed, subBreed) }
            )
        }
    }
}

