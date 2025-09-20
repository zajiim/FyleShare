package com.fyndr.fileshare.presentation.send_or_receive

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
import com.fyndr.fileshare.presentation.components.UserAvatar

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun SendOrReceiveScreen(
    modifier: Modifier = Modifier, avatarPositions: List<AvatarPosition> = getSampleAvatars()
) {
    val infiniteTransition = rememberInfiniteTransition(label = "radar_rotation")
    val angle by infiniteTransition.animateFloat(
        initialValue = 0f, targetValue = 360f, animationSpec = infiniteRepeatable(
            animation = tween(6000, easing = LinearEasing), repeatMode = RepeatMode.Restart
        ), label = "radar_sweep"
    )

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

            rotate(angle, pivot = center) {
                val torchGradient = Brush.radialGradient(
                    colors = listOf(
                        Color(0xFFFFFFFF), // Bright white at center
                        Color(0xCCFFFFFF), // Slightly less bright
                        Color(0x99FFFFFF), // Medium brightness
                        Color(0x66FFFFFF), // Dimmer
                        Color(0x33FFFFFF), // Very dim
                        Color(0x11FFFFFF), // Almost transparent
                        Color.Transparent  // Completely transparent at edge
                    ), center = center, radius = maxRadius
                )

                // Draw the main torch sector
                drawArc(
                    brush = torchGradient,
                    startAngle = -20f, // Narrower torch beam
                    sweepAngle = 40f,  // 40-degree wide beam
                    useCenter = true,
                    topLeft = Offset(center.x - maxRadius, center.y - maxRadius),
                    size = Size(maxRadius * 2, maxRadius * 2)
                )

                // Add a bright center line for more realistic torch effect
//                val lineAngle = Math.toRadians(angle.toDouble())
//                val lineEnd = Offset(
//                    center.x + maxRadius * cos(lineAngle).toFloat(),
//                    center.y + maxRadius * sin(lineAngle).toFloat()
//                )

//                drawLine(
//                    color = Color(0xFFFFFFFF),
//                    start = center,
//                    end = lineEnd,
//                    strokeWidth = 4.dp.toPx(),
//                    cap = StrokeCap.Round
//                )

//                val sideFadeGradient = Brush.radialGradient(
//                    colors = listOf(
//                        Color.Transparent, Color(0x22FFFFFF), Color(0x11FFFFFF), Color.Transparent
//                    ), center = center, radius = maxRadius * 1.2f
//                )

//                drawArc(
//                    brush = sideFadeGradient,
//                    startAngle = -35f,
//                    sweepAngle = 15f,
//                    useCenter = true,
//                    topLeft = Offset(center.x - maxRadius, center.y - maxRadius),
//                    size = Size(maxRadius * 2, maxRadius * 2)
//                )

//                drawArc(
//                    brush = sideFadeGradient,
//                    startAngle = 20f,
//                    sweepAngle = 15f,
//                    useCenter = true,
//                    topLeft = Offset(center.x - maxRadius, center.y - maxRadius),
//                    size = Size(maxRadius * 2, maxRadius * 2)
//                )
            }
        }

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
        ), AvatarPosition(
            name = "Roger", color = Color(0xFFFFE66D), x = 110, y = -60
        ), AvatarPosition(
            name = "Marilyn", color = Color(0xFF4ECDC4), x = -90, y = 100
        ), AvatarPosition(
            name = "Alex", color = Color(0xFF6C5CE7), x = 80, y = 120
        ), AvatarPosition(
            name = "Sarah", color = Color(0xFF00B894), x = -140, y = 30
        )
    )
}


