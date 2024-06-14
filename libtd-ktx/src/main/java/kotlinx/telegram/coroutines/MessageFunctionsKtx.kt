//
// NOTE: THIS FILE IS AUTO-GENERATED by the "TdApiKtxGenerator".kt
// See: https://github.com/tdlibx/td-ktx-generator/
//
package kotlinx.telegram.coroutines

import kotlin.Array
import kotlin.Boolean
import kotlin.Int
import kotlin.Long
import kotlin.LongArray
import kotlin.String
import kotlinx.telegram.core.TelegramFlow
import org.drinkless.td.libcore.telegram.TdApi
import org.drinkless.td.libcore.telegram.TdApi.ChatList
import org.drinkless.td.libcore.telegram.TdApi.FormattedText
import org.drinkless.td.libcore.telegram.TdApi.FoundMessages
import org.drinkless.td.libcore.telegram.TdApi.InputFile
import org.drinkless.td.libcore.telegram.TdApi.InputMessageContent
import org.drinkless.td.libcore.telegram.TdApi.Location
import org.drinkless.td.libcore.telegram.TdApi.Message
import org.drinkless.td.libcore.telegram.TdApi.MessageLink
import org.drinkless.td.libcore.telegram.TdApi.MessageLinkInfo
import org.drinkless.td.libcore.telegram.TdApi.MessageSchedulingState
import org.drinkless.td.libcore.telegram.TdApi.MessageSendOptions
import org.drinkless.td.libcore.telegram.TdApi.MessageSender
import org.drinkless.td.libcore.telegram.TdApi.MessageSenders
import org.drinkless.td.libcore.telegram.TdApi.MessageStatistics
import org.drinkless.td.libcore.telegram.TdApi.MessageThreadInfo
import org.drinkless.td.libcore.telegram.TdApi.Messages
import org.drinkless.td.libcore.telegram.TdApi.ReplyMarkup
import org.drinkless.td.libcore.telegram.TdApi.SearchMessagesFilter
import org.drinkless.td.libcore.telegram.TdApi.Text
import org.drinkless.td.libcore.telegram.TdApi.Users

/**
 * Suspend function, which adds a local message to a chat. The message is persistent across
 * application restarts only if the message database is used. Returns the added message.
 *
 * @param chatId Target chat.  
 * @param sender The sender of the message.  
 * @param replyToMessageId Identifier of the message to reply to or 0.  
 * @param disableNotification Pass true to disable notification for the message.  
 * @param inputMessageContent The content of the message to be added.
 *
 * @return [Message] Describes a message.
 */
suspend fun TelegramFlow.addLocalMessage(
  chatId: Long,
  sender: MessageSender?,
  replyToMessageId: Long,
  disableNotification: Boolean,
  inputMessageContent: InputMessageContent?
): Message = this.sendFunctionAsync(TdApi.AddLocalMessage(chatId, sender, replyToMessageId,
    disableNotification, inputMessageContent))

/**
 * Suspend function, which blocks an original sender of a message in the Replies chat.
 *
 * @param messageId The identifier of an incoming message in the Replies chat.  
 * @param deleteMessage Pass true if the message must be deleted.  
 * @param deleteAllMessages Pass true if all messages from the same sender must be deleted.  
 * @param reportSpam Pass true if the sender must be reported to the Telegram moderators.
 */
suspend fun TelegramFlow.blockMessageSenderFromReplies(
  messageId: Long,
  deleteMessage: Boolean,
  deleteAllMessages: Boolean,
  reportSpam: Boolean
) = this.sendFunctionLaunch(TdApi.BlockMessageSenderFromReplies(messageId, deleteMessage,
    deleteAllMessages, reportSpam))

/**
 * Suspend function, which clears draft messages in all chats.
 *
 * @param excludeSecretChats If true, local draft messages in secret chats will not be cleared.
 */
suspend fun TelegramFlow.clearAllDraftMessages(excludeSecretChats: Boolean) =
    this.sendFunctionLaunch(TdApi.ClearAllDraftMessages(excludeSecretChats))

/**
 * Suspend function, which deletes messages.
 *
 * @param chatId Chat identifier.  
 * @param messageIds Identifiers of the messages to be deleted.  
 * @param revoke Pass true to try to delete messages for all chat members. Always true for
 * supergroups, channels and secret chats.
 */
