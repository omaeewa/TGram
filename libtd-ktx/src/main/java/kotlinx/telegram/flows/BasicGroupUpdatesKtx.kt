//
// NOTE: THIS FILE IS AUTO-GENERATED by the "TdApiKtxGenerator".kt
// See: https://github.com/tdlibx/td-ktx-generator/
//
package kotlinx.telegram.flows

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.mapNotNull
import kotlinx.telegram.core.TelegramFlow
import org.drinkless.td.libcore.telegram.TdApi
import org.drinkless.td.libcore.telegram.TdApi.BasicGroup
import org.drinkless.td.libcore.telegram.TdApi.UpdateBasicGroupFullInfo

/**
 * emits [BasicGroup] if some data of a basic group has changed. This update is guaranteed to come
 * before the basic group identifier is returned to the application.
 */
fun TelegramFlow.basicGroupFlow(): Flow<BasicGroup> =
    this.getUpdatesFlowOfType<TdApi.UpdateBasicGroup>()
    .mapNotNull { it.basicGroup }

/**
 * emits [UpdateBasicGroupFullInfo] if some data from basicGroupFullInfo has been changed.
 */
fun TelegramFlow.basicGroupFullInfoFlow(): Flow<UpdateBasicGroupFullInfo> =
    this.getUpdatesFlowOfType()
