package com.miracle.authorization

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.miracle.ui.R
import com.miracle.ui.theme.LocalColorScheme
import com.miracle.ui.theme.LocalShapes
import com.miracle.ui.theme.LocalSpacing
import com.miracle.ui.theme.LocalTypography
import com.miracle.ui.theme.TGramThemeWithBack

@Composable
fun InputCodeScreen(
    modifier: Modifier = Modifier,
    code: String,
    onCodeChange: (String) -> Unit = {},
    setCode: () -> Unit = {}
) {
    val spacing = LocalSpacing.current
    val colors = LocalColorScheme.current

    Scaffold(
        containerColor = Color.Transparent,
        floatingActionButton = {
            FloatingActionButton(
                onClick = setCode,
                containerColor = colors.primary,
                shape = CircleShape
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                    contentDescription = null,
                    tint = colors.onSurface
                )
            }
        },
        modifier = modifier
    ) {
        Column(
            modifier = Modifier
                .padding(it)
                .fillMaxSize()
                .padding(LocalSpacing.current.extraLarge),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = stringResource(id = R.string.check_your_email),
                style = LocalTypography.current.titleMedium,
                fontWeight = FontWeight.Medium,
                color = colors.onSurface,
            )

            Spacer(Modifier.height(spacing.small))

            Text(
                text = stringResource(id = R.string.please_enter_code),
                style = LocalTypography.current.titleSmall,
                color = colors.onSurfaceVariant,
                textAlign = TextAlign.Center,
                modifier = Modifier.sizeIn(maxWidth = 220.dp)
            )

            Spacer(Modifier.height(spacing.large))


            OutlinedTextField(
                value = code,
                onValueChange = onCodeChange,
                shape = LocalShapes.current.medium,
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = colors.primary,
                    focusedLabelColor = colors.primary,
                    focusedTextColor = colors.onSurface,
                    unfocusedLabelColor = colors.onSurfaceVariant,
                    unfocusedTextColor = colors.onSurfaceVariant
                ),
                label = {
                    Text(
                        text = stringResource(id = R.string.code),
                        style = LocalTypography.current.titleSmall,
                    )
                },
                textStyle = LocalTypography.current.titleMedium,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun InputCodeScreenPreview() {
    TGramThemeWithBack {
        InputCodeScreen(code = "232")
    }
}