suspend fun TelegramFlow.deleteMessages(
  chatId: Long,
  messageIds: LongArray?,
  revoke: Boolean
) = this.sendFunctionLaunch(TdApi.DeleteMessages(chatId, messageIds, revoke))

/**
 * Suspend function, which edits the caption of an inline message sent via a bot; for bots only.
 *
 * @param inlineMessageId Inline message identifier.  
 * @param replyMarkup The new message reply markup; pass null if none.  
 * @param caption New message content caption; pass null to remove caption;
 * 0-GetOption(&quot;message_caption_length_max&quot;) characters.
 */
suspend fun TelegramFlow.editInlineMessageCaption(
  inlineMessageId: String?,
  replyMarkup: ReplyMarkup?,
  caption: FormattedText?
) = this.sendFunctionLaunch(TdApi.EditInlineMessageCaption(inlineMessageId, replyMarkup, caption))

/**
 * Suspend function, which edits the content of a live location in an inline message sent via a bot;
 * for bots only.
 *
 * @param inlineMessageId Inline message identifier.  
 * @param replyMarkup The new message reply markup; pass null if none.  
 * @param location New location content of the message; pass null to stop sharing the live location.
 *  
 * @param heading The new direction in which the location moves, in degrees; 1-360. Pass 0 if
 * unknown.  
 * @param proximityAlertRadius The new maximum distance for proximity alerts, in meters (0-100000).
 * Pass 0 if the notification is disabled.
 */
suspend fun TelegramFlow.editInlineMessageLiveLocation(
  inlineMessageId: String?,
  replyMarkup: ReplyMarkup?,
  location: Location?,
  heading: Int,
  proximityAlertRadius: Int
) = this.sendFunctionLaunch(TdApi.EditInlineMessageLiveLocation(inlineMessageId, replyMarkup,
    location, heading, proximityAlertRadius))

/**
 * Suspend function, which edits the content of a message with an animation, an audio, a document, a
 * photo or a video in an inline message sent via a bot; for bots only.
 *
 * @param inlineMessageId Inline message identifier.  
 * @param replyMarkup The new message reply markup; pass null if none; for bots only.  
 * @param inputMessageContent New content of the message. Must be one of the following types:
 * inputMessageAnimation, inputMessageAudio, inputMessageDocument, inputMessagePhoto or
 * inputMessageVideo.
 */
suspend fun TelegramFlow.editInlineMessageMedia(
  inlineMessageId: String?,
  replyMarkup: ReplyMarkup?,
  inputMessageContent: InputMessageContent?
) = this.sendFunctionLaunch(TdApi.EditInlineMessageMedia(inlineMessageId, replyMarkup,
    inputMessageContent))

/**
 * Suspend function, which edits the reply markup of an inline message sent via a bot; for bots
 * only.
 *
 * @param inlineMessageId Inline message identifier.  
 * @param replyMarkup The new message reply markup; pass null if none.
 */
suspend fun TelegramFlow.editInlineMessageReplyMarkup(inlineMessageId: String?,
    replyMarkup: ReplyMarkup?) =
    this.sendFunctionLaunch(TdApi.EditInlineMessageReplyMarkup(inlineMessageId, replyMarkup))

/**
 * Suspend function, which edits the text of an inline text or game message sent via a bot; for bots
 * only.
 *
 * @param inlineMessageId Inline message identifier.  
 * @param replyMarkup The new message reply markup; pass null if none.  
 * @param inputMessageContent New text content of the message. Must be of type inputMessageText.
 */
suspend fun TelegramFlow.editInlineMessageText(
  inlineMessageId: String?,
  replyMarkup: ReplyMarkup?,
  inputMessageContent: InputMessageContent?
) = this.sendFunctionLaunch(TdApi.EditInlineMessageText(inlineMessageId, replyMarkup,
    inputMessageContent))

/**
 * Suspend function, which edits the message content caption. Returns the edited message after the
 * edit is completed on the server side.
 *
 * @param chatId The chat the message belongs to.  
 * @param messageId Identifier of the message.  
 * @param replyMarkup The new message reply markup; pass null if none; for bots only.  
 * @param caption New message content caption; 0-GetOption(&quot;message_caption_length_max&quot;)
 * characters; pass null to remove caption.
 *
 * @return [Message] Describes a message.
 */
