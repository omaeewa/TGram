//
// NOTE: THIS FILE IS AUTO-GENERATED by the "TdApiKtxGenerator".kt
// See: https://github.com/tdlibx/td-ktx-generator/
//
package kotlinx.telegram.flows

import kotlin.Array
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.mapNotNull
import kotlinx.telegram.core.TelegramFlow
import org.drinkless.td.libcore.telegram.TdApi
import org.drinkless.td.libcore.telegram.TdApi.Chat
import org.drinkless.td.libcore.telegram.TdApi.ChatFilterInfo
import org.drinkless.td.libcore.telegram.TdApi.ChatTheme
import org.drinkless.td.libcore.telegram.TdApi.SecretChat
import org.drinkless.td.libcore.telegram.TdApi.UpdateChatActionBar
import org.drinkless.td.libcore.telegram.TdApi.UpdateChatDefaultDisableNotification
import org.drinkless.td.libcore.telegram.TdApi.UpdateChatDraftMessage
import org.drinkless.td.libcore.telegram.TdApi.UpdateChatHasScheduledMessages
import org.drinkless.td.libcore.telegram.TdApi.UpdateChatIsBlocked
import org.drinkless.td.libcore.telegram.TdApi.UpdateChatIsMarkedAsUnread
import org.drinkless.td.libcore.telegram.TdApi.UpdateChatLastMessage
import org.drinkless.td.libcore.telegram.TdApi.UpdateChatMember
import org.drinkless.td.libcore.telegram.TdApi.UpdateChatMessageTtlSetting
import org.drinkless.td.libcore.telegram.TdApi.UpdateChatNotificationSettings
import org.drinkless.td.libcore.telegram.TdApi.UpdateChatOnlineMemberCount
import org.drinkless.td.libcore.telegram.TdApi.UpdateChatPendingJoinRequests
import org.drinkless.td.libcore.telegram.TdApi.UpdateChatPermissions
import org.drinkless.td.libcore.telegram.TdApi.UpdateChatPhoto
import org.drinkless.td.libcore.telegram.TdApi.UpdateChatPosition
import org.drinkless.td.libcore.telegram.TdApi.UpdateChatReadInbox
import org.drinkless.td.libcore.telegram.TdApi.UpdateChatReadOutbox
import org.drinkless.td.libcore.telegram.TdApi.UpdateChatReplyMarkup
import org.drinkless.td.libcore.telegram.TdApi.UpdateChatTheme
import org.drinkless.td.libcore.telegram.TdApi.UpdateChatTitle
import org.drinkless.td.libcore.telegram.TdApi.UpdateChatUnreadMentionCount
import org.drinkless.td.libcore.telegram.TdApi.UpdateChatVideoChat
import org.drinkless.td.libcore.telegram.TdApi.UpdateNewChatJoinRequest
import org.drinkless.td.libcore.telegram.TdApi.UpdateUnreadChatCount
import org.drinkless.td.libcore.telegram.TdApi.UpdateUserChatAction

/**
 * emits [Chat] if a new chat has been loaded/created. This update is guaranteed to come before the
 * chat identifier is returned to the application. The chat field changes will be reported through
 * separate updates.
 */
fun TelegramFlow.newChatFlow(): Flow<Chat> = this.getUpdatesFlowOfType<TdApi.UpdateNewChat>()
    .mapNotNull { it.chat }

/**
 * emits [UpdateChatTitle] if the title of a chat was changed.
 */
fun TelegramFlow.chatTitleFlow(): Flow<UpdateChatTitle> = this.getUpdatesFlowOfType()

/**
 * emits [UpdateChatPhoto] if a chat photo was changed.
 */
fun TelegramFlow.chatPhotoFlow(): Flow<UpdateChatPhoto> = this.getUpdatesFlowOfType()

/**
 * emits [UpdateChatPermissions] if chat permissions was changed.
 */
fun TelegramFlow.chatPermissionsFlow(): Flow<UpdateChatPermissions> = this.getUpdatesFlowOfType()

/**
 * emits [UpdateChatLastMessage] if the last message of a chat was changed. If lastMessage is null,
 * then the last message in the chat became unknown. Some new unknown messages might be added to the
 * chat in this case.
 */
fun TelegramFlow.chatLastMessageFlow(): Flow<UpdateChatLastMessage> = this.getUpdatesFlowOfType()

/**
 * emits [UpdateChatPosition] if the position of a chat in a chat list has changed. Instead of this
 * update updateChatLastMessage or updateChatDraftMessage might be sent.
 */
fun TelegramFlow.chatPositionFlow(): Flow<UpdateChatPosition> = this.getUpdatesFlowOfType()

/**
 * emits [UpdateChatIsMarkedAsUnread] if a chat was marked as unread or was read.
 */
fun TelegramFlow.chatIsMarkedAsUnreadFlow(): Flow<UpdateChatIsMarkedAsUnread> =
    this.getUpdatesFlowOfType()

/**
 * emits [UpdateChatIsBlocked] if a chat was blocked or unblocked.
 */
fun TelegramFlow.chatIsBlockedFlow(): Flow<UpdateChatIsBlocked> = this.getUpdatesFlowOfType()

/**
 * emits [UpdateChatHasScheduledMessages] if a chat's hasScheduledMessages field has changed.
 */
fun TelegramFlow.chatHasScheduledMessagesFlow(): Flow<UpdateChatHasScheduledMessages> =
    this.getUpdatesFlowOfType()

/**
 * emits [UpdateChatVideoChat] if a chat video chat state has changed.
 */
