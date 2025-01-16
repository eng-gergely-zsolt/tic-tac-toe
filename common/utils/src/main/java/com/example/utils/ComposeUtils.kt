package com.example.utils

import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp

object ComposeUtils {
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
}
