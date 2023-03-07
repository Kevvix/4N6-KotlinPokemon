package com.example.demopokekotlin.utilities

import android.content.Context
import android.media.MediaPlayer
import com.example.demopokekotlin.R

class MediaManager {

    companion object {
        /**
         * SingleTon instance
         */
        private var sInstance: MediaPlayer? = null

        fun tooglePlayMusic(context: Context) {
            if(sInstance != null && sInstance?.isPlaying!!) {
                sInstance?.stop()
            } else {
                sInstance = MediaPlayer.create(context, R.raw.pokemon_theme_song)
                sInstance?.start()
            }
        }
    }
}