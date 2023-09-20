package com.example.musicplayer.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.musicplayer.models.Song
import com.example.musicplayer.viewmodel.MusicViewModel
import com.example.musicplayer.viewmodel.SharedViewModel

@Composable
fun TopTracks(
    navController: NavHostController,
    sharedViewModel: SharedViewModel
) {
    val songviewmodel: MusicViewModel = hiltViewModel()
    val toptracks =songviewmodel.toptracks
    sharedViewModel.toptracks=toptracks
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
    ) {
        items(toptracks) {
            TopTracksItems(it,navController,sharedViewModel)
        }
    }
}

@Composable
fun TopTracksItems(
    song: Song,
    navController: NavHostController,
    sharedViewModel: SharedViewModel
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                sharedViewModel.stop()
                sharedViewModel.playSong(song)
                sharedViewModel.isPlaying=true
                navController.navigate(Screen.SongDetailScreen.route)
            }
            .padding(10.dp),
        colors = CardDefaults.cardColors(Color.Black)
    ) {
        Row(
            modifier = Modifier
                .padding(10.dp)
                .fillMaxWidth()
        ) {
            Box(
                modifier = Modifier
                    .size(50.dp)
                    .clip(CircleShape)
                    .background(Color.Gray)
            ) {
                song.cover?.let { sharedViewModel.DisplayImage(it) }
            }

            Box(
                modifier = Modifier
                    .fillMaxHeight()
            ) {
                Column(
                    modifier = Modifier
                        .align(Alignment.CenterStart)
                        .padding(5.dp)
                ) {
                    song.name?.let {
                        Text(
                            text = it,
                            fontWeight = FontWeight.Bold,
                            color = Color.White,
                        )
                    }
                    song.artist?.let {
                        Text(
                            text = it,
                            color = Color.White
                        )
                    }
                }
            }
        }
    }
}
