package io.github.glavin.votingsystem.navigation

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import io.github.glavin.votingsystem.core.HomePanel
import io.github.glavin.votingsystem.core.NavigationItems
import io.github.glavin.votingsystem.features.auth.domain.AccountType
import io.github.glavin.votingsystem.features.auth.ui.sign.`in`.SignInScreenRoot
import io.github.glavin.votingsystem.features.auth.ui.sign.`in`.SignInViewModel
import io.github.glavin.votingsystem.features.auth.ui.verification.forgot.password.ForgotPasswordRoot
import io.github.glavin.votingsystem.features.auth.ui.verification.forgot.password.ForgotPasswordViewModel
import io.github.glavin.votingsystem.features.home.ui.HomeScreenRoot
import io.github.glavin.votingsystem.features.home.ui.HomeViewModel
import io.github.glavin.votingsystem.features.voting.ui.list.CandidateListScreenRoot
import io.github.glavin.votingsystem.features.voting.ui.list.CandidateListViewModel
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun AppGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = Graphs.Home,
    ) {
        authNavigation(navController)
        homeNavigation(navController)
        notificationNavigation(navController)
    }
}

private fun NavGraphBuilder.authNavigation(navController: NavHostController) {

    navigation<Graphs.Auth>(
        startDestination = Destinations.SignIn
    ) {
        composable<Destinations.SignIn> {
            val viewModel = koinViewModel<SignInViewModel>()
            SignInScreenRoot(
                viewModel,
                onSuccess = {
                    navController.navigate(Graphs.Home) {
                        launchSingleTop = true
                        popUpTo(Graphs.Auth) { inclusive = true }
                    }
                },
                onForgetPassword = {
                    navController.navigate(Destinations.ForgotPassword) {
                        launchSingleTop = true
                    }
                }
            )
        }

        composable<Destinations.ForgotPassword> {
            val viewModel = koinViewModel<ForgotPasswordViewModel>()
            ForgotPasswordRoot(viewModel)
        }
    }
}

private fun NavGraphBuilder.homeNavigation(navController: NavHostController) {
    navigation<Graphs.Home>(
        startDestination = Destinations.Home
    ) {
        composable<Destinations.Home> {
            val viewModel = koinViewModel<HomeViewModel>()
            HomePanel(
                accountType = AccountType.ADMIN,
                navController = navController,
                selectedTab = NavigationItems.VOTING
            ) {
                HomeScreenRoot(viewModel)
            }
        }

        composable<Destinations.Voting> {
            val viewModel = koinViewModel<CandidateListViewModel>()
            HomePanel(
                accountType = AccountType.ADMIN,
                navController = navController,
                selectedTab = NavigationItems.VOTING
            ) {
                CandidateListScreenRoot(viewModel)
            }
        }
    }
}

private fun NavGraphBuilder.notificationNavigation(navController: NavHostController) {
    navigation<Graphs.Notification>(
        startDestination = Destinations.Notification
    ) {
        composable<Destinations.Notification> {
            HomePanel(
                accountType = AccountType.ADMIN,
                navController = navController,
                selectedTab = NavigationItems.VOTING
            ) {
                Text("Notification")
            }
        }
    }
}
