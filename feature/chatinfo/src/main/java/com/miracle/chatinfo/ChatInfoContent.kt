package com.miracle.chatinfo

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import com.miracle.chatinfo.model.ChatInfo
import com.miracle.common.utils.formatPhoneNumber
import com.miracle.ui.theme.TGramTheme
import com.miracle.ui.theme.lSpacing
import com.miracle.ui.theme.mColors
import com.miracle.ui.theme.mTypography

@Composable
fun ChatInfoContent(
    phoneNumber: String,
    isMuted: Boolean,
    modifier: Modifier = Modifier,
    onMuteCheckClick: (Boolean) -> Unit = {},
) {
    Column(modifier = modifier) {
        Text(
            text = stringResource(id = R.string.info),
            color = mColors.primary,
            style = mTypography.bodyLarge,
            fontWeight = FontWeight.Medium
        )

        Spacer(modifier = Modifier.height(lSpacing.medium))
        Text(
            text = phoneNumber,
            color = mColors.onSurface,
            style = mTypography.bodyLarge,
        )
        Spacer(modifier = Modifier.height(lSpacing.small))

        Text(
            text = stringResource(id = R.string.mobile),
            color = mColors.secondary,
            style = mTypography.bodyMedium,
        )

        Spacer(modifier = Modifier.height(lSpacing.medium))

        Row(Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
            Column {
                Text(
                    text = stringResource(id = R.string.notifications),
                    color = mColors.onSurface,
                    style = mTypography.bodyLarge,
                )
                Spacer(modifier = Modifier.height(lSpacing.small))

                val muteStatusTitleRes = when {
                    isMuted -> R.string.on
                    else -> R.string.off
                }

                Text(
                    text = stringResource(id = muteStatusTitleRes),
                    color = mColors.secondary,
                    style = mTypography.bodyMedium,
                )
            }

            Spacer(modifier = Modifier.weight(1f))

            Switch(
                checked = isMuted,
                onCheckedChange = onMuteCheckClick,
                colors = SwitchDefaults.colors(
                    checkedThumbColor = mColors.surface,
                    checkedBorderColor = mColors.primary,
                    checkedTrackColor = mColors.primary,
                    uncheckedThumbColor = mColors.secondary,
                    uncheckedTrackColor = mColors.surface,
                    uncheckedBorderColor = mColors.secondary
                )
            )
        }
    }
}

@Preview
@Composable
private fun ChatInfoContentPrev() {
    TGramTheme {
        Surface {
            ChatInfoContent(
                modifier = Modifier.padding(lSpacing.large),
                phoneNumber = ChatInfo.dummy.phoneNumber.formatPhoneNumber(),
                isMuted = false
            )
        }
    }
}