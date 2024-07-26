package com.miracle.chat.composables

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.expandHorizontally
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.shrinkHorizontally
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.miracle.chat.R
import com.miracle.ui.theme.TGramTheme
import com.miracle.ui.theme.mColors
import com.miracle.ui.theme.mTypography

@Composable
fun ChatBottomAppBar(
    modifier: Modifier = Modifier,
    inputTextValue: String = "",
    onInputTextValueChange: (String) -> Unit = {},
    onStickerIconClick: () -> Unit = {},
    onAttachIconClick: () -> Unit = {},
    onVoiceIconClick: () -> Unit = {},
    onSendMessageClick: () -> Unit = {}
) {

    val stiffness = remember { Spring.StiffnessMedium }

    TextField(
        value = TextFieldValue(inputTextValue, selection = TextRange(inputTextValue.length)),
        onValueChange = { onInputTextValueChange(it.text) },
        colors = TextFieldDefaults.colors(
            unfocusedContainerColor = Color.Transparent,
            focusedContainerColor = Color.Transparent,
            errorContainerColor = Color.Transparent,
            disabledContainerColor = Color.Transparent,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent
        ),
        leadingIcon = {
            IconButton(onClick = onStickerIconClick) {
                Icon(
                    painter = painterResource(id = R.drawable.msg2_sticker),
                    contentDescription = null,
                    modifier = Modifier.size(24.dp),
                    tint = mColors.secondary
                )
            }
        },

        trailingIcon = {
            AnimatedVisibility(
                visible = inputTextValue.isEmpty(),
                enter = fadeIn(animationSpec = spring(stiffness = stiffness)) +
                        expandHorizontally(
                            animationSpec = spring(stiffness = stiffness),
                            expandFrom = Alignment.Start
                        ),
                exit = fadeOut(animationSpec = spring(stiffness = stiffness)) +
                        shrinkHorizontally(
                            animationSpec = spring(stiffness = stiffness),
                            shrinkTowards = Alignment.Start
                        ),
            ) {
                Row {
                    IconButton(onClick = onAttachIconClick) {
                        Icon(
                            painter = painterResource(id = R.drawable.msg_input_attach2),
                            contentDescription = null,
                            modifier = Modifier.size(24.dp),
                            tint = mColors.secondary
                        )
                    }

                    IconButton(onClick = onVoiceIconClick) {
                        Icon(
                            painter = painterResource(id = R.drawable.msg_voice_unmuted),
                            contentDescription = null,
                            modifier = Modifier.size(24.dp),
                            tint = mColors.secondary
                        )
                    }
                }
            }

            AnimatedVisibility(
                visible = inputTextValue.isNotEmpty(),
                enter = fadeIn(animationSpec = spring(stiffness = stiffness)) +
                        scaleIn(animationSpec = spring(stiffness = stiffness)) +
                        expandHorizontally(animationSpec = spring(stiffness = stiffness)),
                exit = fadeOut(animationSpec = spring(stiffness = stiffness)) +
                        scaleOut(animationSpec = spring(stiffness = stiffness)) +
                        shrinkHorizontally(animationSpec = spring(stiffness = stiffness))
            ) {
                IconButton(onClick = onSendMessageClick) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_send),
                        contentDescription = null,
                        modifier = Modifier.size(24.dp),
                        tint = mColors.primary
                    )
                }
            }
        },
        modifier = modifier.fillMaxWidth(),
        placeholder = {
            Text(
                text = stringResource(id = R.string.message),
                style = mTypography.titleMedium,
                color = mColors.secondary,
                fontWeight = FontWeight.Normal
            )
        },
        textStyle = mTypography.titleMedium.copy(fontWeight = FontWeight.Normal)
    )
}

@Preview
@Composable
private fun ChatBottomAppBarPreview() {
    TGramTheme {
        ChatBottomAppBar(Modifier.background(mColors.surfaceContainer))
    }
}