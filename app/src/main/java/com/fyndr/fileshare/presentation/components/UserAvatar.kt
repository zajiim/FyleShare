package com.fyndr.fileshare.presentation.components

import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun UserAvatar(
    modifier: Modifier = Modifier,
    sharedTransitionScope: SharedTransitionScope? = null,
    animatedVisibilityScope: AnimatedVisibilityScope? = null,
    name: String,
    color: Color,
    size: Dp,
    shouldShowName: Boolean = true,
    isLoading: Boolean = false,
) {

    val progress by animateFloatAsState(
        targetValue = if (isLoading) 1f else 0f,
        animationSpec = tween(
            durationMillis = 2000,
            easing = FastOutSlowInEasing
        ),
        label = "animation for progress"
    )

    var shouldScale by remember { mutableStateOf(false) }

    val scale by animateFloatAsState(
        targetValue = if (shouldScale) 1.5f else 1f,
        animationSpec = tween(
            durationMillis = 300,
            easing = FastOutSlowInEasing
        ),
        label = "animation for scaling"
    )

    LaunchedEffect(progress, isLoading) {
        if (progress >= 1f && isLoading) {
            delay(100)
            shouldScale = true
            delay(400)
        }
    }

    LaunchedEffect(isLoading) {
        if (!isLoading) {
            shouldScale = false
        }
    }

    val initials = remember(name) {
        if (name.isBlank()) {
            ""
        } else {
            val words = name.trim().split("\\s+".toRegex())
            when {
                words.size == 1 -> words[0].take(1).uppercase()
                words.size >= 2 -> (words[0].take(1) + words[1].take(1)).uppercase()
                else -> ""
            }
        }
    }

    val primaryColor = MaterialTheme.colorScheme.primary

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
    ) {

        Box(
            modifier = Modifier
                .size(size)
                .let { baseModifier ->
                    if(sharedTransitionScope != null && animatedVisibilityScope != null) {
                        with(sharedTransitionScope) {
                            baseModifier.sharedElement(
                                sharedContentState = rememberSharedContentState(key = "user_avatar"),
                                animatedVisibilityScope = animatedVisibilityScope,
                                boundsTransform = { _, _ ->
                                    tween(durationMillis = 800, easing = FastOutSlowInEasing)
                                }
                            )
                        }
                    } else {
                        baseModifier
                    }
                }.graphicsLayer {
                    scaleX = scale
                    scaleY = scale
                },
            contentAlignment = Alignment.Center
        ) {
            Box(
                modifier = Modifier
                    .size(size)
                    .clip(CircleShape)
                    .background(color)
                    .border(3.dp, Color.White, CircleShape),
                contentAlignment = Alignment.Center
            ) {
                if (initials.isNotEmpty()) {
                    Text(
                        text = initials,
                        color = Color.White,
                        fontSize = size.value.div(2.5).sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
            if (isLoading) {
                Canvas(modifier = Modifier.size(size + 6.dp)) {
                    val strokeWidth = 4.dp.toPx()
                    drawCircle(
                        color = primaryColor.copy(alpha = 0.3f),
                        radius = (size.toPx() + 6.dp.toPx()) / 2,
                        style = Stroke(strokeWidth)
                    )

                    drawArc(
                        color = Color.White,
                        startAngle = -90f,
                        sweepAngle = 360f * progress,
                        useCenter = false,
                        style = Stroke(
                            width = strokeWidth,
                            cap = StrokeCap.Round
                        ),
                        size = Size(size.toPx() + 6.dp.toPx(), size.toPx() + 6.dp.toPx()),
                        topLeft = Offset(
                            -3.dp.toPx(),
                            -3.dp.toPx()
                        )
                    )
                }
            }
        }
        Spacer(modifier = Modifier.height(4.dp))
        if (shouldShowName && name.isNotBlank()) {
            Text(
                text = name,
                color = Color.White,
                fontSize = 12.sp,
                textAlign = TextAlign.Center
            )
        }
    }
}