package com.example.demopokekotlin

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import java.net.URL

class PokemonImageLoader {
    val emptyBitmap = Bitmap.createBitmap(1, 1, Bitmap.Config.ALPHA_8).asImageBitmap()

    private var cache = hashMapOf<String, ImageBitmap>()
    private val rootUrl = "https://assets.pokemon.com/assets/cms2/img/pokedex/full/"

    fun get(id: String): ImageBitmap {
        if (cache.contains(id))
            return cache[id]!!

        if (load(id)) {
            Log.i("POKEMONS", "Getting pokemon $id from cache.")
            return cache[id]!!
        }

        Log.w("POKEMONS", "Pokemon $id was not in cache and could not be fetched remotely.")
        return emptyBitmap;
    }

    private fun load(id: String): Boolean {
        val url = URL("$rootUrl$id.png")
        val bitmap: Bitmap

        try {
            bitmap = BitmapFactory.decodeStream(url.openConnection().getInputStream())
        } catch (e: Exception) {
            Log.w("POKEMONS", "Failed to load Pokemon image at '$url': ${e.message}")
            return false
        }

        if (bitmap == null) {
            Log.w("POKEMONS", "Failed to load Pokemon image at '$url': bitmap is invalid.")
            return false
        }

        cache[id] = bitmap.asImageBitmap()
        return true
    }
}