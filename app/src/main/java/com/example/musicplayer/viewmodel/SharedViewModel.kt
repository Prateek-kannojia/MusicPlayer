package com.example.musicplayer.viewmodel

import android.media.MediaMetadataRetriever
import android.media.MediaPlayer
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.lifecycle.ViewModel
import coil.compose.ImagePainter
import coil.compose.rememberImagePainter
import com.example.musicplayer.models.Song
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.Timer
import java.util.TimerTask

class SharedViewModel : ViewModel() {
    val baseurl = "https://cms.samespace.com"
    var currentsong by mutableStateOf<Song?>(null)
        private set
    var isPlaying by mutableStateOf(false)
    var currentSongId: Int = currentsong?.id ?: 1
        private set
    var isPlaybarVisible by mutableStateOf(false)
        private set
    private var mediaPlayer: MediaPlayer? = null
    var songs: State<List<Song>> = mutableStateOf(emptyList())
    var toptracks: List<Song> = emptyList()
    var fillteredlist: List<Song> = emptyList()

    fun list(tab: Int) {
        fillteredlist = if (tab == 0) {
            songs.value
        } else {
            toptracks
        }
    }

    fun playSong(song: Song) {
        currentsong = song
        if (mediaPlayer == null) {
            mediaPlayer = MediaPlayer()
            mediaPlayer?.setDataSource(song.url)
            mediaPlayer?.prepareAsync()
            mediaPlayer?.setOnPreparedListener { player ->
                player.start()
                isPlaying = true
                isPlaybarVisible=true
            }
        } else {
            mediaPlayer?.start()
            isPlaying = true
            isPlaybarVisible=true
        }
    }

    fun stop() {
        mediaPlayer?.apply {
            if (isPlaying) {
                stop()
            }
            release()
        }
        mediaPlayer = null
    }

    fun pauseSong() {
        mediaPlayer?.pause()
        isPlaying = false
    }

    fun next() {
        val currentSong = currentsong
        currentSong?.let {
            val currentIndex = fillteredlist.indexOf(it)
            if (currentIndex < fillteredlist.size - 1) {
                val nextSong = fillteredlist[currentIndex + 1]
                stop()
                playSong(nextSong)
                currentSongId = nextSong.id
            }
        }
    }

    fun previous() {
        val currentSong = currentsong
        currentSong?.let {
            val currentIndex = fillteredlist.indexOf(it)
            if (currentIndex > 0) {
                val previousSong = fillteredlist[currentIndex - 1]
                stop()
                playSong(previousSong)
                currentSongId = previousSong.id
            }
        }
    }

    @Composable
    fun DisplayImage(cover:String) {
        val baseImageUrl = baseurl
        val imageUrl = "$baseImageUrl/assets/${cover}"
        val painter: ImagePainter = rememberImagePainter(data = imageUrl)
        Image(
            painter = painter,
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxSize()
        )
    }
}
