package com.miracle.authorization.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.miracle.authorization.AuthorizationRoute
import kotlinx.serialization.Serializable

@Serializable
object Authorization

fun NavController.navigateToAuthorization(navOptions: NavOptions) = navigate(Authorization, navOptions)

fun NavGraphBuilder.authorizationScreen(navigateToChats: () -> Unit) = composable<Authorization> {
    AuthorizationRoute(navigateToChats = navigateToChats)
}