suspend fun TelegramFlow.editMessageCaption(
  chatId: Long,
  messageId: Long,
  replyMarkup: ReplyMarkup?,
  caption: FormattedText?
): Message = this.sendFunctionAsync(TdApi.EditMessageCaption(chatId, messageId, replyMarkup,
    caption))

/**
 * Suspend function, which edits the message content of a live location. Messages can be edited for
 * a limited period of time specified in the live location. Returns the edited message after the edit
 * is completed on the server side.
 *
 * @param chatId The chat the message belongs to.  
 * @param messageId Identifier of the message.  
 * @param replyMarkup The new message reply markup; pass null if none; for bots only.  
 * @param location New location content of the message; pass null to stop sharing the live location.
 *  
 * @param heading The new direction in which the location moves, in degrees; 1-360. Pass 0 if
 * unknown.  
 * @param proximityAlertRadius The new maximum distance for proximity alerts, in meters (0-100000).
 * Pass 0 if the notification is disabled.
 *
 * @return [Message] Describes a message.
 */
suspend fun TelegramFlow.editMessageLiveLocation(
  chatId: Long,
  messageId: Long,
  replyMarkup: ReplyMarkup?,
  location: Location?,
  heading: Int,
  proximityAlertRadius: Int
): Message = this.sendFunctionAsync(TdApi.EditMessageLiveLocation(chatId, messageId, replyMarkup,
    location, heading, proximityAlertRadius))

/**
 * Suspend function, which edits the content of a message with an animation, an audio, a document, a
 * photo or a video, including message caption. If only the caption needs to be edited, use
 * editMessageCaption instead. The media can't be edited if the message was set to self-destruct or to
 * a self-destructing media. The type of message content in an album can't be changed with exception of
 * replacing a photo with a video or vice versa. Returns the edited message after the edit is completed
 * on the server side.
 *
 * @param chatId The chat the message belongs to.  
 * @param messageId Identifier of the message.  
 * @param replyMarkup The new message reply markup; pass null if none; for bots only.  
 * @param inputMessageContent New content of the message. Must be one of the following types:
 * inputMessageAnimation, inputMessageAudio, inputMessageDocument, inputMessagePhoto or
 * inputMessageVideo.
 *
 * @return [Message] Describes a message.
 */
suspend fun TelegramFlow.editMessageMedia(
  chatId: Long,
  messageId: Long,
  replyMarkup: ReplyMarkup?,
  inputMessageContent: InputMessageContent?
): Message = this.sendFunctionAsync(TdApi.EditMessageMedia(chatId, messageId, replyMarkup,
    inputMessageContent))

/**
 * Suspend function, which edits the message reply markup; for bots only. Returns the edited message
 * after the edit is completed on the server side.
 *
 * @param chatId The chat the message belongs to.  
 * @param messageId Identifier of the message.  
 * @param replyMarkup The new message reply markup; pass null if none.
 *
 * @return [Message] Describes a message.
 */
suspend fun TelegramFlow.editMessageReplyMarkup(
  chatId: Long,
  messageId: Long,
  replyMarkup: ReplyMarkup?
): Message = this.sendFunctionAsync(TdApi.EditMessageReplyMarkup(chatId, messageId, replyMarkup))

/**
 * Suspend function, which edits the time when a scheduled message will be sent. Scheduling state of
 * all messages in the same album or forwarded together with the message will be also changed.
 *
 * @param chatId The chat the message belongs to.  
 * @param messageId Identifier of the message.  
 * @param schedulingState The new message scheduling state; pass null to send the message
 * immediately.
 */
suspend fun TelegramFlow.editMessageSchedulingState(
  chatId: Long,
  messageId: Long,
  schedulingState: MessageSchedulingState?
) = this.sendFunctionLaunch(TdApi.EditMessageSchedulingState(chatId, messageId, schedulingState))

/**
 * Suspend function, which edits the text of a message (or a text of a game message). Returns the
 * edited message after the edit is completed on the server side.
 *
 * @param chatId The chat the message belongs to.  
 * @param messageId Identifier of the message.  
 * @param replyMarkup The new message reply markup; pass null if none; for bots only.  
 * @param inputMessageContent New text content of the message. Must be of type inputMessageText.
 *
 * @return [Message] Describes a message.
 */
