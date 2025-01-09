package com.example.presentation

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import kotlin.math.pow

@Composable
fun TicTacToeScreen() {

    val nrOfColumns = 3

    var rowMultiplier = 1

    val screenWidthDp = LocalConfiguration.current.screenWidthDp.dp

    val screenHeightDp = LocalConfiguration.current.screenHeightDp.dp

    val squareSize: Dp = screenWidthDp / nrOfColumns - 8.dp

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.Green)
    ) { innerPadding ->
        Surface(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            Column(
                verticalArrangement = Arrangement.SpaceEvenly
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Icon(
                            modifier = Modifier
                                .size(50.dp),
                            contentDescription = "",
                            tint = Color(0xFF417DC1),
                            painter = painterResource(R.drawable.material_symbols_outlined_close)
                        )
                        Text("4 wins")
                    }
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Icon(
                            modifier = Modifier
                                .size(50.dp),
                            contentDescription = "",
                            tint = Color(0xFF4BC7CF),
                            painter = painterResource(R.drawable.material_symbols_outlined_circle)
                        )
                        Text("2 wins")
                    }
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Icon(
                            modifier = Modifier
                                .size(50.dp),
                            tint = Color.LightGray,
                            contentDescription = "",
                            painter = painterResource(R.drawable.material_symbols_outlined_balance)
                        )
                        Text("4 draws")
                    }
                }

                LazyVerticalGrid(
                    columns = GridCells.Fixed(nrOfColumns),
                    modifier = Modifier
                        .padding(
                            end = 8.dp,
                            start = 8.dp
                        ),
                ) {
                    items(nrOfColumns.toDouble().pow(2).toInt()) { index ->
                        if (index == rowMultiplier * nrOfColumns) {
                            ++rowMultiplier
                        }

                        var modifier = Modifier
                            .size(squareSize)
                            .background(Color.White)

                        if (index != (rowMultiplier * nrOfColumns) - nrOfColumns) {
                            modifier =
                                modifier.leftBorder(strokeWidth = 2.dp, color = Color.LightGray)
                        }

                        if (rowMultiplier != nrOfColumns) {
                            modifier =
                                modifier.bottomBorder(strokeWidth = 2.dp, color = Color.LightGray)
                        }

                        Box(
                            modifier = modifier,
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                modifier = Modifier
                                    .size((squareSize.times(0.7F))),
                                contentDescription = "",
                                tint = Color(0xFF417DC1),
                                painter = painterResource(R.drawable.material_symbols_outlined_circle)
                            )
                        }
                    }
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    Box(
                        modifier = Modifier
                            .size(55.dp)
                            .clip(CircleShape)
                            .background(Color.LightGray),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            modifier = Modifier
                                .size(32.dp),
                            tint = Color.White,
                            contentDescription = "Favorite Icon",
                            painter = painterResource(R.drawable.material_symbols_outlined_arrow_back)
                        )
                    }
                    OutlinedButton(
                        modifier = Modifier
                            .width(130.dp),
                        onClick = { },
                        shape = RoundedCornerShape(50),
                        border = BorderStroke(1.dp, Color.LightGray),
                        colors = ButtonDefaults.outlinedButtonColors(contentColor = Color.Gray)
                    ) {
                        Text(text = "Host")
                    }
                    Box(
                        modifier = Modifier
                            .size(55.dp)
                            .clip(CircleShape)
                            .background(Color.LightGray),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            modifier = Modifier
                                .size(32.dp),
                            tint = Color.White,
                            contentDescription = "Favorite Icon",
                            painter = painterResource(R.drawable.material_symbols_outlined_reset_settings)
                        )
                    }
                }
            }
        }
    }
}

/**
 * Sets a left border in the modifier it's called upon.
 * @param color The color of the created border.
 * @param strokeWidth The width of the created border.
 */
fun Modifier.leftBorder(color: Color, strokeWidth: Dp) = composed(
    factory = {
        val density = LocalDensity.current

        val strokeWidthPx = density.run { strokeWidth.toPx() }

        Modifier.drawBehind {
            drawLine(
                color = color,
                strokeWidth = strokeWidthPx,
                start = Offset(x = 0f, y = 0f),
                end = Offset(x = 0f, y = size.height)
            )
        }
    }
)

/**
 * Sets a bottom border in the modifier it's called upon.
 * @param color The color of the created border.
 * @param strokeWidth The width of the created border.
 */
fun Modifier.bottomBorder(color: Color, strokeWidth: Dp) = composed(
    factory = {
        val density = LocalDensity.current

        val strokeWidthPx = density.run { strokeWidth.toPx() }

        Modifier.drawBehind {
            val width = size.width

            val height = size.height - strokeWidthPx / 2

            drawLine(
                color = color,
                strokeWidth = strokeWidthPx,
                start = Offset(x = 0f, y = height),
                end = Offset(x = width, y = height)
            )
        }
    }
)
