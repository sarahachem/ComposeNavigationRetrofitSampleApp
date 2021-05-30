package com.example.dogs.ui

import android.annotation.SuppressLint
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.animation.core.TweenSpec
import androidx.compose.animation.core.animateDp
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.updateTransition
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.outlined.KeyboardArrowRight
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.dogs.compose.component.LabelIconCell
import com.example.dogs.compose.theme.ChipTheme
import com.example.dogs.compose.theme.EightDp
import com.example.dogs.compose.theme.FourDp
import com.example.dogs.compose.theme.SixteenDp
import com.example.dogs.compose.theme.TwelveDp
import com.example.dogs.compose.theme.TwentyFourDp

@SuppressLint("UnusedTransitionTargetStateParameter")
@Composable
fun ExpandableDogCard(
    modifier: Modifier = Modifier,
    card: ExpandableCardInfo,
    onCardArrowClick: () -> Unit,
    expanded: Boolean = false,
    onCardClicked: (String) -> Unit,
    onContentClicked: (String, String) -> Unit,
    expandableContent: List<String>,
    expandable: Boolean = expandableContent.isNotEmpty(),
) {
    val transitionState = remember {
        MutableTransitionState(expanded).apply {
            targetState = !expanded
        }
    }
    val transition = updateTransition(transitionState, label = "")
    val cardPaddingHorizontal by transition.animateDp({ tween() }, label = "") { 4.dp }
    val cardElevation by transition.animateDp(
        { tween() }, label = "elevation"
    ) { if (expanded) TwentyFourDp else FourDp }
    val cardRoundedCorners by transition.animateDp(
        { tween(easing = FastOutSlowInEasing) }, label = "corners"
    ) { SixteenDp }
    val arrowRotationDegree by transition.animateFloat(
        { tween() },
        label = ""
    ) { if (expanded) 180f else 0f }
    Card(
        elevation = cardElevation,
        shape = RoundedCornerShape(cardRoundedCorners),
        modifier = modifier
            .fillMaxWidth()
            .padding(
                horizontal = cardPaddingHorizontal,
                vertical = 2.dp
            )
    ) {
        Column {
            Box(
                modifier = Modifier.clickable { onCardClicked(card.title) }
            ) {
                if (expandable)
                    CardArrow(
                        degrees = arrowRotationDegree,
                        onClick = onCardArrowClick,
                        clickable = expandable
                    )
                CardTitle(title = card.title)
                Icon(
                    modifier = Modifier
                        .align(Alignment.CenterEnd)
                        .padding(end = 12.dp),
                    imageVector = Icons.Outlined.KeyboardArrowRight,
                    contentDescription = "Expandable Arrow",
                )
            }
            if (expandable)
                ExpandableContent(
                    visible = expanded,
                    initialVisibility = expanded,
                    content = expandableContent,
                    parent = card.title,
                    onContentClicked = onContentClicked
                )
        }
    }
}

@Immutable
data class ExpandableCardInfo(val id: Int, val title: String)

@Composable
fun CardArrow(
    degrees: Float,
    onClick: () -> Unit,
    clickable: Boolean
) {
    IconButton(
        enabled = clickable,
        onClick = onClick,
        content = {
            Icon(
                imageVector = Icons.Filled.ArrowDropDown,
                contentDescription = "Expandable Arrow",
                modifier = Modifier.rotate(degrees),
            )
        }
    )
}

@Composable
fun CardTitle(title: String) {
    Text(
        text = title,
        modifier = Modifier
            .fillMaxWidth()
            .padding(SixteenDp),
        textAlign = TextAlign.Center,
    )
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun ExpandableContent(
    visible: Boolean = true,
    initialVisibility: Boolean = false,
    content: List<String>?,
    parent: String,
    onContentClicked: (String, String) -> Unit
) {
    val enterFadeIn = remember {
        fadeIn(
            animationSpec = TweenSpec(
                durationMillis = 200,
                easing = FastOutLinearInEasing
            )
        )
    }
    val enterExpand = remember {
        expandVertically(animationSpec = tween())
    }
    val exitFadeOut = remember {
        fadeOut(
            animationSpec = TweenSpec(
                durationMillis = 200,
                easing = LinearOutSlowInEasing
            )
        )
    }
    val exitCollapse = remember {
        shrinkVertically(animationSpec = tween(200))
    }
    AnimatedVisibility(
        visible = visible,
        initiallyVisible = initialVisibility,
        enter = enterExpand + enterFadeIn,
        exit = exitCollapse + exitFadeOut
    ) {
        Column(modifier = Modifier.padding(horizontal = EightDp)) {
            content?.forEach {
                LabelIconCell(
                    topDivider = true,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = TwelveDp, vertical = EightDp),
                    text = it,
                    onClick = { s: String, s1: String -> onContentClicked(s, s1) },
                    parent = parent
                )
            }
        }
    }
}

@Preview
@Composable
fun DogExpandedCardPreview() {
    ChipTheme {
        ExpandableDogCard(
            card = ExpandableCardInfo(0, "test"),
            onCardArrowClick = {},
            expanded = true,
            expandable = true,
            onCardClicked = {},
            expandableContent = listOf("sublist1"),
            onContentClicked = { s: String, s1: String -> }
        )
    }
}

@Preview
@Composable
fun DogCardPreview() {
    ChipTheme {
        ExpandableDogCard(
            card = ExpandableCardInfo(0, "test"),
            onCardArrowClick = {},
            expanded = false,
            expandable = true,
            onCardClicked = {},
            expandableContent = listOf("sublist1"),
            onContentClicked = { s: String, s1: String -> }
        )
    }
}

