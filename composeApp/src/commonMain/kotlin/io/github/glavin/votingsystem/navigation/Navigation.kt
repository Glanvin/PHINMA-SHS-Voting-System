package io.github.glavin.votingsystem.navigation

import kotlinx.serialization.Serializable

@Serializable
sealed class Graphs {
    @Serializable data object Auth : Graphs()
    @Serializable data object Home : Graphs()
    @Serializable data object Voting : Graphs()
    @Serializable data object Notification : Graphs()
}

@Serializable
sealed class Destinations {
    @Serializable data object SignIn : Destinations()
    @Serializable data object ForgotPassword : Destinations()
    @Serializable data object Home : Destinations()
    @Serializable data object Voting : Destinations()
    @Serializable data object Notification : Destinations()
}