fun TelegramFlow.chatVideoChatFlow(): Flow<UpdateChatVideoChat> = this.getUpdatesFlowOfType()

/**
 * emits [UpdateChatDefaultDisableNotification] if the value of the default disableNotification
 * parameter, used when a message is sent to the chat, was changed.
 */
fun TelegramFlow.chatDefaultDisableNotificationFlow(): Flow<UpdateChatDefaultDisableNotification> =
    this.getUpdatesFlowOfType()

/**
 * emits [UpdateChatReadInbox] if incoming messages were read or number of unread messages has been
 * changed.
 */
fun TelegramFlow.chatReadInboxFlow(): Flow<UpdateChatReadInbox> = this.getUpdatesFlowOfType()

/**
 * emits [UpdateChatReadOutbox] if outgoing messages were read.
 */
fun TelegramFlow.chatReadOutboxFlow(): Flow<UpdateChatReadOutbox> = this.getUpdatesFlowOfType()

/**
 * emits [UpdateChatUnreadMentionCount] if the chat unreadMentionCount has changed.
 */
fun TelegramFlow.chatUnreadMentionCountFlow(): Flow<UpdateChatUnreadMentionCount> =
    this.getUpdatesFlowOfType()

/**
 * emits [UpdateChatNotificationSettings] if notification settings for a chat were changed.
 */
fun TelegramFlow.chatNotificationSettingsFlow(): Flow<UpdateChatNotificationSettings> =
    this.getUpdatesFlowOfType()

/**
 * emits [UpdateChatMessageTtlSetting] if the message Time To Live setting for a chat was changed.
 */
fun TelegramFlow.chatMessageTtlSettingFlow(): Flow<UpdateChatMessageTtlSetting> =
    this.getUpdatesFlowOfType()

/**
 * emits [UpdateChatActionBar] if the chat action bar was changed.
 */
fun TelegramFlow.chatActionBarFlow(): Flow<UpdateChatActionBar> = this.getUpdatesFlowOfType()

/**
 * emits [UpdateChatTheme] if the chat theme was changed.
 */
fun TelegramFlow.chatThemeFlow(): Flow<UpdateChatTheme> = this.getUpdatesFlowOfType()

/**
 * emits [UpdateChatPendingJoinRequests] if the chat pending join requests were changed.
 */
fun TelegramFlow.chatPendingJoinRequestsFlow(): Flow<UpdateChatPendingJoinRequests> =
    this.getUpdatesFlowOfType()

/**
 * emits [UpdateChatReplyMarkup] if the default chat reply markup was changed. Can occur because new
 * messages with reply markup were received or because an old reply markup was hidden by the user.
 */
fun TelegramFlow.chatReplyMarkupFlow(): Flow<UpdateChatReplyMarkup> = this.getUpdatesFlowOfType()

/**
 * emits [UpdateChatDraftMessage] if a chat draft has changed. Be aware that the update may come in
 * the currently opened chat but with old content of the draft. If the user has changed the content of
 * the draft, this update mustn't be applied.
 */
fun TelegramFlow.chatDraftMessageFlow(): Flow<UpdateChatDraftMessage> = this.getUpdatesFlowOfType()

/**
 * emits chatFilters [ChatFilterInfo[]] if the list of chat filters or a chat filter has changed.
 */
fun TelegramFlow.chatFiltersFlow(): Flow<Array<ChatFilterInfo>> =
    this.getUpdatesFlowOfType<TdApi.UpdateChatFilters>()
    .mapNotNull { it.chatFilters }

/**
 * emits [UpdateChatOnlineMemberCount] if the number of online group members has changed. This
 * update with non-zero count is sent only for currently opened chats. There is no guarantee that it
 * will be sent just after the count has changed.
 */
fun TelegramFlow.chatOnlineMemberCountFlow(): Flow<UpdateChatOnlineMemberCount> =
    this.getUpdatesFlowOfType()

/**
 * emits [UpdateUserChatAction] if user activity in the chat has changed.
 */
fun TelegramFlow.userChatActionFlow(): Flow<UpdateUserChatAction> = this.getUpdatesFlowOfType()

/**
 * emits [SecretChat] if some data of a secret chat has changed. This update is guaranteed to come
 * before the secret chat identifier is returned to the application.
 */
fun TelegramFlow.secretChatFlow(): Flow<SecretChat> =
    this.getUpdatesFlowOfType<TdApi.UpdateSecretChat>()
    .mapNotNull { it.secretChat }

/**
 * emits [UpdateUnreadChatCount] if number of unread chats, i.e. with unread messages or marked as
 * unread, has changed. This update is sent only if the message database is used.
 */
fun TelegramFlow.unreadChatCountFlow(): Flow<UpdateUnreadChatCount> = this.getUpdatesFlowOfType()

/**
 * emits chatThemes [ChatTheme[]] if the list of available chat themes has changed.
 */
fun TelegramFlow.chatThemesFlow(): Flow<Array<ChatTheme>> =
    this.getUpdatesFlowOfType<TdApi.UpdateChatThemes>()
    .mapNotNull { it.chatThemes }

/**
 * emits [UpdateChatMember] if user rights changed in a chat; for bots only.
 */
fun TelegramFlow.chatMemberFlow(): Flow<UpdateChatMember> = this.getUpdatesFlowOfType()

/**
 * emits [UpdateNewChatJoinRequest] if a user sent a join request to a chat; for bots only.
 */
fun TelegramFlow.newChatJoinRequestFlow(): Flow<UpdateNewChatJoinRequest> =
    this.getUpdatesFlowOfType()
