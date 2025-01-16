package com.example.presentation.history

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.presentation.R
import com.example.utils.DateTimeUtils

@Composable

fun HistoryScreen(
    viewModel: HistoryViewModel
) {
    Scaffold(
        topBar = { SetTopAppBar() }
    ) { innerPadding ->
        if (viewModel.state.isLoading) {
            SetProgressIndicator(
                innerPadding = innerPadding
            )
        } else {
            SetContent(
                viewModel = viewModel,
                innerPadding = innerPadding
            )
        }
    }
}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun SetTopAppBar() {
    CenterAlignedTopAppBar(
        title = {
            Text(
                text = stringResource(R.string.history)
            )
        }
    )
}

@Composable
fun SetProgressIndicator(
    innerPadding: PaddingValues
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(innerPadding),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator(
            modifier = Modifier
                .size(100.dp),
            strokeWidth = 5.dp,
            color = MaterialTheme.colorScheme.primary
        )
    }
}

@Composable
fun SetContent(
    viewModel: HistoryViewModel,
    innerPadding: PaddingValues
) {
    Box(
        modifier = Modifier
            .padding(innerPadding)
    ) {
        LazyColumn(
            modifier = Modifier
                .padding(horizontal = 10.dp)
        ) {
            items(viewModel.state.ticTacToeMatches) { ticTacToeMatch ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Column {
                        Text(
                            text = "Date time"
                        )
                        Text(
                            text = DateTimeUtils.formatLocalDateTimeToDate(ticTacToeMatch.startDateTime)
                        )
                        Text(
                            text = DateTimeUtils.formatLocalDateTimeToTime(ticTacToeMatch.startDateTime)
                        )
                    }

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
                            text = stringResource(id = R.string.win_counter, ticTacToeMatch.hostWins)
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
                            text = stringResource(id = R.string.win_counter, ticTacToeMatch.guestWins)
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
                            text = stringResource(id = R.string.draw_counter, ticTacToeMatch.draws)
                        )
                    }
                }
                HorizontalDivider()
            }
        }
    }
}
