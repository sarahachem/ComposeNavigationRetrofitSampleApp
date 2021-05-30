package com.example.dogs.compose.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.KeyboardArrowRight
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import com.example.dogs.compose.component.text.ContentText
import com.example.dogs.compose.theme.FourDp
import com.example.dogs.compose.theme.ChipTheme
import com.example.dogs.compose.theme.EightDp
import com.example.dogs.compose.theme.TwelveDp

@Composable
fun LabelIconCell(
    modifier: Modifier = Modifier,
    textAlign: TextAlign? = null,
    text: String,
    parent: String,
    color: Color = ChipTheme.colors.textSecondary,
    onClick: ((String, String) -> Unit),
    topDivider: Boolean
) {
    Column(Modifier.clickable { onClick.invoke(parent, text) }) {
        if (topDivider) Divider(color = ChipTheme.colors.divider)
        Box(
            modifier = modifier
                .fillMaxHeight()
                .padding(horizontal = FourDp)
        ) {
            ContentText(modifier, textAlign = textAlign, text = text, color = color.copy(0.9f))
            Icon(
                modifier = Modifier.align(Alignment.CenterEnd),
                imageVector = Icons.Outlined.KeyboardArrowRight,
                contentDescription = "Next Arrow",
                tint = ChipTheme.colors.textSecondary.copy(alpha = 0.9f)
            )
        }
    }
}


@Preview
@Composable
fun DogCardPreview() {
    ChipTheme {
        LabelIconCell(
            topDivider = true,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = TwelveDp, vertical = EightDp),
            text = "test",
            onClick = { s: String, s1: String -> },
            parent = "parent"
        )
    }
}