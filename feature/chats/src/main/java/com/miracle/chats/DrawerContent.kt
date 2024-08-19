package com.miracle.chats

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.drawscope.withTransform
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.miracle.chats.model.AccountItem
import com.miracle.common.utils.formatPhoneNumber
import com.miracle.ui.composables.ProfilePhoto
import com.miracle.ui.composables.ProfilePhotoSize
import com.miracle.ui.theme.TGramTheme
import com.miracle.ui.theme.lSpacing
import com.miracle.ui.theme.mColors
import com.miracle.ui.theme.mTypography

@Composable
fun DrawerContent(
    accounts: List<AccountItem>,
    modifier: Modifier = Modifier,
    onThemeChangeClick: () -> Unit = {}
) {
    var accountsListVisible by remember {
        mutableStateOf(true)
    }

    val animatedRotationValue by animateFloatAsState(
        targetValue = if (accountsListVisible) 180f else 0f,
        label = ""
    )

    val currentAccount = accounts.firstOrNull { it.isCurrent } ?: AccountItem.empty
    val verticalScrollState = rememberScrollState()
    val currentAccountCircleSize = with(LocalDensity.current) { 8.dp.toPx() }
    val currentAccountCircleGapSize = with(LocalDensity.current) { 9.dp.toPx() }
    val currentAccountCircleColor = mColors.primary
    val backgroundColor = mColors.surface
    val checkPainter = painterResource(id = R.drawable.account_check)


    val currentAccountIndicator = Modifier
        .drawWithContent {
            drawContent()

            val circleCenter = Offset(
                x = size.width - currentAccountCircleSize / 2,
                y = size.height - currentAccountCircleSize / 2
            )

            drawCircle(
                color = backgroundColor,
                radius = currentAccountCircleGapSize,
                center = circleCenter
            )

            drawCircle(
                color = currentAccountCircleColor,
                radius = currentAccountCircleSize,
                center = circleCenter
            )

            val painterSize = checkPainter.intrinsicSize
            val offsetX = circleCenter.x - painterSize.width / 2
            val offsetY = circleCenter.y - painterSize.height / 2

            withTransform({
                translate(left = offsetX, top = offsetY)
            }) {
                with(checkPainter) {
                    draw(size = painterSize)
                }
            }
        }

    Column(
        modifier
            .background(backgroundColor)
            .verticalScroll(verticalScrollState)
    ) {
        Column(
            Modifier
                .background(mColors.surfaceContainer)
                .padding(lSpacing.medium)
        ) {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Top,
                modifier = Modifier.fillMaxWidth()
            ) {
                ProfilePhoto(
                    imageModel = currentAccount.imageModel,
                    profilePhotoSize = ProfilePhotoSize.extraLarge,
                    title = currentAccount.title,
                    userId = currentAccount.userId,
                    placeholderRes = currentAccount.placeHolderRes
                )


                IconButton(onClick = onThemeChangeClick) {
                    Icon(
                        painter = painterResource(id = R.drawable.sunny),
                        contentDescription = null,
                        modifier = Modifier.size(26.dp),
                        tint = mColors.onSurface
                    )
                }
            }

            Spacer(modifier = Modifier.height(lSpacing.medium))

            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Column {
                    Text(
                        text = currentAccount.title,
                        style = mTypography.bodyLarge,
                        color = mColors.onSurface
                    )

                    Spacer(modifier = Modifier.height(lSpacing.small))

                    Text(
                        text = currentAccount.phoneNumber.formatPhoneNumber(),
                        style = mTypography.bodyMedium,
                        color = mColors.secondary,
                        fontWeight = FontWeight.Normal
                    )
                }


                IconButton(
                    onClick = {
                        accountsListVisible = !accountsListVisible
                    },
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.arrowhead_up),
                        contentDescription = null,
                        modifier = Modifier
                            .size(22.dp)
                            .rotate(animatedRotationValue),
                        tint = mColors.onSurface
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(6.dp))


        AnimatedVisibility(
            visible = accountsListVisible,
            enter = expandVertically(),
            exit = shrinkVertically()
        ) {
            Column {
                accounts.forEach { account ->
                    val indicator = if (account.isCurrent) currentAccountIndicator else Modifier

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { }
                            .padding(horizontal = 14.dp, vertical = 6.dp)
                    ) {
                        ProfilePhoto(
                            imageModel = account.imageModel,
                            profilePhotoSize = ProfilePhotoSize.small,
                            title = account.title,
                            userId = account.userId,
                            placeholderRes = account.placeHolderRes,
                            modifier = Modifier.then(indicator)
                        )

                        Spacer(modifier = Modifier.width(lSpacing.large))

                        Text(
                            text = account.title,
                            style = mTypography.bodyLarge,
                            color = mColors.onSurface,
                            fontWeight = FontWeight.Medium
                        )
                    }
                }


                DrawerItem(
                    modifier = Modifier.fillMaxWidth(),
                    title = stringResource(id = R.string.add_account),
                    iconRes = R.drawable.msg_filled_plus
                )
            }
        }

        DrawerItem.entries.forEach { item ->
            DrawerItem(item, modifier = Modifier.fillMaxWidth())
        }
    }
}

