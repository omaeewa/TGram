//
// NOTE: THIS FILE IS AUTO-GENERATED by the "TdApiKtxGenerator".kt
// See: https://github.com/tdlibx/td-ktx-generator/
//
package kotlinx.telegram.coroutines

import kotlin.Int
import kotlin.Long
import kotlinx.telegram.core.TelegramFlow
import org.drinkless.tdlib.TdApi

/**
 * Suspend function, which changes accent color and background custom emoji for the current user;
 * for Telegram Premium users only.
 *
 * @param accentColorId Identifier of the accent color to use.  
 * @param backgroundCustomEmojiId Identifier of a custom emoji to be shown on the reply header and
 * link preview background; 0 if none.
 */
suspend fun TelegramFlow.setAccentColor(accentColorId: Int, backgroundCustomEmojiId: Long) =
    this.sendFunctionLaunch(TdApi.SetAccentColor(accentColorId, backgroundCustomEmojiId))

/**
 * Suspend function, which changes accent color and background custom emoji for profile of the
 * current user; for Telegram Premium users only.
 *
 * @param profileAccentColorId Identifier of the accent color to use for profile; pass -1 if none.  
 * @param profileBackgroundCustomEmojiId Identifier of a custom emoji to be shown on the user's
 * profile photo background; 0 if none.
 */
suspend fun TelegramFlow.setProfileAccentColor(profileAccentColorId: Int,
    profileBackgroundCustomEmojiId: Long) =
    this.sendFunctionLaunch(TdApi.SetProfileAccentColor(profileAccentColorId,
    profileBackgroundCustomEmojiId))