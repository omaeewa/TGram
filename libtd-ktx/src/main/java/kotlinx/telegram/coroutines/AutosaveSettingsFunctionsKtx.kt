//
// NOTE: THIS FILE IS AUTO-GENERATED by the "TdApiKtxGenerator".kt
// See: https://github.com/tdlibx/td-ktx-generator/
//
package kotlinx.telegram.coroutines

import kotlinx.telegram.core.TelegramFlow
import org.drinkless.tdlib.TdApi
import org.drinkless.tdlib.TdApi.AutosaveSettings
import org.drinkless.tdlib.TdApi.AutosaveSettingsScope
import org.drinkless.tdlib.TdApi.ScopeAutosaveSettings

/**
 * Suspend function, which clears the list of all autosave settings exceptions. The method is
 * guaranteed to work only after at least one call to getAutosaveSettings.
 */
suspend fun TelegramFlow.clearAutosaveSettingsExceptions() =
    this.sendFunctionLaunch(TdApi.ClearAutosaveSettingsExceptions())

/**
 * Suspend function, which returns autosave settings for the current user.
 *
 * @return [AutosaveSettings] Describes autosave settings.
 */
suspend fun TelegramFlow.getAutosaveSettings(): AutosaveSettings =
    this.sendFunctionAsync(TdApi.GetAutosaveSettings())

/**
 * Suspend function, which sets autosave settings for the given scope. The method is guaranteed to
 * work only after at least one call to getAutosaveSettings.
 *
 * @param scope Autosave settings scope.  
 * @param settings New autosave settings for the scope; pass null to set autosave settings to
 * default.
 */
suspend fun TelegramFlow.setAutosaveSettings(scope: AutosaveSettingsScope?,
    settings: ScopeAutosaveSettings?) = this.sendFunctionLaunch(TdApi.SetAutosaveSettings(scope,
    settings))