suspend fun TelegramFlow.editMessageText(
  chatId: Long,
  messageId: Long,
  replyMarkup: ReplyMarkup?,
  inputMessageContent: InputMessageContent?
): Message = this.sendFunctionAsync(TdApi.EditMessageText(chatId, messageId, replyMarkup,
    inputMessageContent))

/**
 * Suspend function, which forwards previously sent messages. Returns the forwarded messages in the
 * same order as the message identifiers passed in messageIds. If a message can't be forwarded, null
 * will be returned instead of the message.
 *
 * @param chatId Identifier of the chat to which to forward messages.  
 * @param fromChatId Identifier of the chat from which to forward messages.  
 * @param messageIds Identifiers of the messages to forward. Message identifiers must be in a
 * strictly increasing order. At most 100 messages can be forwarded simultaneously.  
 * @param options Options to be used to send the messages; pass null to use default options.  
 * @param sendCopy If true, content of the messages will be copied without reference to the original
 * sender. Always true if the messages are forwarded to a secret chat or are local.  
 * @param removeCaption If true, media caption of message copies will be removed. Ignored if
 * sendCopy is false.  
 * @param onlyPreview If true, messages will not be forwarded and instead fake messages will be
 * returned.
 *
 * @return [Messages] Contains a list of messages.
 */
suspend fun TelegramFlow.forwardMessages(
  chatId: Long,
  fromChatId: Long,
  messageIds: LongArray?,
  options: MessageSendOptions?,
  sendCopy: Boolean,
  removeCaption: Boolean,
  onlyPreview: Boolean
): Messages = this.sendFunctionAsync(TdApi.ForwardMessages(chatId, fromChatId, messageIds, options,
    sendCopy, removeCaption, onlyPreview))

/**
 * Suspend function, which returns all active live locations that need to be updated by the
 * application. The list is persistent across application restarts only if the message database is
 * used.
 *
 * @return [Messages] Contains a list of messages.
 */
suspend fun TelegramFlow.getActiveLiveLocationMessages(): Messages =
    this.sendFunctionAsync(TdApi.GetActiveLiveLocationMessages())

/**
 * Suspend function, which returns users and chats that were blocked by the current user.
 *
 * @param offset Number of users and chats to skip in the result; must be non-negative.  
 * @param limit The maximum number of users and chats to return; up to 100.
 *
 * @return [MessageSenders] Represents a list of message senders.
 */
suspend fun TelegramFlow.getBlockedMessageSenders(offset: Int, limit: Int): MessageSenders =
    this.sendFunctionAsync(TdApi.GetBlockedMessageSenders(offset, limit))

/**
 * Suspend function, which returns information about a message.
 *
 * @param chatId Identifier of the chat the message belongs to.  
 * @param messageId Identifier of the message to get.
 *
 * @return [Message] Describes a message.
 */
suspend fun TelegramFlow.getMessage(chatId: Long, messageId: Long): Message =
    this.sendFunctionAsync(TdApi.GetMessage(chatId, messageId))

/**
 * Suspend function, which returns an HTML code for embedding the message. Available only for
 * messages in supergroups and channels with a username.
 *
 * @param chatId Identifier of the chat to which the message belongs.  
 * @param messageId Identifier of the message.  
 * @param forAlbum Pass true to return an HTML code for embedding of the whole media album.
 *
 * @return [Text] Contains some text.
 */
suspend fun TelegramFlow.getMessageEmbeddingCode(
  chatId: Long,
  messageId: Long,
  forAlbum: Boolean
): Text = this.sendFunctionAsync(TdApi.GetMessageEmbeddingCode(chatId, messageId, forAlbum))

/**
 * Suspend function, which returns a confirmation text to be shown to the user before starting
 * message import.
 *
 * @param chatId Identifier of a chat to which the messages will be imported. It must be an
 * identifier of a private chat with a mutual contact or an identifier of a supergroup chat with
 * canChangeInfo administrator right.
 *
 * @return [Text] Contains some text.
 */
suspend fun TelegramFlow.getMessageImportConfirmationText(chatId: Long): Text =
    this.sendFunctionAsync(TdApi.GetMessageImportConfirmationText(chatId))

