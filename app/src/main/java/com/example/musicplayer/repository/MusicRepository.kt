package com.example.musicplayer.repository

import android.util.Log
import com.example.musicplayer.api.MusicApiService
import com.example.musicplayer.models.Song
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import retrofit2.Response.success
import javax.inject.Inject

class MusicRepository @Inject constructor(private val musicApiService: MusicApiService) {
    private val _songs = MutableStateFlow<List<Song>>(emptyList())
    val songs: StateFlow<List<Song>> = _songs

    var toptracks= emptyList<Song>()

    suspend fun getSongs(){
        val response=musicApiService.getSongs()
        if(response.isSuccessful&&response.body()!=null){
            val songs=response.body()?.data?: emptyList()
            _songs.value=songs
            toptracks=_songs.value.filter { it.top_track }
        }
    }
}