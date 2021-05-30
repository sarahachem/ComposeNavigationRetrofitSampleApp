package com.example.dogs.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.ExperimentalFoundationApi
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
import androidx.compose.runtime.rememberCoroutineScope
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
import com.example.dogs.compose.component.text.ContentText
import com.example.dogs.compose.theme.ChipTheme

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
                        DogBreedsCards(scope = scope, state = listState, viewModel = viewModel)
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
                        DogImages(
                            modifier = Modifier,
                            viewModel = viewModel,
                            name = args.name,
                            scope = scope
                        )
                    }
                }
            }
        }
    }
}