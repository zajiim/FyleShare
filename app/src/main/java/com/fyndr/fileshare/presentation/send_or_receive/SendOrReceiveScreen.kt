package com.fyndr.fileshare.presentation.send_or_receive

import android.util.Log
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.fyndr.fileshare.presentation.components.UserAvatar

private const val TAG = "SendOrReceiveScreen"
@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun SendOrReceiveScreen(
    modifier: Modifier = Modifier,
    avatarPositions: List<AvatarPosition> = getSampleAvatars(),
    viewModel: SendOrReceiveViewModel = hiltViewModel()
) {
    val state = viewModel.state.collectAsState()
    val infiniteTransition = rememberInfiniteTransition(label = "radar_rotation")
    val angle by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(6000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "radar_sweep"
    )
    Log.e(TAG, "the username is ${state.value.currentUserName}: ")

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(
                Brush.radialGradient(
                    colors = listOf(
                        Color(0xFF2A2A2A),
                        Color(0xFF1A1A1A),
                        Color(0xFF0D0D0D),
                    )
                )
            ), contentAlignment = Alignment.Center
    ) {
        Canvas(
            modifier = Modifier.fillMaxSize()
        ) {
            val center = Offset(size.width / 2f, size.height / 2f)
            val maxRadius = minOf(size.width, size.height) / 2f * 0.9f

            val numCircles = 4
            for (i in 1..numCircles) {
                val radius = (maxRadius / numCircles) * i
                drawCircle(
                    color = Color(0xFF404040),
                    radius = radius,
                    center = center,
                    style = Stroke(width = 1.5.dp.toPx())
                )
            }

            rotate(
                degrees = angle,
                pivot = center
            ) {
                val torchGradient = Brush.radialGradient(
                    colors = listOf(
                        Color(0xFFFFFFFF),
                        Color(0xCCFFFFFF),
                        Color(0x99FFFFFF),
                        Color(0x66FFFFFF),
                        Color(0x33FFFFFF),
                        Color(0x11FFFFFF),
                        Color.Transparent
                    ),
                    center = center,
                    radius = maxRadius
                )

                drawArc(
                    brush = torchGradient,
                    startAngle = -20f,
                    sweepAngle = 40f,
                    useCenter = true,
                    topLeft = Offset(center.x - maxRadius, center.y - maxRadius),
                    size = Size(maxRadius * 2, maxRadius * 2)
                )
            }
        }

        UserAvatar(
            name = state.value.currentUserName,
            color = Color(0xFF40C4FF),
            size = 60.dp,
            shouldShowName = false
        )

        avatarPositions.forEach { avatarPos ->
            UserAvatar(
                name = avatarPos.name,
                color = avatarPos.color,
                size = 50.dp,
                modifier = Modifier
                    .offset(avatarPos.x.dp, avatarPos.y.dp)
                    .size(60.dp)
            )
        }
    }
}


data class AvatarPosition(
    val name: String, val color: Color, val x: Int, val y: Int
)


fun getSampleAvatars(): List<AvatarPosition> {
    return listOf(
        AvatarPosition(
            name = "Justin", color = Color(0xFFFF6B9D), x = -120, y = -80
        ),
        AvatarPosition(
            name = "Roger", color = Color(0xFFFFE66D), x = 110, y = -60
        ),
        AvatarPosition(
            name = "Marilyn", color = Color(0xFF4ECDC4), x = -90, y = 100
        ),
        AvatarPosition(
            name = "Alex", color = Color(0xFF6C5CE7), x = 80, y = 120
        ),
        AvatarPosition(
            name = "Sarah", color = Color(0xFF00B894), x = -140, y = 30
        )
    )
}


