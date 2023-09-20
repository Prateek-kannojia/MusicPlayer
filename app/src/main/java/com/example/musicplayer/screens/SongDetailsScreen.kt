package com.example.musicplayer.screens

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTransformGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.ImagePainter
import coil.compose.rememberImagePainter
import com.example.musicplayer.R
import com.example.musicplayer.models.Song
import com.example.musicplayer.viewmodel.SharedViewModel
import kotlin.math.roundToInt


@Composable
fun SongDetails(sharedViewModel: SharedViewModel) {
    val song = sharedViewModel.currentsong
    if (song != null) {
        SongDetailsScreen(song, sharedViewModel.baseurl, sharedViewModel)
    }
}


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun SongDetailsScreen(
    song: Song,
    baseurl: String,
    sharedViewModel: SharedViewModel,
) {
    val songs = sharedViewModel.songs
    var currentIndex=song.id
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black),
        verticalArrangement = Arrangement.Center,

        ) {
        val modifier=SwipeableImage(sharedViewModel = sharedViewModel)
        Box(
            modifier = modifier
        )  {
            val imageUrl = "$baseurl/assets/${song.cover}"
            val painter: ImagePainter = rememberImagePainter(data = imageUrl)
            Image(
                painter = painter,
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .size(400.dp)
                    .padding(16.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .background(Color.Gray)
            )

        }
        Column(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalAlignment = CenterHorizontally

        ) {
            song.name?.let {
                Text(
                    text = it,
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                    fontSize = 25.sp,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(10.dp)
                )
            }
            song.artist?.let {
                Text(
                    text = it,
                    color = Color.White,
                    fontSize = 15.sp,
                    textAlign = TextAlign.Center
                )
            }
        }
        LinearProgressIndicator(
            progress = 0.3f,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .clip(RoundedCornerShape(5.dp))
                .height(5.dp),
            color = Color.White,
            trackColor = Color.Gray
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(modifier = Modifier.size(60.dp),
                onClick = { sharedViewModel.previous() }
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.previous_icon),
                    contentDescription = "Previous",
                    tint = Color.White
                )
            }

            IconButton(modifier = Modifier.size(100.dp),
                onClick = {
                    if (sharedViewModel.isPlaying) {
                        sharedViewModel.pauseSong()
                    } else {
                        sharedViewModel.playSong(song)
                    }
                }
            ) {
                val icon = if (sharedViewModel.isPlaying) {
                    painterResource(id = R.drawable.pause_icon)
                } else {
                    painterResource(id = R.drawable.play_icon)
                }
                Icon(
                    painter = icon,
                    contentDescription = if (sharedViewModel.isPlaying) "Pause" else "Play",
                    tint = Color.White,
                    modifier = Modifier.size(80.dp)
                )
            }

            IconButton(modifier = Modifier.size(60.dp),
                onClick = { sharedViewModel.next() }
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.next_icon),
                    contentDescription = "Next",
                    tint = Color.White
                )
            }
        }
    }
}

@Composable
fun SwipeableImage(sharedViewModel:SharedViewModel):Modifier{
    var offsetX by remember { mutableStateOf(0f) }
    var lastGestureTime by remember { mutableStateOf(System.currentTimeMillis()) }
    val modifier=
    Modifier
        .pointerInput(Unit) {
            detectTransformGestures { _, panGesture, _, _ ->
                val currentTime = System.currentTimeMillis()
                if (currentTime - lastGestureTime > 250) {
                    lastGestureTime = currentTime
                    when {
                        panGesture.x > 0 -> {
                            offsetX += panGesture.x
                            if (offsetX > 100) {
                                sharedViewModel.previous()
                                offsetX = 0f
                            }
                        }
                        panGesture.x < 0 -> {
                            offsetX += panGesture.x
                            if (offsetX < -100) {
                                sharedViewModel.next()
                                offsetX = 0f
                            }
                        }
                    }
                }
            }
        }
    return modifier
}