/**
 * Suspend function, which returns an HTTPS link to a message in a chat. Available only for already
 * sent messages in supergroups and channels, or if message.canGetMediaTimestampLinks and a media
 * timestamp link is generated. This is an offline request.
 *
 * @param chatId Identifier of the chat to which the message belongs.  
 * @param messageId Identifier of the message.  
 * @param mediaTimestamp If not 0, timestamp from which the video/audio/video note/voice note
 * playing must start, in seconds. The media can be in the message content or in its web page preview. 
 * 
 * @param forAlbum Pass true to create a link for the whole media album.  
 * @param forComment Pass true to create a link to the message as a channel post comment, or from a
 * message thread.
 *
 * @return [MessageLink] Contains an HTTPS link to a message in a supergroup or channel.
 */
suspend fun TelegramFlow.getMessageLink(
  chatId: Long,
  messageId: Long,
  mediaTimestamp: Int,
  forAlbum: Boolean,
  forComment: Boolean
): MessageLink = this.sendFunctionAsync(TdApi.GetMessageLink(chatId, messageId, mediaTimestamp,
    forAlbum, forComment))

/**
 * Suspend function, which returns information about a public or private message link. Can be called
 * for any internal link of the type internalLinkTypeMessage.
 *
 * @param url The message link.
 *
 * @return [MessageLinkInfo] Contains information about a link to a message in a chat.
 */
suspend fun TelegramFlow.getMessageLinkInfo(url: String?): MessageLinkInfo =
    this.sendFunctionAsync(TdApi.GetMessageLinkInfo(url))

/**
 * Suspend function, which returns information about a message, if it is available locally without
 * sending network request. This is an offline request.
 *
 * @param chatId Identifier of the chat the message belongs to.  
 * @param messageId Identifier of the message to get.
 *
 * @return [Message] Describes a message.
 */
suspend fun TelegramFlow.getMessageLocally(chatId: Long, messageId: Long): Message =
    this.sendFunctionAsync(TdApi.GetMessageLocally(chatId, messageId))

/**
 * Suspend function, which returns forwarded copies of a channel message to different public
 * channels. For optimal performance, the number of returned messages is chosen by TDLib.
 *
 * @param chatId Chat identifier of the message.  
 * @param messageId Message identifier.  
 * @param offset Offset of the first entry to return as received from the previous request; use
 * empty string to get first chunk of results.  
 * @param limit The maximum number of messages to be returned; must be positive and can't be greater
 * than 100. For optimal performance, the number of returned messages is chosen by TDLib and can be
 * smaller than the specified limit.
 *
 * @return [FoundMessages] Contains a list of messages found by a search.
 */
suspend fun TelegramFlow.getMessagePublicForwards(
  chatId: Long,
  messageId: Long,
  offset: String?,
  limit: Int
): FoundMessages = this.sendFunctionAsync(TdApi.GetMessagePublicForwards(chatId, messageId, offset,
    limit))

/**
 * Suspend function, which returns detailed statistics about a message. Can be used only if
 * message.canGetStatistics == true.
 *
 * @param chatId Chat identifier.  
 * @param messageId Message identifier.  
 * @param isDark Pass true if a dark theme is used by the application.
 *
 * @return [MessageStatistics] A detailed statistics about a message.
 */
suspend fun TelegramFlow.getMessageStatistics(
  chatId: Long,
  messageId: Long,
  isDark: Boolean
): MessageStatistics = this.sendFunctionAsync(TdApi.GetMessageStatistics(chatId, messageId, isDark))

/**
 * Suspend function, which returns information about a message thread. Can be used only if
 * message.canGetMessageThread == true.
 *
 * @param chatId Chat identifier.  
 * @param messageId Identifier of the message.
 *
 * @return [MessageThreadInfo] Contains information about a message thread.
 */
suspend fun TelegramFlow.getMessageThread(chatId: Long, messageId: Long): MessageThreadInfo =
    this.sendFunctionAsync(TdApi.GetMessageThread(chatId, messageId))

