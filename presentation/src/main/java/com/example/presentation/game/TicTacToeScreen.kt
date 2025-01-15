package com.example.presentation.game

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.core.domain.model.Player
import com.example.presentation.R
import kotlin.math.pow
import com.example.presentation.game.TicTacToeSideEffect.Navigation.NavigateBack

@Composable
fun TicTacToeScreen(
    boardSize: Int = 3,
    viewModel: TicTacToeViewModel,
    onNavigation: ((TicTacToeSideEffect.Navigation) -> Unit)
) {

    var rowMultiplier = 1

    val screenWidthDp = LocalConfiguration.current.screenWidthDp.dp

    val squareSize: Dp = screenWidthDp / boardSize - 8.dp

    viewModel.boardSize = boardSize

    LaunchedEffect(Unit) {
        viewModel.initializeBoard(boardSize)
    }

    viewModel.state.winner?.let { winner ->
        EndGameDialog(
            winner = winner,
            onNavigation = onNavigation,
            onReplay = { viewModel.replay() },
            onDismiss = { viewModel.dismissDialog() }
        )
    }

    if (viewModel.state.isDraw) {
        EndGameDialog(
            onNavigation = onNavigation,
            onReplay = { viewModel.replay() },
            onDismiss = { viewModel.dismissDialog() }
        )
    }

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
                            tint = MaterialTheme.colorScheme.secondary,
                            contentDescription = stringResource(R.string.host_icon_description),
                            painter = painterResource(R.drawable.material_symbols_outlined_close)
                        )

                        Text(
                            text = stringResource(id = R.string.win_counter, viewModel.state.hostWins)
                        )
                    }

                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Icon(
                            modifier = Modifier
                                .size(50.dp),
                            tint = MaterialTheme.colorScheme.tertiary,
                            contentDescription = stringResource(R.string.guest_icon_description),
                            painter = painterResource(R.drawable.material_symbols_outlined_circle)
                        )

                        Text(
                            text = stringResource(id = R.string.win_counter, viewModel.state.guestWins)
                        )
                    }

                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Icon(
                            modifier = Modifier
                                .size(50.dp),
                            tint = Color.LightGray,
                            contentDescription = stringResource(R.string.draw_icon_description),
                            painter = painterResource(R.drawable.material_symbols_outlined_balance)
                        )

                        Text(
                            text = stringResource(id = R.string.draw_counter, viewModel.state.draws)
                        )
                    }
                }

                LazyVerticalGrid(
                    columns = GridCells.Fixed(boardSize),
                    modifier = Modifier
                        .padding(
                            end = 8.dp,
                            start = 8.dp
                        ),
                ) {
                    items(boardSize.toDouble().pow(2).toInt()) { indexOfField ->
                        if (indexOfField == rowMultiplier * boardSize) {
                            ++rowMultiplier
                        }

                        var modifier = Modifier
                            .size(squareSize)
                            .background(Color.White)

                        // Set the left borders of the fields.
                        if (indexOfField != (rowMultiplier * boardSize) - boardSize) {
                            modifier =
                                modifier.leftBorder(strokeWidth = 2.dp, color = Color.LightGray)
                        }

                        // Set the bottom borders of the fields.
                        if (rowMultiplier != boardSize) {
                            modifier =
                                modifier.bottomBorder(strokeWidth = 2.dp, color = Color.LightGray)
                        }

                        // Reset row multiplier to keep border upon recomposition.
                        if (indexOfField == boardSize.toDouble().pow(2).toInt() - 1) {
                            rowMultiplier = 1
                        }

                        Box(
                            modifier = modifier
                                .clickable { viewModel.takeField(indexOfField) },
                            contentAlignment = Alignment.Center,
                        ) {
                            val field = viewModel.state.board.find { it.index == indexOfField }

                            field?.let {
                                if (field.player == Player.Host) {
                                    Icon(
                                        modifier = Modifier
                                            .size((squareSize.times(0.7F))),
                                        tint = MaterialTheme.colorScheme.secondary,
                                        contentDescription = stringResource(R.string.host_icon_description),
                                        painter = painterResource(R.drawable.material_symbols_outlined_close)
                                    )
                                } else {
                                    Icon(
                                        modifier = Modifier
                                            .size((squareSize.times(0.7F))),
                                        tint = MaterialTheme.colorScheme.tertiary,
                                        contentDescription = stringResource(R.string.guest_icon_description),
                                        painter = painterResource(R.drawable.material_symbols_outlined_circle)
                                    )
                                }
                            }

                        }
                    }
                }

                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    FilledIconButton(
                        modifier = Modifier
                            .size(55.dp),
                        onClick = {
                            onNavigation.invoke(NavigateBack)
                        },
                        colors = IconButtonDefaults.filledIconButtonColors(
                            containerColor = MaterialTheme.colorScheme.primary
                        ),
                        shape = IconButtonDefaults.filledShape
                    ) {
                        Icon(
                            modifier = Modifier
                                .size(32.dp),
                            tint = Color.White,
                            contentDescription = stringResource(R.string.back_icon_description),
                            painter = painterResource(R.drawable.material_symbols_outlined_arrow_back)
                        )
                    }

                    OutlinedButton(
                        modifier = Modifier
                            .width(130.dp),
                        onClick = { },
                        shape = RoundedCornerShape(50),
                        border = BorderStroke(2.dp, MaterialTheme.colorScheme.primary),
                        colors = ButtonDefaults.outlinedButtonColors(contentColor = Color.Gray)
                    ) {
                        Text(
                            color = MaterialTheme.colorScheme.primary,
                            text = viewModel.state.currentPlayer.name
                        )
                    }

                    FilledIconButton(
                        modifier = Modifier
                            .size(55.dp),
                        onClick = { viewModel.resetBoard() },
                        colors = IconButtonDefaults.filledIconButtonColors(
                            containerColor = MaterialTheme.colorScheme.primary
                        ),
                        shape = IconButtonDefaults.filledShape
                    ) {
                        Icon(
                            modifier = Modifier
                                .size(32.dp),
                            tint = Color.White,
                            contentDescription = stringResource(R.string.reset_game_icon_description),
                            painter = painterResource(R.drawable.material_symbols_outlined_reset_settings)
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun EndGameDialog(
    onReplay: () -> Unit,
    onDismiss: () -> Unit,
    winner: Player? = null,
    onNavigation: (TicTacToeSideEffect.Navigation) -> Unit
) {
    Dialog(
        onDismissRequest = {}
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 30.dp),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.background
            )
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    modifier = Modifier
                        .padding(vertical = 20.dp),
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    text = if (winner == null) stringResource(R.string.draw)
                    else stringResource(id = R.string.end_game_title, winner.name),
                )

                Button(
                    modifier = Modifier
                        .padding(bottom = 20.dp),
                    onClick = {
                        onReplay()
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.primary
                    )
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            modifier = Modifier
                                .size(32.dp),
                            tint = Color.White,
                            painter = painterResource(R.drawable.material_symbols_outlined_replay),
                            contentDescription = stringResource(R.string.replay_icon_description)
                        )

                        Spacer(Modifier.width(10.dp))

                        Text(
                            text = stringResource(R.string.replay)
                        )
                    }
                }

                OutlinedButton(
                    modifier = Modifier
                        .padding(bottom = 20.dp),
                    onClick = {
                        onDismiss()
                        onNavigation.invoke(NavigateBack)
                    },
                    border = BorderStroke(2.dp, MaterialTheme.colorScheme.primary),
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            modifier = Modifier
                                .size(32.dp),
                            imageVector = Icons.Filled.Home,
                            tint = MaterialTheme.colorScheme.primary,
                            contentDescription = stringResource(R.string.home_icon_description),
                        )

                        Spacer(Modifier.width(10.dp))

                        Text(
                            text = stringResource(R.string.home)
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
