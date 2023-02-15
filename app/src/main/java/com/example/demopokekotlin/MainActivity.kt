package com.example.demopokekotlin

import android.content.Context
import android.media.MediaPlayer
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MainActivityPage()
        }
    }
}

@Composable
fun MainActivityPage() {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = "welcome"
    ) {
        composable(route = "welcome") {
            WelcomeActivityPage(navController)
        }

        composable(route = "pokemon/list") {
            var data = Pokemon.getAll(LocalContext.current)
            PokemonListPage(navController, data)
        }

    }
}


/**
 * Class MediaManager created on 11/07/16 - 4:01 PM.
 * All copyrights reserved to the Zoomvy.
 * Class behaviour is to initialize and play a media file on notification
 */
class MediaManager private constructor(context: Context) {
    private val mContext: Context

    init {
        mContext = context.applicationContext
    }

    companion object {
        /**
         * SingleTon instance
         */
        private var sInstance: MediaPlayer? = null
        fun getInstance(context: Context): MediaPlayer? {
            if (null == sInstance) {
                synchronized(MediaPlayer::class.java) {
                    sInstance = MediaPlayer.create(context, R.raw.pokemon_theme_song)
                }
            }
            return sInstance
        }
    }
}