//
// NOTE: THIS FILE IS AUTO-GENERATED by the "TdApiKtxGenerator".kt
// See: https://github.com/tdlibx/td-ktx-generator/
//
package kotlinx.telegram.coroutines

import kotlin.Boolean
import kotlin.Int
import kotlin.Long
import kotlin.String
import kotlinx.telegram.core.TelegramFlow
import org.drinkless.td.libcore.telegram.TdApi
import org.drinkless.td.libcore.telegram.TdApi.GameHighScores
import org.drinkless.td.libcore.telegram.TdApi.Message

/**
 * Suspend function, which returns the high scores for a game and some part of the high score table
 * in the range of the specified user; for bots only.
 *
 * @param chatId The chat that contains the message with the game.  
 * @param messageId Identifier of the message.  
 * @param userId User identifier.
 *
 * @return [GameHighScores] Contains a list of game high scores.
 */
suspend fun TelegramFlow.getGameHighScores(
  chatId: Long,
  messageId: Long,
  userId: Long
): GameHighScores = this.sendFunctionAsync(TdApi.GetGameHighScores(chatId, messageId, userId))

/**
 * Suspend function, which returns game high scores and some part of the high score table in the
 * range of the specified user; for bots only.
 *
 * @param inlineMessageId Inline message identifier.  
 * @param userId User identifier.
 *
 * @return [GameHighScores] Contains a list of game high scores.
 */
suspend fun TelegramFlow.getInlineGameHighScores(inlineMessageId: String?, userId: Long):
    GameHighScores = this.sendFunctionAsync(TdApi.GetInlineGameHighScores(inlineMessageId, userId))

/**
 * Suspend function, which updates the game score of the specified user in the game; for bots only.
 *
 * @param chatId The chat to which the message with the game belongs.  
 * @param messageId Identifier of the message.  
 * @param editMessage True, if the message needs to be edited.  
 * @param userId User identifier.  
 * @param score The new score.  
 * @param force Pass true to update the score even if it decreases. If the score is 0, the user will
 * be deleted from the high score table.
 *
 * @return [Message] Describes a message.
 */
suspend fun TelegramFlow.setGameScore(
  chatId: Long,
  messageId: Long,
  editMessage: Boolean,
  userId: Long,
  score: Int,
  force: Boolean
): Message = this.sendFunctionAsync(TdApi.SetGameScore(chatId, messageId, editMessage, userId,
    score, force))

/**
 * Suspend function, which updates the game score of the specified user in a game; for bots only.
 *
 * @param inlineMessageId Inline message identifier.  
 * @param editMessage True, if the message needs to be edited.  
 * @param userId User identifier.  
 * @param score The new score.  
 * @param force Pass true to update the score even if it decreases. If the score is 0, the user will
 * be deleted from the high score table.
 */
suspend fun TelegramFlow.setInlineGameScore(
  inlineMessageId: String?,
  editMessage: Boolean,
  userId: Long,
  score: Int,
  force: Boolean
) = this.sendFunctionLaunch(TdApi.SetInlineGameScore(inlineMessageId, editMessage, userId, score,
    force))
