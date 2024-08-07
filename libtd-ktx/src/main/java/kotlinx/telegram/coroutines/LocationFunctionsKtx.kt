//
// NOTE: THIS FILE IS AUTO-GENERATED by the "TdApiKtxGenerator".kt
// See: https://github.com/tdlibx/td-ktx-generator/
//
package kotlinx.telegram.coroutines

import kotlinx.telegram.core.TelegramFlow
import org.drinkless.tdlib.TdApi
import org.drinkless.tdlib.TdApi.BusinessLocation
import org.drinkless.tdlib.TdApi.Location

/**
 * Suspend function, which changes the business location of the current user. Requires Telegram
 * Business subscription.
 *
 * @param location The new location of the business; pass null to remove the location.
 */
suspend fun TelegramFlow.setBusinessLocation(location: BusinessLocation?) =
    this.sendFunctionLaunch(TdApi.SetBusinessLocation(location))

/**
 * Suspend function, which changes the location of the current user. Needs to be called if
 * getOption(&quot;is_location_visible&quot;) is true and location changes for more than 1 kilometer.
 * Must not be called if the user has a business location.
 *
 * @param location The new location of the user.
 */
suspend fun TelegramFlow.setLocation(location: Location?) =
    this.sendFunctionLaunch(TdApi.SetLocation(location))
