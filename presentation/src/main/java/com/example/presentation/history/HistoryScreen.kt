package com.example.presentation.history

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.format.DateTimeFormatter

@Composable
fun HistoryScreen() {
    Scaffold { innerPadding ->
        Box(
            modifier = Modifier
                .padding(innerPadding)
        ) {
            LazyColumn(
                modifier = Modifier
                    .padding(horizontal = 10.dp)
            ) {
                items(50) { item ->
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
                                text = LocalDate.now().toString()
                            )
                            Text(
                                text = LocalTime.now().withSecond(0).withNano(0).toString()
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
                                text = stringResource(id = R.string.win_counter, 6)
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
                                text = stringResource(id = R.string.win_counter, "11")
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
                                text = stringResource(id = R.string.draw_counter, "3")
                            )
                        }
                    }
                    HorizontalDivider()
                }
            }
        }
    }
}
