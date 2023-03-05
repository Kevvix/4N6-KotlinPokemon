package com.example.demopokekotlin.utilities

import android.content.Context
import android.media.MediaPlayer
import com.example.demopokekotlin.R

/**
 * Class MediaManager created on 11/07/16 - 4:01 PM.
 * All copyrights reserved to the Zoomvy.
 * Class behaviour is to initialize and play a media file on notification
 */
public class MediaManager private constructor(context: Context) {
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