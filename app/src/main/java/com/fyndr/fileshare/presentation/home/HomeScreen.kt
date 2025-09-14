package com.fyndr.fileshare.presentation.home

import android.widget.Space
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.fyndr.fileshare.ui.theme.DarkBackground
import com.fyndr.fileshare.ui.theme.DarkSurface

@Composable
fun HomeScreen(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(DarkBackground),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            text = "Fyle Share",
            style = MaterialTheme.typography.headlineLarge.copy(
                fontWeight = FontWeight.Bold,
                fontSize = 24.sp,
                color = MaterialTheme.colorScheme.onPrimary
            ),
        )
        Spacer(modifier = Modifier.weight(1f))

        CustomButton(
            text = "Send",
            onClick = {}
        )

        Spacer(modifier = Modifier.height(16.dp))


        CustomButton(
            text = "Receive",
            onClick = {}
        )
        Spacer(modifier = Modifier.weight(1f))


        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .fillMaxHeight(0.4f)
                .clip(RoundedCornerShape(24.dp))
                .background(DarkSurface)
        ) {

        }

        Spacer(modifier = Modifier.weight(0.5f))


    }
}


@Preview(showBackground = true)
@Composable
private fun PreviewHomeScreen() {
    HomeScreen()
}

@Preview
@Composable
private fun CustomButtonPreview() {
    CustomButton(text = "Sample") {
    }
}


@Composable
fun CustomButton(
    modifier: Modifier = Modifier,
    text: String,
    onClick: () -> Unit
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .height(64.dp)
            .padding(4.dp)
            .clip(RoundedCornerShape(24.dp))
            .background(DarkSurface)
            .clickable {
                onClick()
            }
    ) {
        Text(
            modifier = Modifier.align(Alignment.Center),
            text = text,
            color = Color.White,
        )
    }
}