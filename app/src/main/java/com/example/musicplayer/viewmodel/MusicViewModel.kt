package com.example.musicplayer.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.musicplayer.models.Song
import com.example.musicplayer.repository.MusicRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class MusicViewModel @Inject constructor(private val musicRepository: MusicRepository):ViewModel() {
    val songs: StateFlow<List<Song>>
        get()=musicRepository.songs

    val toptracks
        get() = musicRepository.toptracks

    init {
        viewModelScope.launch {
            musicRepository.getSongs()
        }
    }
}