package io.github.glavin.votingsystem.features.voting.ui.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import chiro.composeapp.generated.resources.Res
import chiro.composeapp.generated.resources.compose_multiplatform
import coil3.compose.AsyncImage
import coil3.compose.AsyncImagePainter
import coil3.compose.rememberAsyncImagePainter
import io.github.glavin.votingsystem.features.voting.domain.Candidate
import io.github.glavin.votingsystem.features.voting.ui.list.CandidateListState
import io.github.glavin.votingsystem.features.voting.ui.list.CandidateListViewModel
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview

@Stable
@Composable
fun CandidateCardItem(
    candidate: Candidate,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Card(
        modifier = modifier,
        elevation = CardDefaults.cardElevation(5.dp),
        onClick = onClick
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .background(Color.Cyan)
                    .clip(RoundedCornerShape(20.dp)),
                contentAlignment = Alignment.Center
            ) {
//                AsyncImage(
//                    model = candidate.photoUrl,
//                    contentDescription = "Candidate Photo",
//                    modifier = Modifier.clip(RoundedCornerShape(20.dp))
//                )
            }
        }
    }
}
@Stable
@Composable
fun CandidateListCardItem(
    modifier: Modifier = Modifier,
    candidate: Candidate,
    onClick: (Candidate) -> Unit = {},
    cardSize: Dp,
    profileSize: Dp
) {
    Card(
        modifier = modifier
            .size(cardSize)
            .clickable {
                onClick(candidate)
            },
        elevation = CardDefaults.cardElevation(
            defaultElevation = 2.dp
        )
    ) {
        Column(
            modifier = Modifier
                .weight(1f),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .size(profileSize)
                    .fillMaxWidth()
                    .height(100.dp),
                contentAlignment = Alignment.Center,
            ) {
                var imageLoadResult by remember {
                    mutableStateOf<Result<Painter>?>(null)
                }

                val painter = rememberAsyncImagePainter(
                    model = candidate.photoUrl,
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

                when(val result = imageLoadResult) {
                    null -> CircularProgressIndicator(Modifier.size(52.dp))
                    else -> {
                        Image(
                            painter = if(result.isSuccess) painter else {
                                painterResource(Res.drawable.compose_multiplatform)
                            },
                            contentDescription = "${candidate.lastName} Photo",
                            contentScale = if(result.isSuccess) ContentScale.Crop else ContentScale.Fit,
                            modifier = Modifier.clip(CircleShape)
                        )
                    }
                }
            }

            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(8.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxHeight()
                        .weight(1f)
                ) {

                    HorizontalDivider(modifier = Modifier.fillMaxWidth().padding(horizontal = 8.dp))

                    Text(
                        text = "${candidate.firstName} ${candidate.lastName}",
                        style = MaterialTheme.typography.titleMedium,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis
                    )
                    Text(
                        text = candidate.party,
                        style = MaterialTheme.typography.bodyMedium,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis
                    )
                    Text(
                        text = "Grade ${candidate.grade}",
                        style = MaterialTheme.typography.bodyMedium,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            }
        }
    }
}
