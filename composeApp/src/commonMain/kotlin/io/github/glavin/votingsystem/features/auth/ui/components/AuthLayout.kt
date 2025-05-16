package io.github.glavin.votingsystem.features.auth.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.unit.*
import androidx.window.core.layout.WindowWidthSizeClass


@Stable
@Composable
fun DynamicLayout(
    modifier: Modifier = Modifier,
    logo: @Composable () -> Unit = {},
    text: String,
    windowWidthSizeClass: WindowWidthSizeClass,
    content: @Composable () -> Unit
) {
    if(windowWidthSizeClass == WindowWidthSizeClass.COMPACT) {
        MobileLayout(
            modifier = modifier,
            logo = logo,
            content = content
        )
    }else {
        DesktopLayout(
            modifier = modifier,
            logo = logo,
            text = text,
            content = content
        )
    }
}

@Stable
@Composable
private fun DesktopLayout(
    modifier: Modifier = Modifier,
    logo: @Composable () -> Unit = {},
    text: String,
    content: @Composable () -> Unit
) {
    Surface {
        Row(modifier = modifier.fillMaxSize()) {
            Box(
                modifier = Modifier
                    .weight(1f),
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    Box(
                        modifier = Modifier
                            .size(200.dp)
                    ) {
                        logo()
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = text,
                        style = MaterialTheme.typography.titleLarge
                    )
                }
            }

            Column(
                modifier = Modifier
                    .weight(1f)
            ) {
                Surface(
                    modifier = Modifier
                        .fillMaxSize(),
                    shape = RoundedCornerShape(topStart = 36.dp, bottomStart = 36.dp),
                    color = MaterialTheme.colorScheme.surfaceContainer
                ) {
                    content()
                }
            }

        }
    }
}

@Stable
@Composable
private fun MobileLayout(
    modifier: Modifier = Modifier,
    logo: @Composable () -> Unit = {},
    content: @Composable () -> Unit
) {
    Surface {
        Column(
            modifier = modifier
                .fillMaxSize(),
            verticalArrangement = Arrangement.Bottom,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Box(
                modifier = Modifier
                    .size(100.dp)
            ) {
                logo()
            }

            Spacer(modifier = Modifier.height(32.dp))

            Surface(
                modifier = Modifier
                    .fillMaxHeight(0.81f)
                    .fillMaxWidth(),
                shape = RoundedCornerShape(topStart = 36.dp, topEnd = 36.dp),
                color = MaterialTheme.colorScheme.surfaceContainer,

                ) {
                content()
            }
        }
    }
}
