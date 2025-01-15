package com.example.presentation.landing

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.presentation.R
import com.example.presentation.landing.LandingSideEffect.Navigation.NavigateToHistory
import com.example.presentation.landing.LandingSideEffect.Navigation.NavigateToTicTacToe

@Composable
fun LandingScreen(
    onNavigation: ((navigationEffect: LandingSideEffect.Navigation) -> Unit)
) {
    Scaffold(
        modifier = Modifier
            .background(Color.Red)
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .padding(innerPadding)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(30.dp)
                    .background(MaterialTheme.colorScheme.background),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    textAlign = TextAlign.Center,
                    text = stringResource(R.string.landing_choose_board_size)
                )

                Spacer(Modifier.height(30.dp))

                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 50.dp),
                    shape = RectangleShape,
                    elevation = CardDefaults.elevatedCardElevation(10.dp),
                    onClick = {
                        onNavigation.invoke(NavigateToTicTacToe(3))
                    }
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 30.dp),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        Text(
                            text = stringResource(R.string.board_size_3)
                        )

                        Icon(
                            contentDescription = stringResource(R.string.forward_arrow_description),
                            painter = painterResource(R.drawable.material_symbols_outlined_arrow_forward)
                        )
                    }

                }

                Spacer(Modifier.height(30.dp))

                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 50.dp),
                    shape = RectangleShape,
                    elevation = CardDefaults.elevatedCardElevation(10.dp),
                    onClick = {
                        onNavigation.invoke(NavigateToTicTacToe(4))
                    }
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 30.dp),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        Text(
                            text = stringResource(R.string.board_size_4)
                        )
                        Icon(
                            contentDescription = stringResource(R.string.forward_arrow_description),
                            painter = painterResource(R.drawable.material_symbols_outlined_arrow_forward)
                        )
                    }

                }

                Spacer(Modifier.height(30.dp))

                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 50.dp),
                    shape = RectangleShape,
                    elevation = CardDefaults.elevatedCardElevation(10.dp),
                    onClick = {
                        onNavigation.invoke(NavigateToTicTacToe(5))
                    }
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 30.dp),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        Text(
                            text = stringResource(R.string.board_size_5)
                        )
                        Icon(
                            contentDescription = stringResource(R.string.forward_arrow_description),
                            painter = painterResource(R.drawable.material_symbols_outlined_arrow_forward)
                        )
                    }
                }

                Spacer(Modifier.height(30.dp))

                TextButton(
                    onClick = {
                        onNavigation.invoke(NavigateToHistory)
                    }
                ) {
                    Text(
                        fontSize = 18.sp,
                        text = stringResource(R.string.landing_see_history)
                    )
                }
            }
        }
    }
}
