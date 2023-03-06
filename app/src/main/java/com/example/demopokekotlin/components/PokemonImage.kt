package com.example.demopokekotlin.components

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import coil.compose.AsyncImage
import coil.request.CachePolicy
import coil.request.ImageRequest

@Composable
fun PokemonImage(idPokemon : Int) {

    val id = idPokemon.toString().padStart(3, '0') // formatter le id pour le site
    val imageRequest = ImageRequest.Builder(LocalContext.current.applicationContext)
        .data("https://assets.pokemon.com/assets/cms2/img/pokedex/full/${id}.png")
        .crossfade(true).diskCacheKey(idPokemon.toString()).diskCachePolicy(CachePolicy.ENABLED)
        .setHeader("Cache-Control", "max-age=604800") // garder en cache 7 jours
        .build()

    AsyncImage(
        model = imageRequest,
        contentDescription = "Image d'un pokémon", // requis pour accessibilité
        contentScale = ContentScale.FillBounds,
        modifier = Modifier.fillMaxSize()
    )

}