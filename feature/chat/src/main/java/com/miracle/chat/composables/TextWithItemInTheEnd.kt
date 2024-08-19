package com.miracle.chat.composables

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.SubcomposeLayout
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.miracle.ui.theme.TGramTheme
import com.miracle.ui.theme.mColors
import com.miracle.ui.theme.mTypography
import java.lang.Integer.max
import kotlin.math.roundToInt

@Composable
fun TextWithItemInTheEnd(
    text: String,
    textColor: Color,
    modifier: Modifier = Modifier,
    fillParentWidth: Boolean = false,
    textStyle: TextStyle = LocalTextStyle.current,
    endItem: @Composable () -> Unit
) {
    val textMeasurer = rememberTextMeasurer()

    SubcomposeLayout(
        modifier = modifier
    ) { constraints ->
        val endItemPlaceable = subcompose("endItem", endItem).map {
            it.measure(constraints)
        }.firstOrNull()

        val textMeasurementResult = textMeasurer.measure(
            text = text, constraints = constraints, style = textStyle
        )

        val textPlaceable = subcompose("textItem") {
            Canvas(
                modifier = Modifier.size(
                    textMeasurementResult.size.width.toDp(),
                    textMeasurementResult.size.height.toDp()
                )
            ) {
                textMeasurementResult.multiParagraph.paint(
                    canvas = drawContext.canvas,
                    color = textColor
                )
            }
        }.first().measure(constraints)


        val endItemWidth = endItemPlaceable?.width ?: 0
        val endItemHeight = endItemPlaceable?.height ?: 0

        val lastLineIndex = textMeasurementResult.lineCount - 1
        val lastLineWidth =
            (textMeasurementResult.getLineRight(lastLineIndex) - textMeasurementResult.getLineLeft(
                lastLineIndex
            )).roundToInt()

        val singleLine = textMeasurementResult.lineCount == 1

        val width = when {
            fillParentWidth -> constraints.maxWidth

            singleLine -> {
                if (lastLineWidth + endItemWidth <= constraints.maxWidth)
                    lastLineWidth + endItemWidth
                else max(lastLineWidth, endItemWidth)
            }

            else -> textPlaceable.width
        }

        val height = if (lastLineWidth + endItemWidth <= width)
            textPlaceable.height
        else textPlaceable.height + endItemHeight

        layout(width, height) {
            textPlaceable.place(0, 0)

            endItemPlaceable?.place(
                x = width - endItemWidth,
                y = height - endItemHeight
            )
        }
    }
}

@Preview
@Composable
private fun TextWithItemInTheEndPreview() {
    val sampleText =
        "Если бы я стал утверждать, что между Землёй и Марсом вокруг Солнца по эллиптической орбите вращается фарфоровый чайник, никто не смог бы опровергнуть моё утверждение, добавь я предусмотрительно, что чайник слишком мал, чтобы обнаружить его даже при помощи самых мощных телескопов. "

    val samples = listOf(
        sampleText.take(10),
        sampleText.take(66),
        sampleText.take(137),
    )
    TGramTheme {

        Column(Modifier.width(300.dp)) {
            samples.forEach { sample ->
                Box(
                    Modifier
                        .padding(5.dp)
                        .background(mColors.surfaceContainer, RoundedCornerShape(20.dp))
                ) {
                    TextWithItemInTheEnd(
                        modifier = Modifier
                            .padding(7.dp),
                        text = sample,
                        textColor = mColors.onSurface
                    ) {
                        Text(
                            text = "12:54",
                            style = mTypography.bodyMedium,
                            color = mColors.secondary,
                            modifier = Modifier.padding(start = 6.dp)
                        )
                    }
                }
            }

        }

    }
}