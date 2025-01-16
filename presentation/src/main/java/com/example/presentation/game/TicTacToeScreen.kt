package com.example.presentation.game

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.keyframes
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
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.core.domain.model.Player
import com.example.presentation.R
import com.example.presentation.game.TicTacToeSideEffect.Navigation.NavigateBack
import com.example.utils.ComposeUtils.bottomBorder
import com.example.utils.ComposeUtils.leftBorder
import kotlin.math.pow

@Composable
fun TicTacToeScreen(
    boardSize: Int = 3,
    viewModel: TicTacToeViewModel,
    onNavigation: ((TicTacToeSideEffect.Navigation) -> Unit)
) {
    viewModel.boardSize = boardSize

    LaunchedEffect(Unit) {
        viewModel.initializeBoard(boardSize)
    }

    viewModel.state.winner?.let { winner ->
        SetEndGameDialog(
            winner = winner,
            onNavigation = onNavigation,
            onReplay = { viewModel.replay() },
            onDismiss = { viewModel.dismissDialog() }
        )
    }

    if (viewModel.state.isDraw) {
        SetEndGameDialog(
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
                SetInGameTopBar(
                    viewModel = viewModel
                )

                SetGameBoard(
                    boardSize = boardSize,
                    viewModel = viewModel
                )

                SetInGameBottomBar(
                    viewModel = viewModel,
                    onNavigation = onNavigation
                )
            }
        }
    }
}

/**
 * Sets the bar to show the actual game status (wins, draws).
 * @param viewModel The viewmodel of the UI
 */
@Composable
fun SetInGameTopBar(
    viewModel: TicTacToeViewModel
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
}

/**
 * Sets the game board.
 * @param boardSize Defines how many tiles the board consists of (Ex.: 3x3, 4x4, 5x5).
 * @param viewModel The viewmodel of the UI
 */
@Composable
fun SetGameBoard(
    boardSize: Int,
    viewModel: TicTacToeViewModel
) {


    val screenWidthDp = LocalConfiguration.current.screenWidthDp.dp

    val squareSize: Dp = screenWidthDp / boardSize - 8.dp

    LazyVerticalGrid(
        columns = GridCells.Fixed(boardSize),
        modifier = Modifier
            .padding(
                end = 8.dp,
                start = 8.dp
            ),
    ) {
        items(boardSize.toDouble().pow(2).toInt()) { indexOfField ->
            val scale by animateFloatAsState(
                targetValue = if (viewModel.shouldAnimate(indexOfField)) 1.1f else 1f,
                animationSpec = keyframes {
                    durationMillis = 1000
                    1f at 0
                    1.5f at 500
                    1f at 1000
                }, label = ""
            )

            var modifier = Modifier
                .size(squareSize)
                .background(MaterialTheme.colorScheme.background)

            // Set the left borders of the fields.
            if (viewModel.state.borderMap[indexOfField]?.first == true) {
                modifier =
                    modifier.leftBorder(strokeWidth = 2.dp, color = Color.LightGray)
            }

            // Set the bottom borders of the fields.
            if (viewModel.state.borderMap[indexOfField]?.second == true) {
                modifier =
                    modifier.bottomBorder(strokeWidth = 2.dp, color = Color.LightGray)
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
                                .size((squareSize.times(0.7F)))
                                .graphicsLayer(
                                    scaleX = scale,
                                    scaleY = scale
                                ),
                            tint = MaterialTheme.colorScheme.secondary,
                            contentDescription = stringResource(R.string.host_icon_description),
                            painter = painterResource(R.drawable.material_symbols_outlined_close)
                        )
                    } else {
                        Icon(
                            modifier = Modifier
                                .size((squareSize.times(0.7F)))
                                .graphicsLayer(
                                    scaleX = scale,
                                    scaleY = scale
                                ),
                            tint = MaterialTheme.colorScheme.tertiary,
                            contentDescription = stringResource(R.string.guest_icon_description),
                            painter = painterResource(R.drawable.material_symbols_outlined_circle)
                        )
                    }
                }

            }
        }
    }
}

/**
 * Sets the in game bottom bar.
 * @param viewModel The viewmodel of the UI.
 * @param onNavigation The callback to set when navigation is needed.
 */
@Composable
fun SetInGameBottomBar(
    viewModel: TicTacToeViewModel,
    onNavigation: (TicTacToeSideEffect.Navigation) -> Unit
) {
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

/**
 * Sets the dialog when a match is over.
 * @param onReplay The callback to call when replay is needed.
 * @param onDismiss The callback to call when the dialog needs to be dismissed.
 * @param winner The winner [Player] of the match.
 * @param onNavigation The callback to set when navigation is needed.
 */
@Composable
fun SetEndGameDialog(
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
