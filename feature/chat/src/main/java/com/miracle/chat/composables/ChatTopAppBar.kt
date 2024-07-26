package com.miracle.chat.composables

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.miracle.chat.model.ChatInfo
import com.miracle.ui.composables.ProfilePhoto
import com.miracle.ui.composables.ProfilePhotoSize
import com.miracle.ui.theme.TGramTheme
import com.miracle.ui.theme.lSpacing
import com.miracle.ui.theme.mColors
import com.miracle.ui.theme.mTypography

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatTopAppBar(
    chatInfo: ChatInfo,
    modifier: Modifier = Modifier,
    onBackBtnClick: () -> Unit = {},
    onMoreBtnClick: () -> Unit = {},
    colors: TopAppBarColors = TopAppBarDefaults.topAppBarColors()
) {
    TopAppBar(
        modifier = modifier,
        navigationIcon = {
            IconButton(onClick = onBackBtnClick) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = null
                )
            }
        },
        title = {
            Row(verticalAlignment = Alignment.CenterVertically) {
                ProfilePhoto(
                    imageModel = chatInfo.imageModel,
                    title = chatInfo.title,
                    userId = chatInfo.id,
                    profilePhotoSize = ProfilePhotoSize.medium,
                    placeholderRes = chatInfo.placeholderRes
                )

                Spacer(modifier = Modifier.width(lSpacing.small))

                Column {
                    Text(
                        text = chatInfo.title,
                        style = mTypography.titleMedium,
                        color = mColors.onSurface
                    )
                    Text(
                        text = "last seen recently",
                        style = mTypography.bodyLarge,
                        color = mColors.secondary,
                    )
                }
            }
        },
        actions = {
            IconButton(onClick = onMoreBtnClick) {
                Icon(imageVector = Icons.Filled.MoreVert, contentDescription = null)
            }
        },
        colors = colors
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
private fun ChatTopAppBarPreview() {
    TGramTheme {
        ChatTopAppBar(chatInfo = ChatInfo.dummy)
    }
}
