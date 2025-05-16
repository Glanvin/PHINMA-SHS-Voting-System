package io.github.glavin.votingsystem.features.home.ui.component

import androidx.compose.animation.animateColor
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.window.core.layout.WindowWidthSizeClass
import chiro.composeapp.generated.resources.Res
import chiro.composeapp.generated.resources.compose_multiplatform
import coil3.compose.rememberAsyncImagePainter
import io.github.glavin.votingsystem.core.ui.rainbowColor
import io.github.glavin.votingsystem.features.auth.domain.AccountType
import io.github.glavin.votingsystem.features.home.domain.User
import org.jetbrains.compose.resources.painterResource

@Stable
@Composable
fun ProfileDetailsItem(
    user: User,
) {
    var imageLoadResult by remember {
        mutableStateOf<Result<Painter>?>(null)
    }

    val windowWidthSizeClass = currentWindowAdaptiveInfo().windowSizeClass.windowWidthSizeClass

    val sizes = if (windowWidthSizeClass == WindowWidthSizeClass.COMPACT) 100.dp else 150.dp

    val painter = rememberAsyncImagePainter(
        model = user.profilePicture,
        onSuccess = {
            imageLoadResult = if (it.painter.intrinsicSize.width > 1 && it.painter.intrinsicSize.height > 1) {
                Result.success(it.painter)
            } else {
                Result.failure(Exception("Invalid image size"))
            }
        },
        onError = {
            it.result.throwable.printStackTrace()
            imageLoadResult = Result.failure(it.result.throwable)
        }
    )

    val transition = rememberInfiniteTransition("Profile Picture Transition")
    val animatedColor by transition.animateColor(
        initialValue = rainbowColor[0],
        targetValue = rainbowColor[3],
        label = "Profile Picture Color",
        animationSpec = infiniteRepeatable(
            animation = tween(2000),
            repeatMode = RepeatMode.Reverse
        )
    )


    val coloredUsername = buildAnnotatedString {
        withStyle(style = SpanStyle(color = animatedColor)) {
            append(user.firstName + " " + user.lastName)
        }
    }

    when(val result = imageLoadResult) {
        null -> CircularProgressIndicator(Modifier.size(sizes))
        else -> {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ){
                Image(
                    painter = if (result.isSuccess) painter else painterResource(Res.drawable.compose_multiplatform),
                    contentDescription = "Profile Picture of ${user.firstName} ${user.lastName}",
                    modifier = Modifier
                        .size(sizes)
                        .clip(CircleShape)
                )

                HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp, horizontal = 16.dp))

                Text(
                    text = coloredUsername,
                    style = MaterialTheme.typography.displaySmall,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )

                Spacer(modifier = Modifier.height(8.dp))

                if (user.accountType != AccountType.ADMIN || user.accountType != AccountType.FACULTY) {
                    Text(
                        text = "Grade ${user.grade}",
                        style = MaterialTheme.typography.bodyMedium,
                        maxLines = 1,
                    )
                    Text(
                        text = user.strand,
                        style = MaterialTheme.typography.bodyMedium,
                        maxLines = 1,
                    )
                }
            }
        }
    }
}

@Composable
fun StudentProfileCard(
    user: User,
    windowWidthSizeClass: WindowWidthSizeClass
) {
    val isCompactWidth = windowWidthSizeClass == WindowWidthSizeClass.COMPACT

    val firstName = user.firstName.split(" ")[0]

    val surfaceModifier = if (isCompactWidth) {
        // For compact screens: 80% width, rounded top corners, aligned at bottom
        Modifier
            .fillMaxWidth(0.8f)
            .clip(RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp))
            .wrapContentHeight()
    } else {
        // For larger screens: 80% width, all rounded corners, centered
        Modifier
            .fillMaxWidth(0.8f)
            .clip(RoundedCornerShape(24.dp))
            .wrapContentHeight()
    }

    Surface(
        modifier = surfaceModifier,
        tonalElevation = 8.dp,
        shadowElevation = 8.dp,
    ) {
        Column(
            modifier = Modifier
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {

            Box(
                modifier = Modifier
                    .size(120.dp)
                    .clip(CircleShape)
                    .background(MaterialTheme.colorScheme.primaryContainer),
                contentAlignment = Alignment.Center
            ) {

                Icon(
                    painter = painterResource(Res.drawable.compose_multiplatform),
                    contentDescription = "Profile Picture of $firstName",
                    modifier = Modifier.size(64.dp),
                    tint = MaterialTheme.colorScheme.onPrimaryContainer
                )
            }

            Text(
                text = "Hello, $firstName",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center
            )

            Text(
                text = if (user.isNewUser) "Welcome" else "Welcome Back",
                style = MaterialTheme.typography.titleLarge,
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.secondary
            )

            HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))

            // School ID
            Text(
                text = user.schoolId,
                style = MaterialTheme.typography.titleLarge,
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Medium
            )

            // Grade & Strand
            Text(
                text = "Grade: ${user.grade}, Strand: ${user.strand}",
                style = MaterialTheme.typography.titleLarge,
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Medium
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Voting Status
            val votingStatusColor = if (user.hasVoted) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.error
            val votingStatusText = if (user.hasVoted) "Voted" else "Not Yet Voted"

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp),
                colors = CardDefaults.cardColors(
                    containerColor = votingStatusColor.copy(alpha = 0.1f)
                )
            ) {
                Text(
                    text = votingStatusText,
                    modifier = Modifier
                        .padding(vertical = 12.dp)
                        .fillMaxWidth(),
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold,
                    color = votingStatusColor
                )
            }
        }
    }
}
