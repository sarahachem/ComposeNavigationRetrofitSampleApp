package com.example.dogs.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.padding
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import coil.request.CachePolicy
import com.example.dogs.R
import com.example.dogs.compose.component.text.BodyText
import com.example.dogs.compose.theme.ChipTheme
import com.example.dogs.compose.theme.SixteenDp
import com.google.accompanist.coil.rememberCoilPainter
import com.google.accompanist.imageloading.ImageLoadState

@Composable
fun DogImage(
    modifier: Modifier = Modifier,
    url: String?,
    backgroundColor: Color = MaterialTheme.colors.surface,
    textColor: Color = MaterialTheme.colors.onSurface
) {
    Column {
        Box(modifier = modifier.background(color = backgroundColor)) {
            val painter =
                rememberCoilPainter(
                    request = url,
                    fadeIn = true,
                    requestBuilder = {
                        val cachePolicy = CachePolicy.ENABLED
                        diskCachePolicy(cachePolicy)
                        memoryCachePolicy(cachePolicy)
                        networkCachePolicy(CachePolicy.ENABLED)
                    })
            Image(
                painter = painter,
                modifier = Modifier.aspectRatio(ratio = 1f),
                contentDescription = "Breed image",
                contentScale = ContentScale.Fit
            )
            when (painter.loadState) {
                ImageLoadState.Loading -> {
                    CircularProgressIndicator(
                        modifier = Modifier
                            .padding(SixteenDp)
                            .align(Alignment.Center), color = ChipTheme.colors.mainColor
                    )
                }
                is ImageLoadState.Error -> {
                    val text = stringResource(id = R.string.no_image_error)
                    val textModifier = Modifier.align(Alignment.Center)
                    BodyText(modifier = textModifier, text = text, color = textColor)
                }

                else -> {}
            }
        }
    }
}
