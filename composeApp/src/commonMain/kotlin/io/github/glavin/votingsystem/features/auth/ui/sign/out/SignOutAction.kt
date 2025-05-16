package io.github.glavin.votingsystem.features.auth.ui.sign.out

sealed interface SignOutAction {
    object SignOut: SignOutAction
    object Error: SignOutAction
}