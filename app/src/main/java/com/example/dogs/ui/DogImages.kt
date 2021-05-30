package com.example.dogs.ui

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.dogs.compose.theme.FourtyEightDp
import com.example.dogs.compose.theme.SixteenDp
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState

@ExperimentalComposeUiApi
@ExperimentalAnimationApi
@ExperimentalFoundationApi
@ExperimentalMaterialApi
@Composable
fun DogImages(
    modifier: Modifier = Modifier,
    images: List<String>,
    name: String,
    onRefresh: (String) -> Unit
) {
    val state = rememberLazyListState()
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        SwipeRefresh(
            state = rememberSwipeRefreshState(false),
            onRefresh = { onRefresh.invoke(name) },
        ) {
            LazyColumn(
                state = state,
                modifier = Modifier
                    .fillMaxSize()
            ) {
                items(images) { item ->
                    Card(
                        modifier = modifier
                            .fillMaxWidth()
                            .padding(
                                start = FourtyEightDp,
                                end = FourtyEightDp,
                                top = SixteenDp
                            ),
                        shape = MaterialTheme.shapes.large,
                        elevation = 0.dp,
                    ) {
                        DogImage(url = item)
                    }
                }

            }
        }
    }
}