/**
 * Suspend function, which returns messages in a message thread of a message. Can be used only if
 * message.canGetMessageThread == true. Message thread of a channel message is in the channel's linked
 * supergroup. The messages are returned in a reverse chronological order (i.e., in order of decreasing
 * messageId). For optimal performance, the number of returned messages is chosen by TDLib.
 *
 * @param chatId Chat identifier.  
 * @param messageId Message identifier, which thread history needs to be returned.  
 * @param fromMessageId Identifier of the message starting from which history must be fetched; use 0
 * to get results from the last message.  
 * @param offset Specify 0 to get results from exactly the fromMessageId or a negative offset up to
 * 99 to get additionally some newer messages.  
 * @param limit The maximum number of messages to be returned; must be positive and can't be greater
 * than 100. If the offset is negative, the limit must be greater than or equal to -offset. For optimal
 * performance, the number of returned messages is chosen by TDLib and can be smaller than the
 * specified limit.
 *
 * @return [Messages] Contains a list of messages.
 */
suspend fun TelegramFlow.getMessageThreadHistory(
  chatId: Long,
  messageId: Long,
  fromMessageId: Long,
  offset: Int,
  limit: Int
): Messages = this.sendFunctionAsync(TdApi.GetMessageThreadHistory(chatId, messageId, fromMessageId,
    offset, limit))

/**
 * Suspend function, which returns viewers of a recent outgoing message in a basic group or a
 * supergroup chat. For video notes and voice notes only users, opened content of the message, are
 * returned. The method can be called if message.canGetViewers == true.
 *
 * @param chatId Chat identifier.  
 * @param messageId Identifier of the message.
 *
 * @return [Users] Represents a list of users.
 */
suspend fun TelegramFlow.getMessageViewers(chatId: Long, messageId: Long): Users =
    this.sendFunctionAsync(TdApi.GetMessageViewers(chatId, messageId))

/**
 * Suspend function, which returns information about messages. If a message is not found, returns
 * null on the corresponding position of the result.
 *
 * @param chatId Identifier of the chat the messages belong to.  
 * @param messageIds Identifiers of the messages to get.
 *
 * @return [Messages] Contains a list of messages.
 */
suspend fun TelegramFlow.getMessages(chatId: Long, messageIds: LongArray?): Messages =
    this.sendFunctionAsync(TdApi.GetMessages(chatId, messageIds))

/**
 * Suspend function, which returns information about a message that is replied by a given message.
 * Also returns the pinned message, the game message, and the invoice message for messages of the types
 * messagePinMessage, messageGameScore, and messagePaymentSuccessful respectively.
 *
 * @param chatId Identifier of the chat the message belongs to.  
 * @param messageId Identifier of the reply message.
 *
 * @return [Message] Describes a message.
 */
suspend fun TelegramFlow.getRepliedMessage(chatId: Long, messageId: Long): Message =
    this.sendFunctionAsync(TdApi.GetRepliedMessage(chatId, messageId))

/**
 * Suspend function, which imports messages exported from another app.
 *
 * @param chatId Identifier of a chat to which the messages will be imported. It must be an
 * identifier of a private chat with a mutual contact or an identifier of a supergroup chat with
 * canChangeInfo administrator right.  
 * @param messageFile File with messages to import. Only inputFileLocal and inputFileGenerated are
 * supported. The file must not be previously uploaded.  
 * @param attachedFiles Files used in the imported messages. Only inputFileLocal and
 * inputFileGenerated are supported. The files must not be previously uploaded.
 */
suspend fun TelegramFlow.importMessages(
  chatId: Long,
  messageFile: InputFile?,
  attachedFiles: Array<InputFile>?
) = this.sendFunctionLaunch(TdApi.ImportMessages(chatId, messageFile, attachedFiles))

/**
 * Suspend function, which informs TDLib that the message content has been opened (e.g., the user
 * has opened a photo, video, document, location or venue, or has listened to an audio file or voice
 * note message). An updateMessageContentOpened update will be generated if something has changed.
 *
 * @param chatId Chat identifier of the message.  
 * @param messageId Identifier of the message with the opened content.
 */
suspend fun TelegramFlow.openMessageContent(chatId: Long, messageId: Long) =
    this.sendFunctionLaunch(TdApi.OpenMessageContent(chatId, messageId))

/**
 * Suspend function, which resends messages which failed to send. Can be called only for messages
 * for which messageSendingStateFailed.canRetry is true and after specified in
 * messageSendingStateFailed.retryAfter time passed. If a message is re-sent, the corresponding failed
 * to send message is deleted. Returns the sent messages in the same order as the message identifiers
 * passed in messageIds. If a message can't be re-sent, null will be returned instead of the message.
 *
 * @param chatId Identifier of the chat to send messages.  
 * @param messageIds Identifiers of the messages to resend. Message identifiers must be in a
 * strictly increasing order.
 *
 * @return [Messages] Contains a list of messages.
 */
