package com.example.musicplayer.models

import android.os.Parcel
import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Song(
//    val accent: String,
    val artist: String?,
    var cover: String?,
//    val date_created: String,
//    val date_updated: String,
    val id: Int,
    val name: String?,
//    val sort: Any,
//    val status: String,
    val top_track: Boolean,
    val url: String?,
//    val user_created: String,
//    val user_updated: String
):Parcelable