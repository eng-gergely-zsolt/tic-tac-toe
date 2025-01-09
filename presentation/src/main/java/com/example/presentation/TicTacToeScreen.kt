package com.example.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicText
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun TicTacToeScreen() {
    val nrOfColumns = 4
    val screenWidthDp = LocalConfiguration.current.screenWidthDp.dp
    val screenHeightDp = LocalConfiguration.current.screenHeightDp.dp
    val squareSize: Dp = screenWidthDp / nrOfColumns - 8.dp

    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
        LazyVerticalGrid(
            columns = GridCells.Fixed(nrOfColumns),
            modifier = Modifier
                .fillMaxSize()
                .padding(
                    end = 8.dp,
                    start = 8.dp,
                    top = (screenHeightDp - screenWidthDp) / 2
                ),
            contentPadding = innerPadding,
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(nrOfColumns * nrOfColumns) { index ->
                Box(
                    modifier = Modifier
                        .size(squareSize)
                        .background(Color.Blue, RoundedCornerShape(8.dp))
                        .clickable {  }
                ) {
                }
            }
        }
    }
}
