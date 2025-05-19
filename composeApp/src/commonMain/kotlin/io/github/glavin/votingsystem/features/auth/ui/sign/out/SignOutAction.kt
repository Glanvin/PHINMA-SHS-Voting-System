package io.github.glavin.votingsystem.features.auth.ui.sign.out

sealed interface SignOutAction {
    data object Idle : SignOutAction
    data object SignOut : SignOutAction
    data object Error : SignOutAction
    data object Success : SignOutAction
    data object DialogClose : SignOutAction
    data object DialogOpen : SignOutAction
}