suspend fun TelegramFlow.resendMessages(chatId: Long, messageIds: LongArray?): Messages =
    this.sendFunctionAsync(TdApi.ResendMessages(chatId, messageIds))

/**
 * Suspend function, which searches for messages in all chats except secret chats. Returns the
 * results in reverse chronological order (i.e., in order of decreasing (date, chatId, messageId)). For
 * optimal performance, the number of returned messages is chosen by TDLib and can be smaller than the
 * specified limit.
 *
 * @param chatList Chat list in which to search messages; pass null to search in all chats
 * regardless of their chat list. Only Main and Archive chat lists are supported.  
 * @param query Query to search for.  
 * @param offsetDate The date of the message starting from which the results need to be fetched. Use
 * 0 or any date in the future to get results from the last message.  
 * @param offsetChatId The chat identifier of the last found message, or 0 for the first request.  
 * @param offsetMessageId The message identifier of the last found message, or 0 for the first
 * request.  
 * @param limit The maximum number of messages to be returned; up to 100. For optimal performance,
 * the number of returned messages is chosen by TDLib and can be smaller than the specified limit.  
 * @param filter Additional filter for messages to search; pass null to search for all messages.
 * Filters searchMessagesFilterCall, searchMessagesFilterMissedCall, searchMessagesFilterMention,
 * searchMessagesFilterUnreadMention, searchMessagesFilterFailedToSend and searchMessagesFilterPinned
 * are unsupported in this function.  
 * @param minDate If not 0, the minimum date of the messages to return.  
 * @param maxDate If not 0, the maximum date of the messages to return.
 *
 * @return [Messages] Contains a list of messages.
 */
suspend fun TelegramFlow.searchMessages(
  chatList: ChatList?,
  query: String?,
  offsetDate: Int,
  offsetChatId: Long,
  offsetMessageId: Long,
  limit: Int,
  filter: SearchMessagesFilter?,
  minDate: Int,
  maxDate: Int
): Messages = this.sendFunctionAsync(TdApi.SearchMessages(chatList, query, offsetDate, offsetChatId,
    offsetMessageId, limit, filter, minDate, maxDate))

/**
 * Suspend function, which searches for messages in secret chats. Returns the results in reverse
 * chronological order. For optimal performance, the number of returned messages is chosen by TDLib.
 *
 * @param chatId Identifier of the chat in which to search. Specify 0 to search in all secret chats.
 *  
 * @param query Query to search for. If empty, searchChatMessages must be used instead.  
 * @param offset Offset of the first entry to return as received from the previous request; use
 * empty string to get first chunk of results.  
 * @param limit The maximum number of messages to be returned; up to 100. For optimal performance,
 * the number of returned messages is chosen by TDLib and can be smaller than the specified limit.  
 * @param filter Additional filter for messages to search; pass null to search for all messages.
 *
 * @return [FoundMessages] Contains a list of messages found by a search.
 */
suspend fun TelegramFlow.searchSecretMessages(
  chatId: Long,
  query: String?,
  offset: String?,
  limit: Int,
  filter: SearchMessagesFilter?
): FoundMessages = this.sendFunctionAsync(TdApi.SearchSecretMessages(chatId, query, offset, limit,
    filter))

/**
 * Suspend function, which invites a bot to a chat (if it is not yet a member) and sends it the
 * /start command. Bots can't be invited to a private chat other than the chat with the bot. Bots can't
 * be invited to channels (although they can be added as admins) and secret chats. Returns the sent
 * message.
 *
 * @param botUserId Identifier of the bot.  
 * @param chatId Identifier of the target chat.  
 * @param parameter A hidden parameter sent to the bot for deep linking purposes
 * (https://core.telegram.org/bots#deep-linking).
 *
 * @return [Message] Describes a message.
 */
suspend fun TelegramFlow.sendBotStartMessage(
  botUserId: Long,
  chatId: Long,
  parameter: String?
): Message = this.sendFunctionAsync(TdApi.SendBotStartMessage(botUserId, chatId, parameter))

