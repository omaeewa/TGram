package com.miracle.chatinfo

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
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
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.miracle.chatinfo.model.ChatInfo
import com.miracle.ui.composables.ProfilePhoto
import com.miracle.ui.composables.ProfilePhotoSize
import com.miracle.ui.noRippleClickable
import com.miracle.ui.theme.TGramTheme
import com.miracle.ui.theme.lSpacing
import com.miracle.ui.theme.mColors
import com.miracle.ui.theme.mTypography

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatInfoTopAppBar(
    chatInfo: ChatInfo,
    modifier: Modifier = Modifier,
    onBackBtnClick: () -> Unit = {},
    onMoreBtnClick: () -> Unit = {},
    onCallBtnClick: () -> Unit = {},
    onVideoCallBtnClick: () -> Unit = {},
    colors: TopAppBarColors = TopAppBarDefaults.topAppBarColors(),
    onProfileClick: () -> Unit = {},
    scrollBehavior: TopAppBarScrollBehavior? = null
) {
    TopAppBar(
        modifier = modifier,
        scrollBehavior = scrollBehavior,
        navigationIcon = {
            IconButton(onClick = onBackBtnClick) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = null,
                    tint = mColors.onSurface,
                    modifier = Modifier.size(26.dp)
                )
            }
        },
        title = {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.noRippleClickable(onClick = onProfileClick)
            ) {
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
            IconButton(onClick = onVideoCallBtnClick) {
                Icon(
                    painter = painterResource(id = com.miracle.common.R.drawable.calls_video),
                    contentDescription = null,
                    modifier = Modifier.size(30.dp),
                    tint = mColors.onSurface
                )
            }
            IconButton(onClick = onCallBtnClick) {
                Icon(
                    painter = painterResource(id = com.miracle.common.R.drawable.ic_call),
                    contentDescription = null,
                    modifier = Modifier.size(24.dp),
                    tint = mColors.onSurface
                )
            }
            IconButton(onClick = onMoreBtnClick) {
                Icon(
                    imageVector = Icons.Filled.MoreVert,
                    contentDescription = null,
                    modifier = Modifier.size(24.dp),
                    tint = mColors.onSurface
                )
            }
        },
        colors = colors
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
private fun ChatInfoTopAppBarPreview() {
    TGramTheme {
        ChatInfoTopAppBar(chatInfo = ChatInfo.dummy)
    }
}