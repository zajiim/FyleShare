package com.fyndr.fileshare.presentation.send_or_receive

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PaintingStyle.Companion.Stroke
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlin.math.cos
import kotlin.math.sin


@Composable
fun SendOrReceiveScreen(
    modifier: Modifier = Modifier,
    avatarPositions: List<AvatarPosition> = emptyList()
) {
    val infiniteTransition = rememberInfiniteTransition(label = "radar_rotation")
    val angle by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(3000, easing = LinearEasing), // 3 seconds per rotation
            repeatMode = RepeatMode.Restart
        ),
        label = "radar_sweep"
    )

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(
                Brush.radialGradient(
                    colors = listOf(
                        Color(0xFF2A2A2A),
                        Color(0xFF1A1A1A),
                        Color(0xFF0D0D0D)
                    )
                )
            ),
        contentAlignment = Alignment.Center
    ) {
        Canvas(
            modifier = Modifier.fillMaxSize()
        ) {
            val center = Offset(size.width / 2f, size.height / 2f)
            val maxRadius = minOf(size.width, size.height) / 2f * 0.9f

            // Draw radar circles (range rings)
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

            // Draw crosshair lines
            drawLine(
                color = Color(0xFF404040),
                start = Offset(0f, center.y),
                end = Offset(size.width, center.y),
                strokeWidth = 1.5.dp.toPx()
            )
            drawLine(
                color = Color(0xFF404040),
                start = Offset(center.x, 0f),
                end = Offset(center.x, size.height),
                strokeWidth = 1.5.dp.toPx()
            )

            // Draw radar sweep (the bright rotating sector)
            val sweepAngle = 60f // Width of the sweep in degrees
            val sweepGradient = Brush.sweepGradient(
                colors = listOf(
                    Color.Transparent,
                    Color(0x33FFFFFF),
                    Color(0x66FFFFFF),
                    Color(0x99FFFFFF),
                    Color.Transparent
                ),
                center = center
            )

            // Rotate the sweep gradient
            rotate(angle, pivot = center) {
                drawArc(
                    brush = sweepGradient,
                    startAngle = -sweepAngle / 2f,
                    sweepAngle = sweepAngle,
                    useCenter = true,
                    topLeft = Offset(center.x - maxRadius, center.y - maxRadius),
                    size = Size(maxRadius * 2, maxRadius * 2)
                )
            }

            // Draw the bright sweep line
            val sweepLineAngle = Math.toRadians(angle.toDouble())
            val lineEnd = Offset(
                center.x + maxRadius * cos(sweepLineAngle).toFloat(),
                center.y + maxRadius * sin(sweepLineAngle).toFloat()
            )

            drawLine(
                color = Color(0xFFFFFFFF),
                start = center,
                end = lineEnd,
                strokeWidth = 3.dp.toPx()
            )
        }

        // Draw user avatars at specified positions
        avatarPositions.forEach { avatarPos ->
            UserAvatar(
                name = avatarPos.name,
                color = avatarPos.color,
                modifier = Modifier
                    .offset(avatarPos.x.dp, avatarPos.y.dp)
                    .size(60.dp)
            )
        }
    }
}

data class AvatarPosition(
    val name: String,
    val color: Color,
    val x: Int, // X offset from center in dp
    val y: Int  // Y offset from center in dp
)


@Composable
fun UserAvatar(
    name: String,
    color: Color,
    modifier: Modifier = Modifier
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
    ) {
        Box(
            modifier = Modifier
                .size(50.dp)
                .clip(CircleShape)
                .background(color)
                .border(3.dp, Color.White, CircleShape),
            contentAlignment = Alignment.Center
        ) {
            // You can add profile picture here or use initials
            Text(
                text = name.take(1).uppercase(),
                color = Color.White,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )
        }
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = name,
            color = Color.White,
            fontSize = 12.sp,
            textAlign = TextAlign.Center
        )
    }
}

