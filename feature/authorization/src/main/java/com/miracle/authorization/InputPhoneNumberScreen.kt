package com.miracle.authorization

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
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
fun InputPhoneNumberScreen(
    modifier: Modifier = Modifier,
    phoneNumber: String,
    onPhoneNumberChange: (String) -> Unit = {},
    setPhoneNumber: () -> Unit = {}
) {
    val spacing = LocalSpacing.current
    val colors = LocalColorScheme.current

    Scaffold(
        containerColor = Color.Transparent,
        floatingActionButton = {
            FloatingActionButton(
                onClick = setPhoneNumber,
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
                text = stringResource(id = R.string.your_phone_number),
                style = LocalTypography.current.titleMedium,
                fontWeight = FontWeight.Medium,
                color = colors.onSurface,
            )

            Spacer(Modifier.height(spacing.small))

            Text(
                text = stringResource(id = R.string.please_confirm_country_code),
                style = LocalTypography.current.titleSmall,
                color = colors.onSurfaceVariant,
                textAlign = TextAlign.Center,
                modifier = Modifier.sizeIn(maxWidth = 220.dp)
            )

            Spacer(Modifier.height(spacing.large))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .border(1.dp, colors.onSurfaceVariant, LocalShapes.current.medium)
                    .defaultMinSize(
                        minWidth = OutlinedTextFieldDefaults.MinWidth,
                        minHeight = OutlinedTextFieldDefaults.MinHeight
                    ),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = stringResource(id = R.string.country),
                    style = LocalTypography.current.titleMedium,
                    color = colors.onSurfaceVariant,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(spacing.medium)
                )

                Spacer(modifier = Modifier.weight(1f))

                Icon(
                    imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                    contentDescription = null,
                    tint = colors.onSurfaceVariant,
                    modifier = Modifier
                        .padding(end = spacing.medium)
                        .size(29.dp)
                )
            }

            Spacer(Modifier.height(spacing.medium))

            OutlinedTextField(
                value = phoneNumber,
                onValueChange = onPhoneNumberChange,
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
                        text = stringResource(id = R.string.phone_number),
                        style = LocalTypography.current.titleSmall,
                    )
                },
                textStyle = LocalTypography.current.titleMedium,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }


}

@Preview
@Composable
private fun InputPhoneNumberScreenPreview() {
    TGramThemeWithBack {
        InputPhoneNumberScreen(
            Modifier.padding(LocalSpacing.current.medium),
            phoneNumber = "09090909"
        )
    }
}