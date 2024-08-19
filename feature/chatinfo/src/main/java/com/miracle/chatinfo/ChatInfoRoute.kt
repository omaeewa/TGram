package com.miracle.chatinfo

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.tooling.preview.Preview
import com.miracle.chatinfo.model.ChatInfo
import com.miracle.common.utils.formatPhoneNumber
import com.miracle.ui.composables.ContainerWithScrollBehavior
import com.miracle.ui.theme.TGramTheme
import com.miracle.ui.theme.lColors
import com.miracle.ui.theme.lSpacing
import com.miracle.ui.theme.mColors

@Composable
fun ChatInfoRoute(
    onBackBtnClick: () -> Unit,
    viewModel: ChatInfoViewModel,
) {
    val chatInfo by viewModel.chatInfo.collectAsState()
    var isMuted by remember { mutableStateOf(true) }

    ChatInfoScreen(
        chatInfo = chatInfo,
        tabTypes = TabType.entries,
        onBackBtnClick = onBackBtnClick,
        isMuted = isMuted,
        onMuteCheckClick = { isMuted = it })
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ChatInfoScreen(
    chatInfo: ChatInfo,
    tabTypes: List<TabType>,
    modifier: Modifier = Modifier,
    isMuted: Boolean,
    onMuteCheckClick: (Boolean) -> Unit = {},
    onBackBtnClick: () -> Unit = {}
) {
    val scrollBehavior =
        TopAppBarDefaults.exitUntilCollapsedScrollBehavior(rememberTopAppBarState())
    val pagerState = rememberPagerState(pageCount = { tabTypes.size })

    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            ChatInfoTopAppBar(
                chatInfo = chatInfo,
                onBackBtnClick = onBackBtnClick,
                colors = TopAppBarDefaults.topAppBarColors(containerColor = mColors.surfaceContainer),
            )
        }
    ) {

        Column(modifier = Modifier.padding(it)) {
            ContainerWithScrollBehavior(scrollBehavior = scrollBehavior) {
                Column {
                    ChatInfoContent(
                        modifier = Modifier.padding(lSpacing.large),
                        phoneNumber = chatInfo.phoneNumber.formatPhoneNumber(),
                        isMuted = isMuted,
                        onMuteCheckClick = onMuteCheckClick
                    )

                    Box(
                        modifier = Modifier
                            .background(lColors.darkerBackground)
                            .fillMaxWidth()
                            .height(lSpacing.medium)
                    )
                }
            }

            MediaTabRow(tabTypes = tabTypes, pagerState = pagerState)

            HorizontalPager(state = pagerState) { page ->
                when (tabTypes[page]) {
                    TabType.Media -> MediaContent()
                    TabType.Files -> FilesContent()
                    TabType.Links -> LinksContent()
                    TabType.Voice -> VoiceContent()
                    TabType.Groups -> GroupsContent()
                }
            }
        }
    }
}




enum class TabType {
    Media, Files, Links, Voice, Groups
}


@Preview
@Composable
private fun ChatInfoScreenPreview() {
    TGramTheme {
        ChatInfoScreen(
            chatInfo = ChatInfo.dummy,
            isMuted = false,
            tabTypes = TabType.entries
        )
    }
}