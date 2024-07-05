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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.miracle.ui.theme.TGramTheme
import com.miracle.ui.theme.lSpacing
import com.miracle.ui.theme.mColors
import com.miracle.ui.theme.mShapes
import com.miracle.ui.theme.mTypography

@Composable
fun InputCodeScreen(
    modifier: Modifier = Modifier,
    code: String,
    onCodeChange: (String) -> Unit = {},
    setCode: () -> Unit = {}
) {
    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = setCode,
                shape = CircleShape
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                    contentDescription = null,
                    tint = mColors.onSurface
                )
            }
        },
        modifier = modifier
    ) {
        Column(
            modifier = Modifier
                .padding(it)
                .fillMaxSize()
                .padding(lSpacing.extraLarge),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = stringResource(id = R.string.check_your_email),
                style = mTypography.titleMedium,
                fontWeight = FontWeight.Medium,
                color = mColors.onSurface,
            )

            Spacer(Modifier.height(lSpacing.small))

            Text(
                text = stringResource(id = R.string.please_enter_code),
                style = mTypography.titleSmall,
                color = mColors.secondary,
                textAlign = TextAlign.Center,
                modifier = Modifier.sizeIn(maxWidth = 220.dp)
            )

            Spacer(Modifier.height(lSpacing.large))


            OutlinedTextField(
                value = code,
                onValueChange = onCodeChange,
                shape = mShapes.medium,
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = mColors.primary,
                    focusedLabelColor = mColors.primary,
                    focusedTextColor = mColors.onSurface,
                    unfocusedLabelColor = mColors.onSurfaceVariant,
                    unfocusedTextColor = mColors.onSurfaceVariant
                ),
                label = {
                    Text(
                        text = stringResource(id = R.string.code),
                        style = mTypography.titleSmall,
                    )
                },
                textStyle = mTypography.titleMedium,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun InputCodeScreenPreview() {
    TGramTheme {
        InputCodeScreen(code = "232")
    }
}