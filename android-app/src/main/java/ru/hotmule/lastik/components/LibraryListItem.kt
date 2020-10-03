package ru.hotmule.lastik.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.Text
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Favorite
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.ui.tooling.preview.Preview
import dev.chrisbanes.accompanist.coil.CoilImage
import ru.hotmule.lastik.R
import ru.hotmule.lastik.domain.ListItem
import ru.hotmule.lastik.theme.crimson
import ru.hotmule.lastik.theme.sunflower
import ru.hotmule.lastik.utlis.toCommasString
import ru.hotmule.lastik.utlis.toDateString

@Composable
fun LibraryListItem(
    item: ListItem,
    modifier: Modifier = Modifier,
    scrobbleWidth: Float? = null,
) {
    ConstraintLayout(
        modifier = modifier
            .clickable(onClick = { })
            .preferredHeight(82.dp)
            .fillMaxWidth()
    ) {

        val (scrobbles, rank, image, title, subtitle, date, like, divider) = createRefs()
        val surfaceWidth = if (scrobbleWidth != null && item.scrobbles != null)
            scrobbleWidth * item.scrobbles!!
        else null

        surfaceWidth?.let {
            Surface(
                color = crimson.copy(alpha = 0.1f),
                modifier = Modifier
                    .width(surfaceWidth.dp)
                    .fillMaxHeight()
                    .constrainAs(scrobbles) {
                        end.linkTo(parent.end)
                    }
            ) { }
        }

        item.nowPlaying?.let {
            if (it) {
                Surface(
                    color = sunflower.copy(alpha = 0.1f),
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight()
                ) { }
            }
        }

        item.position?.let {
            Text(
                text = it.toString(),
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.body2,
                modifier = Modifier
                    .preferredWidth(24.dp)
                    .constrainAs(rank) {
                        start.linkTo(parent.start, 16.dp)
                        top.linkTo(parent.top)
                        bottom.linkTo(parent.bottom)
                    }
            )
        }

        item.imageUrl?.let {
            CoilImage(
                data = it,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .height(50.dp)
                    .width(50.dp)
                    .clip(shape = RoundedCornerShape(8))
                    .constrainAs(image) {
                        top.linkTo(parent.top, 16.dp)
                        bottom.linkTo(parent.bottom, 16.dp)
                        start.linkTo(
                            if (item.position != null) rank.end else parent.start, 16.dp
                        )
                    }
            )
        }

        item.title?.let {
            Text(
                text = it,
                maxLines = 1,
                fontWeight = FontWeight.Bold,
                style = MaterialTheme.typography.body1,
                modifier = Modifier
                    .constrainAs(title) {
                        start.linkTo(
                            when {
                                item.imageUrl != null -> image.end
                                item.position != null -> rank.end
                                else -> parent.start
                            },
                            16.dp
                        )
                        top.linkTo(parent.top, 16.dp)
                        if (item.subtitle == null) bottom.linkTo(parent.bottom, 16.dp)
                    }
            )
        }

        item.subtitle?.let {
            ProvideEmphasis(emphasis = EmphasisAmbient.current.medium) {
                Text(
                    text = it,
                    style = MaterialTheme.typography.body2,
                    modifier = Modifier
                        .constrainAs(subtitle) {
                            start.linkTo(title.start)
                            bottom.linkTo(parent.bottom, 16.dp)
                        }
                )
            }
        }

        if (item.time != null ||
            item.nowPlaying == true ||
            item.scrobbles != null) {

            ProvideEmphasis(emphasis = EmphasisAmbient.current.medium) {
                Text(
                    text = when {
                        item.time != null -> item.time!!.toDateString("d MMM, HH:mm")
                        item.nowPlaying != null -> stringResource(R.string.scrobbling_now)
                        else -> item.scrobbles!!.toCommasString() + " " +
                                stringResource(R.string.scrobbles)
                    },
                    style = MaterialTheme.typography.body2,
                    modifier = Modifier
                        .constrainAs(date) {
                            end.linkTo(parent.end, 16.dp)
                            bottom.linkTo(parent.bottom, 16.dp)
                        }
                )
            }
        }


        item.scrobbles?.let {
            ProvideEmphasis(emphasis = EmphasisAmbient.current.high) {
                Text(
                    text = it.toCommasString() + " " + stringResource(id = R.string.scrobbles),
                    style = MaterialTheme.typography.body1,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .constrainAs(date) {
                            end.linkTo(parent.end, 16.dp)
                            bottom.linkTo(parent.bottom, 16.dp)
                        }
                )
            }
        }

        item.loved?.let {
            IconButton(
                onClick = {

                },
                icon = {
                    Image(
                        asset = Icons.Rounded.Favorite,
                        colorFilter = ColorFilter.tint(crimson)
                    )
                },
                modifier = Modifier
                    .clickable(onClick = { })
                    .constrainAs(like) {
                        end.linkTo(parent.end, 8.dp)
                        top.linkTo(parent.top)
                        bottom.linkTo(parent.bottom)
                    }
            )
        }

        Divider(
            modifier = Modifier.constrainAs(divider) {
                bottom.linkTo(parent.bottom)
            }
        )
    }
}

@Preview
@Composable
fun ScrobblePreview() = LibraryListItem(
    ListItem(
        imageUrl = "imageUrl",
        title = "Man",
        subtitle = "Skepta",
        time = 113243214
    )
)

@Preview
@Composable
fun ArtistPreview() = LibraryListItem(
    ListItem(
        position = 2,
        title = "Skepta",
        scrobbles = 500
    )
)

@Preview
@Composable
fun AlbumPreview() = LibraryListItem(
    ListItem(
        position = 20,
        imageUrl = "imageUrl",
        title = "Konnichiva",
        subtitle = "Skepta",
        scrobbles = 500
    )
)

@Preview
@Composable
fun TrackPreview() = LibraryListItem(
    ListItem(
        position = 200,
        imageUrl = "imageUrl",
        title = "Shutdown",
        subtitle = "Skepta",
        scrobbles = 500
    )
)

@Preview
@Composable
fun LovedTrackPreview() = LibraryListItem(
    ListItem(
        loved = true,
        imageUrl = "imageUrl",
        title = "Man",
        subtitle = "Skepta"
    )
)