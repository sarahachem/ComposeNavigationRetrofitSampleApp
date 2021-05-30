package com.example.dogs.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.navArgs
import com.example.dogs.R
import com.example.dogs.compose.component.CircleProgress
import com.example.dogs.compose.component.text.ContentText
import com.example.dogs.compose.theme.ChipTheme
import kotlinx.coroutines.launch

@ExperimentalComposeUiApi
@ExperimentalAnimationApi
@ExperimentalFoundationApi
@ExperimentalMaterialApi
class BreedsFragment : Fragment() {

    private val viewModel by activityViewModels<DogsViewModel>()

    @OptIn(ExperimentalMaterialApi::class)
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setContent {
                val isLoadingDogBreeds by viewModel.isLoadingDogBreedsLiveData.observeAsState()
                val dogBreeds by viewModel.dogBreedsLiveData.observeAsState()
                val expandedCardIds = viewModel.expandedCardIdsList.collectAsState()
                ChipTheme {
                    val scope = rememberCoroutineScope()
                    val listState = rememberLazyListState()
                    Scaffold(
                        modifier = Modifier.heightIn(56.dp),
                        topBar = {
                            TopAppBar(
                                elevation = 0.dp,
                                title = {
                                    ContentText(
                                        text = stringResource(id = R.string.dog_breeds),
                                        textAlign = TextAlign.Start
                                    )
                                },
                                navigationIcon = {
                                    IconButton(onClick = { activity?.onBackPressed() }) {
                                        Icon(
                                            Icons.Rounded.ArrowBack,
                                            tint = MaterialTheme.colors.onSurface,
                                            contentDescription = "Back button"
                                        )
                                    }
                                }
                            )
                        }
                    ) {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            dogBreeds?.let {
                                DogBreedsCards(
                                    state = listState,
                                    breeds = it,
                                    expandedCardIds = expandedCardIds.value,
                                    onCardArrowClicked = { id -> viewModel.onCardArrowClicked(id) },
                                    onDogBreedCLicked = { breed ->
                                        scope.launch {
                                            viewModel.onDogBreedCLicked(
                                                breed
                                            )
                                        }
                                    },
                                    onDogSubBreedCLicked = { breed, subBreed ->
                                        scope.launch {
                                            viewModel.onDogSubBreedCLicked(
                                                breed,
                                                subBreed
                                            )
                                        }
                                    }
                                )
                            }
                            if (isLoadingDogBreeds == true) {
                                CircleProgress(
                                    Modifier.matchParentSize()
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@ExperimentalComposeUiApi
@ExperimentalAnimationApi
@ExperimentalFoundationApi
@ExperimentalMaterialApi
class BreedImagesFragment : Fragment() {

    private val viewModel by activityViewModels<DogsViewModel>()
    private val args: BreedImagesFragmentArgs by navArgs()

    @OptIn(ExperimentalMaterialApi::class)
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        return ComposeView(requireContext()).apply {
            setContent {
                ChipTheme {
                    val dogImages by viewModel.dogImagesLiveData.observeAsState()
                    val scope = rememberCoroutineScope()
                    Scaffold(
                        modifier = Modifier.heightIn(56.dp),
                        topBar = {
                            TopAppBar(
                                elevation = 0.dp,
                                title = {
                                    ContentText(
                                        text = args.name,
                                        textAlign = TextAlign.Start
                                    )
                                },
                                navigationIcon = {
                                    IconButton(onClick = { activity?.onBackPressed() }) {
                                        Icon(
                                            Icons.Rounded.ArrowBack,
                                            tint = MaterialTheme.colors.onSurface,
                                            contentDescription = "Back button"
                                        )
                                    }
                                }
                            )
                        }
                    ) {
                        dogImages?.second?.let {
                            DogImages(
                                modifier = Modifier,
                                images = it,
                                name = args.name,
                                onRefresh = { scope.launch { viewModel.refresh(args.name) } }
                            )
                        }
                    }
                }
            }
        }
    }
}