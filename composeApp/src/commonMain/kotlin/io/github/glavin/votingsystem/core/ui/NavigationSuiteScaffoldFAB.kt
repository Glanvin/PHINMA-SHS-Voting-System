package io.github.glavin.votingsystem.core.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DrawerDefaults
import androidx.compose.material3.NavigationBarDefaults
import androidx.compose.material3.NavigationRailDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuite
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteColors
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteDefaults
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteScaffoldDefaults
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteScaffoldLayout
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteScope
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteType
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp


@Composable
fun NavigationSuiteScaffoldFab(
    navigationSuiteItems: NavigationSuiteScope.() -> Unit,
    modifier: Modifier = Modifier,
    layoutType: NavigationSuiteType =
        NavigationSuiteScaffoldDefaults.calculateFromAdaptiveInfo(currentWindowAdaptiveInfo()),
    navigationSuiteColors: NavigationSuiteColors = NavigationSuiteDefaults.colors(),
    containerColor: Color = NavigationSuiteScaffoldDefaults.containerColor,
    contentColor: Color = NavigationSuiteScaffoldDefaults.contentColor,
    floatingActionButton: @Composable () -> Unit = {},
    snackbarHost: @Composable () -> Unit = {},
    content: @Composable () -> Unit,
) {
    Surface(modifier = modifier, color = containerColor, contentColor = contentColor) {
        NavigationSuiteScaffoldLayout(
            navigationSuite = {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    if (layoutType == NavigationSuiteType.NavigationRail
                        || layoutType == NavigationSuiteType.NavigationDrawer) {
                        Box(
                            modifier = Modifier.padding(16.dp)
                        ) {
                            floatingActionButton()
                        }
                    }
                    NavigationSuite(
                        layoutType = layoutType,
                        colors = navigationSuiteColors,
                        content = navigationSuiteItems
                    )
                }
            },
            layoutType = layoutType,
            content = {
                Scaffold(
                    modifier = Modifier.consumeWindowInsets(
                        when (layoutType) {
                            NavigationSuiteType.NavigationBar ->
                                NavigationBarDefaults.windowInsets.only(WindowInsetsSides.Bottom)
                            NavigationSuiteType.NavigationRail ->
                                NavigationRailDefaults.windowInsets.only(WindowInsetsSides.Start)
                            NavigationSuiteType.NavigationDrawer ->
                                DrawerDefaults.windowInsets.only(WindowInsetsSides.Start)
                            else -> WindowInsets(0, 0, 0, 0)
                        }
                    ),
                    floatingActionButton = {
                        if (layoutType == NavigationSuiteType.NavigationBar) {
                            floatingActionButton()
                        }
                    },
                    snackbarHost = snackbarHost
                ) {
                    Box(modifier = Modifier.padding(it)) {
                        content()
                    }
                }
            }
        )
    }
}

@Composable
private fun CenteredNavigationSuiteContent(
    layoutType: NavigationSuiteType,
    navigationSuiteColors: NavigationSuiteColors,
    navigationSuiteItems: NavigationSuiteScope.() -> Unit,
    floatingActionButton: @Composable () -> Unit,
) {
    Box(modifier = Modifier.fillMaxHeight()) {
        // Position FAB at top-center when using rail/drawer
        if (layoutType == NavigationSuiteType.NavigationRail ||
            layoutType == NavigationSuiteType.NavigationDrawer
        ) {
            Box(
                modifier = Modifier
                    .align(Alignment.TopCenter)
                    .padding(16.dp)
            ) {
                floatingActionButton()
            }
        }

        // Centered navigation suite (rail/drawer)
        NavigationSuite(
            modifier = Modifier.align(Alignment.Center),
            layoutType = layoutType,
            colors = navigationSuiteColors,
            content = navigationSuiteItems
        )
    }
}

// Your original scaffold remains the same except for the navigationSuite parameter
@Composable
fun NavigationSuiteScaffoldFabCentered(
    navigationSuiteItems: NavigationSuiteScope.() -> Unit,
    modifier: Modifier = Modifier,
    layoutType: NavigationSuiteType =
        NavigationSuiteScaffoldDefaults.calculateFromAdaptiveInfo(currentWindowAdaptiveInfo()),
    navigationSuiteColors: NavigationSuiteColors = NavigationSuiteDefaults.colors(),
    containerColor: Color = NavigationSuiteScaffoldDefaults.containerColor,
    contentColor: Color = NavigationSuiteScaffoldDefaults.contentColor,
    floatingActionButton: @Composable () -> Unit = {},
    snackbarHost: @Composable () -> Unit = {},
    content: @Composable () -> Unit = {},
) {
    Surface(modifier = modifier, color = containerColor, contentColor = contentColor) {
        NavigationSuiteScaffoldLayout(
            navigationSuite = {
                CenteredNavigationSuiteContent(
                    layoutType = layoutType,
                    navigationSuiteColors = navigationSuiteColors,
                    navigationSuiteItems = navigationSuiteItems,
                    floatingActionButton = floatingActionButton
                )
            },
            layoutType = layoutType,
            content = {
                Scaffold(
                    modifier = Modifier.consumeWindowInsets(
                        when (layoutType) {
                            NavigationSuiteType.NavigationBar ->
                                NavigationBarDefaults.windowInsets.only(WindowInsetsSides.Bottom)
                            NavigationSuiteType.NavigationRail ->
                                NavigationRailDefaults.windowInsets.only(WindowInsetsSides.Start)
                            NavigationSuiteType.NavigationDrawer ->
                                DrawerDefaults.windowInsets.only(WindowInsetsSides.Start)
                            else -> WindowInsets(0, 0, 0, 0)
                        }
                    ),
                    floatingActionButton = {
                        if (layoutType == NavigationSuiteType.NavigationBar) {
                            floatingActionButton()
                        }
                    },
                    snackbarHost = { snackbarHost() }
                ) {
                    Box(modifier = Modifier.padding(it)) {
                        content()
                    }
                }
            }
        )
    }
}