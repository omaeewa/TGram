package com.miracle.chats

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.miracle.chats.model.ChatListItem
import com.miracle.common.utils.formatAsFriendlyDate
import com.miracle.common.utils.removeNewlines
import com.miracle.ui.composables.ProfilePhoto
import com.miracle.ui.composables.ProfilePhotoSize
import com.miracle.ui.theme.TGramTheme
import com.miracle.ui.theme.lSpacing
import com.miracle.ui.theme.mColors
import com.miracle.ui.theme.mTypography
import dev.chrisbanes.haze.HazeState
import dev.chrisbanes.haze.haze
import dev.chrisbanes.haze.hazeChild
import kotlin.random.Random

@Composable
fun ChatsScreen(
    chats: List<ChatListItem>,
    modifier: Modifier = Modifier,
    changeDrawerState: () -> Unit = {},
    navigateToChat: (chatId: Long) -> Unit = {}
) {
    val hazeState = remember { HazeState() }
    Scaffold(
        topBar = {
            ChatsTopAppBar(
                modifier = Modifier.hazeChild(state = hazeState),
                onOpenDrawerIconClick = changeDrawerState
            )
        },
        modifier = modifier
    ) {

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .haze(
                    hazeState,
                    backgroundColor = mColors.surfaceContainer,
                    tint = mColors.surfaceContainer.copy(alpha = 0.9f),
                    blurRadius = 30.dp,
                )
        ) {
            item {
                Spacer(modifier = Modifier.height(it.calculateTopPadding()))
            }

            itemsIndexed(items = chats, key = { _, chat -> chat.id }) { index, chat ->
                val itemIsNotLast = index != chats.indices.last

                ChatItem(
                    chat,
                    showUnderline = itemIsNotLast,
                    modifier = Modifier.clickable { navigateToChat(chat.id) })
            }
        }
    }
}

@Preview
@Composable
private fun ChatsScreenPreview() {
    val items = (0..100L).map { ChatListItem.dummy.copy(id = it, isMuted = Random.nextBoolean()) }

    TGramTheme {
        ChatsScreen(items)
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatsTopAppBar(
    modifier: Modifier = Modifier,
    onOpenDrawerIconClick: () -> Unit = {},
    onNavIconClick: () -> Unit = {}
) {

    TopAppBar(
        modifier = modifier,
        title = {
            Text(
                text = stringResource(id = R.string.telegram),
                style = mTypography.titleLarge,
                color = mColors.onSurface,
                fontWeight = FontWeight.Medium
            )
        },
        navigationIcon = {
            IconButton(onClick = onOpenDrawerIconClick) {
                Icon(
                    imageVector = Icons.Filled.Menu,
                    contentDescription = null,
                    tint = mColors.onSurface
                )
            }
        },
        actions = {
            IconButton(onClick = onNavIconClick) {
                Icon(
                    imageVector = Icons.Filled.Search,
                    contentDescription = null,
                    tint = mColors.onSurface
                )
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.Transparent),
    )
}

@Preview
@Composable
private fun ChatsTopAppBarPreview() {
    TGramTheme {
        ChatsTopAppBar(modifier = Modifier.background(mColors.surfaceContainer))
    }
}


@Composable
fun ChatItem(
    item: ChatListItem,
    modifier: Modifier = Modifier,
    showUnderline: Boolean = false,
) {
    Row(modifier = modifier, verticalAlignment = Alignment.CenterVertically) {

        ProfilePhoto(
            imageModel = item.imageModel,
            title = item.title,
            userId = item.id,
            modifier = Modifier
                .padding(
                    start = lSpacing.small,
                    top = lSpacing.small,
                    bottom = lSpacing.small,
                    end = 12.dp
                ),
            profilePhotoSize = ProfilePhotoSize.large,
            placeholderRes = item.placeholderRes
        )

        Box(Modifier.height(71.dp)) {

            Column(
                Modifier
                    .padding(
                        end = lSpacing.small,
                        bottom = lSpacing.small,
                        top = lSpacing.small,
                    )
                    .align(Alignment.Center)
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        modifier = Modifier.weight(1f),
                        text = item.title,
                        style = mTypography.titleMedium,
                        color = mColors.onSurface
                    )
                    Text(
                        text = item.date?.formatAsFriendlyDate().orEmpty(),
                        style = mTypography.bodyMedium,
                        color = mColors.secondary
                    )
                }

                Spacer(modifier = Modifier.height(lSpacing.tiny))

                Row(verticalAlignment = Alignment.Top) {
                    Text(
                        modifier = Modifier.weight(1f),
                        text = item.lastMessage.orEmpty().removeNewlines(),
                        style = mTypography.bodyLarge,
                        color = mColors.secondary,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )

                    Spacer(modifier = Modifier.width(lSpacing.medium))

                    val unreadMessagesBackColor = if (item.isMuted) mColors.secondaryContainer
                    else mColors.primary

                    if (item.unreadCount > 0)
                        Surface(
                            shape = RoundedCornerShape(50),
                            color = unreadMessagesBackColor
                        ) {

                            Text(
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                                text = item.unreadCount.toString(),
                                style = mTypography.bodyMedium,
                                color = mColors.onSurface,
                                fontWeight = FontWeight.Medium
                            )
                        }
                }
            }


            if (showUnderline)
                HorizontalDivider(
                    color = Color.Black,
                    thickness = (0.3).dp,
                    modifier = Modifier
                        .align(Alignment.BottomStart)
                        .fillMaxWidth()
                )
        }
    }
}

@Preview
@Composable
private fun ChatItemPreview() {

    TGramTheme {
        Surface {
            ChatItem(ChatListItem.dummy.copy(isMuted = Random.nextBoolean()))
        }
    }
}