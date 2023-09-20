package com.example.musicplayer.api

import com.example.musicplayer.models.Data
import com.example.musicplayer.models.Song
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface MusicApiService {
    @GET("items/songs")
    suspend fun getSongs(): Response<Data>

}