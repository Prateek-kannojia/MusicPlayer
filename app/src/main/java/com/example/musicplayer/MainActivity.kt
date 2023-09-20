package com.example.musicplayer

import android.app.Activity
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.unit.dp
import androidx.core.view.ViewCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.musicplayer.screens.NowPlayingBar
import com.example.musicplayer.screens.Screen
import com.example.musicplayer.screens.SongDetails
import com.example.musicplayer.screens.SongScreen
import com.example.musicplayer.screens.TopTracks
import com.example.musicplayer.ui.theme.MusicPlayerTheme
import com.example.musicplayer.viewmodel.SharedViewModel
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    lateinit var navController: NavHostController
    lateinit var sharedViewModel: SharedViewModel

    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        setContent {
            MusicPlayerTheme {
                navController= rememberNavController()
                sharedViewModel= viewModel()
                NavGraph(navController,sharedViewModel)
            }
        }
    }
}

@Composable
fun SetStatusBarColor(color: Color) {
    val systemUiController = rememberSystemUiController()
    SideEffect {
        systemUiController.setSystemBarsColor(color)
    }
}
@Composable
fun NavGraph(navController: NavHostController, sharedViewModel: SharedViewModel) {
    NavHost(navController, startDestination = Screen.ForYouTab.route) {
        composable(route = Screen.ForYouTab.route) {
            TabbedView(navController,sharedViewModel)
        }
        composable(route = Screen.TopTracksTab.route) {
            TabbedView(navController,sharedViewModel)
        }
        composable(route = Screen.SongDetailScreen.route) {
            SongDetails(sharedViewModel)
        }
    }
}

@Composable
fun TabbedView(navController: NavHostController, sharedViewModel: SharedViewModel) {
    val tabs = listOf("For You", "Top Tracks")
    var selectedTabIndex by remember {
        mutableStateOf(0)
    }
    Column(
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.Bottom
    ){
        Box(
            modifier = Modifier
                .weight(1f)
        ) {
            when (selectedTabIndex) {
                0 -> {
                    sharedViewModel.list(0)
                    SongScreen(navController,sharedViewModel)
                }
                1 -> {
                    sharedViewModel.list(1)
                    TopTracks(navController,sharedViewModel)
                }
            }
        }
        if (sharedViewModel.isPlaybarVisible){
            NowPlayingBar(sharedViewModel)
        }
        TabRow(
            containerColor= Color.Black,
            selectedTabIndex = selectedTabIndex,
            indicator = { tabPositions ->
                val indicator= Modifier
                    .padding(10.dp)
                    .tabIndicatorOffset(tabPositions[selectedTabIndex])
                    .clip(CircleShape)
                TabRowDefaults.Indicator(
                    modifier =indicator,
                    color = Color.White
                )
            },
            divider ={
                Spacer(modifier = Modifier.height(0.dp))
            }
        )
        {
            tabs.forEachIndexed { index, title ->
                Tab(
                    selected = selectedTabIndex == index,
                    onClick = { selectedTabIndex = index },
                    text = { Text(text = title, color = Color.White) },
                    modifier = Modifier
                        .padding(10.dp)
                )
            }
        }

    }
}




