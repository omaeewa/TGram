//
// NOTE: THIS FILE IS AUTO-GENERATED by the "TdApiKtxGenerator".kt
// See: https://github.com/tdlibx/td-ktx-generator/
//
package kotlinx.telegram.coroutines

import kotlin.Boolean
import kotlin.Long
import kotlin.String
import kotlinx.telegram.core.TelegramFlow
import org.drinkless.tdlib.TdApi
import org.drinkless.tdlib.TdApi.CustomRequestResult
import org.drinkless.tdlib.TdApi.FoundWebApp
import org.drinkless.tdlib.TdApi.HttpUrl
import org.drinkless.tdlib.TdApi.InputMessageReplyTo
import org.drinkless.tdlib.TdApi.ThemeParameters
import org.drinkless.tdlib.TdApi.WebAppInfo

/**
 * Suspend function, which informs TDLib that a previously opened Web App was closed.
 *
 * @param webAppLaunchId Identifier of Web App launch, received from openWebApp.
 */
suspend fun TelegramFlow.closeWebApp(webAppLaunchId: Long) =
    this.sendFunctionLaunch(TdApi.CloseWebApp(webAppLaunchId))

/**
 * Suspend function, which returns an HTTPS URL of a Web App to open after a link of the type
 * internalLinkTypeWebApp is clicked.
 *
 * @param chatId Identifier of the chat in which the link was clicked; pass 0 if none.  
 * @param botUserId Identifier of the target bot.  
 * @param webAppShortName Short name of the Web App.  
 * @param startParameter Start parameter from internalLinkTypeWebApp.  
 * @param theme Preferred Web App theme; pass null to use the default theme.  
 * @param applicationName Short name of the application; 0-64 English letters, digits, and
 * underscores.  
 * @param allowWriteAccess Pass true if the current user allowed the bot to send them messages.
 *
 * @return [HttpUrl] Contains an HTTP URL.
 */
suspend fun TelegramFlow.getWebAppLinkUrl(
  chatId: Long,
  botUserId: Long,
  webAppShortName: String?,
  startParameter: String?,
  theme: ThemeParameters?,
  applicationName: String?,
  allowWriteAccess: Boolean
): HttpUrl = this.sendFunctionAsync(TdApi.GetWebAppLinkUrl(chatId, botUserId, webAppShortName,
    startParameter, theme, applicationName, allowWriteAccess))

/**
 * Suspend function, which returns an HTTPS URL of a Web App to open from the side menu, a
 * keyboardButtonTypeWebApp button, an inlineQueryResultsButtonTypeWebApp button, or an
 * internalLinkTypeSideMenuBot link.
 *
 * @param botUserId Identifier of the target bot.  
 * @param url The URL from a keyboardButtonTypeWebApp button, inlineQueryResultsButtonTypeWebApp
 * button, an internalLinkTypeSideMenuBot link, or an empty when the bot is opened from the side menu. 
 * 
 * @param theme Preferred Web App theme; pass null to use the default theme.  
 * @param applicationName Short name of the application; 0-64 English letters, digits, and
 * underscores.
 *
 * @return [HttpUrl] Contains an HTTP URL.
 */
suspend fun TelegramFlow.getWebAppUrl(
  botUserId: Long,
  url: String?,
  theme: ThemeParameters?,
  applicationName: String?
): HttpUrl = this.sendFunctionAsync(TdApi.GetWebAppUrl(botUserId, url, theme, applicationName))

/**
 * Suspend function, which informs TDLib that a Web App is being opened from the attachment menu, a
 * botMenuButton button, an internalLinkTypeAttachmentMenuBot link, or an
 * inlineKeyboardButtonTypeWebApp button. For each bot, a confirmation alert about data sent to the bot
 * must be shown once.
 *
 * @param chatId Identifier of the chat in which the Web App is opened. The Web App can't be opened
 * in secret chats.  
 * @param botUserId Identifier of the bot, providing the Web App.  
 * @param url The URL from an inlineKeyboardButtonTypeWebApp button, a botMenuButton button, an
 * internalLinkTypeAttachmentMenuBot link, or an empty string otherwise.  
 * @param theme Preferred Web App theme; pass null to use the default theme.  
 * @param applicationName Short name of the application; 0-64 English letters, digits, and
 * underscores.  
 * @param messageThreadId If not 0, the message thread identifier in which the message will be sent.
 *  
 * @param replyTo Information about the message or story to be replied in the message sent by the
 * Web App; pass null if none.
 *
 * @return [WebAppInfo] Contains information about a Web App.
 */
suspend fun TelegramFlow.openWebApp(
  chatId: Long,
  botUserId: Long,
  url: String?,
  theme: ThemeParameters?,
  applicationName: String?,
  messageThreadId: Long,
  replyTo: InputMessageReplyTo?
): WebAppInfo = this.sendFunctionAsync(TdApi.OpenWebApp(chatId, botUserId, url, theme,
    applicationName, messageThreadId, replyTo))

/**
 * Suspend function, which returns information about a Web App by its short name. Returns a 404
 * error if the Web App is not found.
 *
 * @param botUserId Identifier of the target bot.  
 * @param webAppShortName Short name of the Web App.
 *
 * @return [FoundWebApp] Contains information about a Web App found by its short name.
 */
suspend fun TelegramFlow.searchWebApp(botUserId: Long, webAppShortName: String?): FoundWebApp =
    this.sendFunctionAsync(TdApi.SearchWebApp(botUserId, webAppShortName))

/**
 * Suspend function, which sends a custom request from a Web App.
 *
 * @param botUserId Identifier of the bot.  
 * @param method The method name.  
 * @param parameters JSON-serialized method parameters.
 *
 * @return [CustomRequestResult] Contains the result of a custom request.
 */
suspend fun TelegramFlow.sendWebAppCustomRequest(
  botUserId: Long,
  method: String?,
  parameters: String?
): CustomRequestResult = this.sendFunctionAsync(TdApi.SendWebAppCustomRequest(botUserId, method,
    parameters))

/**
 * Suspend function, which sends data received from a keyboardButtonTypeWebApp Web App to a bot.
 *
 * @param botUserId Identifier of the target bot.  
 * @param buttonText Text of the keyboardButtonTypeWebApp button, which opened the Web App.  
 * @param data The data.
 */
suspend fun TelegramFlow.sendWebAppData(
  botUserId: Long,
  buttonText: String?,
  data: String?
) = this.sendFunctionLaunch(TdApi.SendWebAppData(botUserId, buttonText, data))