@Preview
@Composable
private fun DrawerContentPreview() {
    val accounts = (0 until 3).map { AccountItem.dummy.copy(isCurrent = it == 0) }
    TGramTheme {
        DrawerContent(accounts = accounts, modifier = Modifier.width(400.dp))
    }
}

@Composable
private fun DrawerItem(
    item: DrawerItem,
    modifier: Modifier = Modifier,
    onItemClick: () -> Unit = { }
) {
    DrawerItem(
        title = stringResource(id = item.titleRes),
        iconRes = item.iconRes,
        modifier = modifier,
        onItemClick = onItemClick
    )
}

@Composable
private fun DrawerItem(
    title: String,
    iconRes: Int,
    modifier: Modifier = Modifier,
    onItemClick: () -> Unit = { }
) {
    Row(
        modifier = modifier
            .clickable(onClick = onItemClick)
            .padding(horizontal = 18.dp, vertical = 15.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            painter = painterResource(id = iconRes),
            contentDescription = null,
            modifier = Modifier.size(24.dp),
            tint = mColors.secondary
        )

        Spacer(modifier = Modifier.width(lSpacing.extraLarge))

        Text(
            text = title,
            style = mTypography.bodyLarge,
            color = mColors.onSurface,
            fontWeight = FontWeight.Medium
        )
    }
}

@Preview
@Composable
private fun DrawerItemsPreview() {
    TGramTheme {
        Surface {
            LazyColumn(
                contentPadding = PaddingValues(lSpacing.small),
                verticalArrangement = Arrangement.spacedBy(lSpacing.small)
            ) {
                items(DrawerItem.entries) { item ->
                    DrawerItem(item)
                }
            }
        }
    }
}

enum class DrawerItem(
    @StringRes val titleRes: Int,
    @DrawableRes val iconRes: Int
) {
    //    AddAccount(titleRes = R.string.add_account, iconRes = R.drawable.msg_filled_plus),
    MyProfile(titleRes = R.string.my_profile, iconRes = R.drawable.msg_openprofile),
    Wallet(titleRes = R.string.wallet, iconRes = R.drawable.wallet),
    NewGroup(titleRes = R.string.new_group, iconRes = R.drawable.msg_groups),
    Contacts(titleRes = R.string.contacts, iconRes = R.drawable.msg_contacts),
    Calls(titleRes = R.string.calls, iconRes = R.drawable.msg_calls),
    PeopleNearby(titleRes = R.string.people_nearby, iconRes = R.drawable.msg_nearby),
    SavedMessages(titleRes = R.string.saved_messages, iconRes = R.drawable.msg_saved),
    Settings(titleRes = R.string.settings, iconRes = R.drawable.msg_settings),
    InviteFriends(titleRes = R.string.invite_friends, iconRes = R.drawable.msg_contact_add),
    TelegramFeatures(titleRes = R.string.telegram_features, iconRes = R.drawable.msg_psa),
}