/**
 * Suspend function, which sends a message. Returns the sent message.
 *
 * @param chatId Target chat.  
 * @param messageThreadId If not 0, a message thread identifier in which the message will be sent.  
 * @param replyToMessageId Identifier of the message to reply to or 0.  
 * @param options Options to be used to send the message; pass null to use default options.  
 * @param replyMarkup Markup for replying to the message; pass null if none; for bots only.  
 * @param inputMessageContent The content of the message to be sent.
 *
 * @return [Message] Describes a message.
 */
suspend fun TelegramFlow.sendMessage(
  chatId: Long,
  messageThreadId: Long,
  replyToMessageId: Long,
  options: MessageSendOptions?,
  replyMarkup: ReplyMarkup?,
  inputMessageContent: InputMessageContent?
): Message = this.sendFunctionAsync(TdApi.SendMessage(chatId, messageThreadId, replyToMessageId,
    options, replyMarkup, inputMessageContent))

/**
 * Suspend function, which sends 2-10 messages grouped together into an album. Currently only audio,
 * document, photo and video messages can be grouped into an album. Documents and audio files can be
 * only grouped in an album with messages of the same type. Returns sent messages.
 *
 * @param chatId Target chat.  
 * @param messageThreadId If not 0, a message thread identifier in which the messages will be sent. 
 * 
 * @param replyToMessageId Identifier of a message to reply to or 0.  
 * @param options Options to be used to send the messages; pass null to use default options.  
 * @param inputMessageContents Contents of messages to be sent. At most 10 messages can be added to
 * an album.
 *
 * @return [Messages] Contains a list of messages.
 */
suspend fun TelegramFlow.sendMessageAlbum(
  chatId: Long,
  messageThreadId: Long,
  replyToMessageId: Long,
  options: MessageSendOptions?,
  inputMessageContents: Array<InputMessageContent>?
): Messages = this.sendFunctionAsync(TdApi.SendMessageAlbum(chatId, messageThreadId,
    replyToMessageId, options, inputMessageContents))

/**
 * Suspend function, which changes the block state of a message sender. Currently, only users and
 * supergroup chats can be blocked.
 *
 * @param sender Message Sender.  
 * @param isBlocked New value of isBlocked.
 */
suspend fun TelegramFlow.toggleMessageSenderIsBlocked(sender: MessageSender?, isBlocked: Boolean) =
    this.sendFunctionLaunch(TdApi.ToggleMessageSenderIsBlocked(sender, isBlocked))

/**
 * Suspend function, which toggles sender signatures messages sent in a channel; requires
 * canChangeInfo administrator right.
 *
 * @param supergroupId Identifier of the channel.  
 * @param signMessages New value of signMessages.
 */
suspend fun TelegramFlow.toggleSupergroupSignMessages(supergroupId: Long, signMessages: Boolean) =
    this.sendFunctionLaunch(TdApi.ToggleSupergroupSignMessages(supergroupId, signMessages))

/**
 * Suspend function, which informs TDLib that messages are being viewed by the user. Many useful
 * activities depend on whether the messages are currently being viewed or not (e.g., marking messages
 * as read, incrementing a view counter, updating a view counter, removing deleted messages in
 * supergroups and channels).
 *
 * @param chatId Chat identifier.  
 * @param messageThreadId If not 0, a message thread identifier in which the messages are being
 * viewed.  
 * @param messageIds The identifiers of the messages being viewed.  
 * @param forceRead True, if messages in closed chats must be marked as read by the request.
 */
suspend fun TelegramFlow.viewMessages(
  chatId: Long,
  messageThreadId: Long,
  messageIds: LongArray?,
  forceRead: Boolean
) = this.sendFunctionLaunch(TdApi.ViewMessages(chatId, messageThreadId, messageIds, forceRead))

/**
 * Suspend function, which informs TDLib that a sponsored message was viewed by the user.
 *
 * @param chatId Identifier of the chat with the sponsored message.  
 * @param sponsoredMessageId The identifier of the sponsored message being viewed.
 */
suspend fun TelegramFlow.viewSponsoredMessage(chatId: Long, sponsoredMessageId: Int) =
    this.sendFunctionLaunch(TdApi.ViewSponsoredMessage(chatId, sponsoredMessageId))
