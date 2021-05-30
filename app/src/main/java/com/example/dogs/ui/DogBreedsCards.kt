package com.example.dogs.ui

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import com.example.dogs.compose.component.CircleProgress
import com.example.dogs.compose.theme.SixteenDp
import com.example.dogs.compose.theme.TwelveDp
import com.example.dogs.compose.theme.TwentyFourDp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@ExperimentalComposeUiApi
@ExperimentalAnimationApi
@ExperimentalFoundationApi
@ExperimentalMaterialApi
@Composable
fun DogBreedsCards(
    scope: CoroutineScope,
    state: LazyListState,
    viewModel: DogsViewModel
) {
    val expandedCardIds = viewModel.expandedCardIdsList.collectAsState()
    val isLoadingDogBreeds by viewModel.isLoadingDogBreedsLiveData.observeAsState()
    val dogBreeds by viewModel.dogBreedsLiveData.observeAsState()
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        LazyColumn(
            state = state,
            modifier = Modifier
                .fillMaxSize()
        ) {
            dogBreeds?.let {
                itemsIndexed(it) { index, item ->
                    ExpandableDogCard(
                        modifier = Modifier.padding(
                            start = TwentyFourDp,
                            end = TwentyFourDp,
                            top = SixteenDp
                        ),
                        card = ExpandableCardInfo(index, item.name),
                        onCardArrowClick = { viewModel.onCardArrowClicked(index) },
                        onCardClicked = {
                            scope.launch { viewModel.onDogBreedCLicked(it) }
                        },
                        expandableContent = item.subBreeds,
                        expandable = item.subBreeds.isNotEmpty(),
                        expanded = expandedCardIds.value.contains(index),
                        onContentClicked = { breed, subBreed ->
                            scope.launch { viewModel.onDogSubBreedCLicked(breed, subBreed) }
                        }
                    )
                }
            }
        }
        if (isLoadingDogBreeds == true) {
            CircleProgress(
                Modifier.matchParentSize()
            )
        }
    }
}
