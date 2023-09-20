package com.example.musicplayer.screens

sealed class Screen(val route:String){
    object ForYouTab:Screen("For_You_Tab")
    object TopTracksTab:Screen("Top_Tracks_Tab")
    object SongDetailScreen:Screen("Song_Detail_Screen")
}
