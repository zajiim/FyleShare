package com.fyndr.fileshare.presentation.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
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
import com.fyndr.fileshare.domain.home.models.itemList
import com.fyndr.fileshare.presentation.home.components.CustomButton
import com.fyndr.fileshare.presentation.home.components.CustomMediaItem
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
//                .fillMaxHeight(0.4f)
                .wrapContentHeight()
                .heightIn(min = 180.dp, max = 250.dp)
                .clip(RoundedCornerShape(24.dp))
                .background(DarkSurface),
            contentAlignment = Alignment.Center
        ) {

            LazyVerticalGrid(
                columns = GridCells.Fixed(3),
                modifier = Modifier
                    .wrapContentHeight()
                    .fillMaxHeight()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                items(itemList) { mediaItem ->
                    CustomMediaItem(
                        itemName = mediaItem.mediaTypeName,
                        itemImage = mediaItem.mediaTypeIcon,
                        onItemClick = {
                            println("Clicked on ${mediaItem.mediaTypeName}")
                        }
                    )
                }
            }


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


