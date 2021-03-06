package com.example.dogs.compose.component.text

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import com.example.dogs.compose.theme.ChipTheme

@Composable
fun ContentText(
    modifier: Modifier = Modifier,
    text: String,
    color: Color = ChipTheme.colors.textPrimary,
    textAlign: TextAlign? = null
) = Text(
    modifier = modifier,
    textAlign = textAlign,
    text = text,
    color = color,
    overflow = TextOverflow.Ellipsis,
    style = ChipTheme.types.body1
)

@Composable
fun BodyText(
    modifier: Modifier = Modifier,
    textAlign: TextAlign?= null,
    text: String,
    color: Color = ChipTheme.colors.textSecondary
) = ContentText(modifier, textAlign = textAlign, text = text, color = color)