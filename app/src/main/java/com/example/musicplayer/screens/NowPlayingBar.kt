package com.example.musicplayer.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.musicplayer.R
import com.example.musicplayer.viewmodel.SharedViewModel

@Composable
fun NowPlayingBar(
    sharedViewModel: SharedViewModel,
) {
    val currentSong=sharedViewModel.currentsong
    if (currentSong != null) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.Black),
            verticalAlignment = Alignment.Bottom,
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Row(
                modifier= Modifier.padding(8.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Box(
                    modifier = Modifier
                        .size(50.dp)
                        .clip(CircleShape)
                        .background(Color.Gray)
                ) {
                    currentSong.cover?.let { sharedViewModel.DisplayImage(it) }
                }

                currentSong.name?.let {
                    Text(
                        text = it,
                        fontWeight = FontWeight.Bold,
                        color = Color.White,
                    )
                }
            }

            IconButton(modifier = Modifier.padding(8.dp),
                onClick = {
                    if (sharedViewModel.isPlaying) {
                        sharedViewModel.pauseSong()
                    } else {
                        sharedViewModel.playSong(currentSong)
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
        }
    }
}