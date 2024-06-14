//
// NOTE: THIS FILE IS AUTO-GENERATED by the "TdApiKtxGenerator".kt
// See: https://github.com/tdlibx/td-ktx-generator/
//
package kotlinx.telegram.flows

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.mapNotNull
import kotlinx.telegram.core.TelegramFlow
import org.drinkless.td.libcore.telegram.TdApi
import org.drinkless.td.libcore.telegram.TdApi.Call
import org.drinkless.td.libcore.telegram.TdApi.GroupCall
import org.drinkless.td.libcore.telegram.TdApi.UpdateGroupCallParticipant
import org.drinkless.td.libcore.telegram.TdApi.UpdateNewCallSignalingData
import org.drinkless.td.libcore.telegram.TdApi.UpdateNewCallbackQuery
import org.drinkless.td.libcore.telegram.TdApi.UpdateNewInlineCallbackQuery

/**
 * emits [Call] if new call was created or information about a call was updated.
 */
fun TelegramFlow.callFlow(): Flow<Call> = this.getUpdatesFlowOfType<TdApi.UpdateCall>()
    .mapNotNull { it.call }

/**
 * emits [GroupCall] if information about a group call was updated.
 */
fun TelegramFlow.groupCallFlow(): Flow<GroupCall> =
    this.getUpdatesFlowOfType<TdApi.UpdateGroupCall>()
    .mapNotNull { it.groupCall }

/**
 * emits [UpdateGroupCallParticipant] if information about a group call participant was changed. The
 * updates are sent only after the group call is received through getGroupCall and only if the call is
 * joined or being joined.
 */
fun TelegramFlow.groupCallParticipantFlow(): Flow<UpdateGroupCallParticipant> =
    this.getUpdatesFlowOfType()

/**
 * emits [UpdateNewCallSignalingData] if new call signaling data arrived.
 */
fun TelegramFlow.newCallSignalingDataFlow(): Flow<UpdateNewCallSignalingData> =
    this.getUpdatesFlowOfType()

/**
 * emits [UpdateNewCallbackQuery] if a new incoming callback query; for bots only.
 */
fun TelegramFlow.newCallbackQueryFlow(): Flow<UpdateNewCallbackQuery> = this.getUpdatesFlowOfType()

/**
 * emits [UpdateNewInlineCallbackQuery] if a new incoming callback query from a message sent via a
 * bot; for bots only.
 */
fun TelegramFlow.newInlineCallbackQueryFlow(): Flow<UpdateNewInlineCallbackQuery> =
    this.getUpdatesFlowOfType()
