package io.github.glavin.votingsystem.core

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Ballot
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.outlined.Ballot
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import io.github.glavin.votingsystem.core.ui.ChiroTheme
import io.github.glavin.votingsystem.core.ui.NavigationSuiteScaffoldFab
import io.github.glavin.votingsystem.core.ui.ObserveAsEvents
import io.github.glavin.votingsystem.core.ui.SnackbarController
import io.github.glavin.votingsystem.features.auth.domain.AccountType
import io.github.glavin.votingsystem.navigation.AppGraph
import io.github.glavin.votingsystem.navigation.Destinations
import kotlinx.coroutines.launch

@Composable
fun Main(
    dynamicColor: Boolean = false,
    darkTheme: Boolean = false,
) {

    val navController = rememberNavController()

    ChiroTheme(
        dynamicColor = dynamicColor,
        darkTheme = false
    ) {
        AppGraph(navController = navController)
    }
}

@Composable
fun HomePanel(
    accountType: AccountType? = AccountType.STUDENT,
    navController: NavHostController,
    selectedTab: NavigationItems,
    snackbarHost: @Composable () -> Unit = {},
    content: @Composable () -> Unit
) {

    val entry by navController.currentBackStackEntryAsState()
    val currentDestination = entry?.destination
    val scope = rememberCoroutineScope()
    val snackbarHostState = remember {
        SnackbarHostState()
    }

    ObserveAsEvents(
        flow = SnackbarController.snackbarEvents
    ) { events ->
        scope.launch {
            snackbarHostState.currentSnackbarData?.dismiss()
            val result = snackbarHostState.showSnackbar(
                message = events.message,
                actionLabel = events.action?.name,
                duration = events.duration
            )

            if (result == SnackbarResult.ActionPerformed) {
                events.action?.action?.invoke()
            }
        }
    }

    NavigationSuiteScaffoldFab(
        navigationSuiteItems = {
                NavigationItems.entries.forEachIndexed { _, navigationItem ->
                    item(
                        selected = currentDestination?.hierarchy?.any {
                            it.hasRoute(navigationItem.destination::class)
                        } == true,
                        onClick = {
                            navController.navigate(navigationItem.destination) {
                                popUpTo(navController.graph.findStartDestination().id) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        },
                        icon = {
                            Icon(
                                imageVector = if(currentDestination?.hierarchy?.any {
                                        it.hasRoute(navigationItem.destination::class)
                                    } == true) {
                                    navigationItem.selectedIcon
                                } else {
                                    navigationItem.unSelectedIcon
                                },
                                contentDescription = navigationItem.label
                            )
                        },
                        label = {
                            Text(text = navigationItem.label)
                        },

                        )
                }
        },
        floatingActionButton = {
            if (accountType == AccountType.ADMIN) {
                FloatingActionButton(
                    onClick = {},
                ) {
                    Icon(
                        imageVector = Icons.Outlined.Add,
                        contentDescription = "Add Candidate"
                    )
                }
            }
        },
        snackbarHost = {
            SnackbarHost(snackbarHostState)
        },
        content = content
    )
}

enum class NavigationItems(
    val label: String,
    val destination: Destinations,
    val selectedIcon: ImageVector,
    val unSelectedIcon: ImageVector
) {
    HOME("Home", Destinations.Home, Icons.Filled.Home, Icons.Outlined.Home),
    VOTING("Vote", Destinations.Voting, Icons.Filled.Ballot, Icons.Outlined.Ballot),
    NOTIFICATIONS(
        "Updates",
        Destinations.Notification,
        Icons.Filled.Notifications,
        Icons.Outlined.Notifications
    ),
}