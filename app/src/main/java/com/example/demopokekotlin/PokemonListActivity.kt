package com.example.demopokekotlin

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import coil.request.CachePolicy
import coil.request.ImageRequest
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


    /**
     * Le composant racine de l'application Jetpack Compose.
     * Les fonctions marquées avec l'annotation @Composable peuvent être utilisées comme COMPOSants
     * d'interface déclaratifs entre lesquels du code Kotlin peut est évalué normalement.
     */
    @Composable
    fun PokemonListPage(navController: NavController = rememberNavController(), data : MutableList<Pokemon>) {

        var pokemons : State<MutableList<Pokemon>> = remember { mutableStateOf(data)}

        // conteneur de base d'une application Material Design
        Surface(Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
            val context = LocalContext.current

            Text(
                text = "Pokemons"
            )

            // équivalent du RecycleView
            LazyColumn(Modifier.fillMaxHeight()) {
                items(count = pokemons.value.size, key = { pokemons.value[it].id!! }) // clé primaire
                {
                    val currentPokemon = pokemons.value[it]

                    // composant personnalisé qui sert de template pour les items
                    PokemonListDelegate(
                        Modifier.fillParentMaxHeight(0.45f),
                        currentPokemon,
                        onClick = {
                            val intent = Intent(context, PokemonDetailsActivity::class.java)
                            intent.putExtra("pokemonId", currentPokemon.id!!)
                            context.startActivity(intent)
                        })
                }
            }
        }
    }

    /**
     * Le delegate pour la liste des pokémons.     *
     * Un delegate est un composant qui sert de template pour l'affichage des données d'un modèle.
     * C'est comme une partial view en MVC, on réutilise le même bout d'interface pour tous les
     * éléments de la liste des pokemons en remplaçant seulement les données.
     *
     * @param modifier: Un modifier qui spécifie des attributs spéciaux de l'élément.
     * @param pokemon: Le modèle de pokémon dont les données sont affichées.
     * @param onClick: Une fonction lambda (anonyme) qui est appelée lorsque l'item est cliqué.
     */
    @Composable
    fun PokemonListDelegate(
        modifier: Modifier = Modifier, pokemon: Pokemon, onClick: () -> Unit = {}
    ) {
        val id = pokemon.id.toString().padStart(3, '0') // formatter le id pour le site
        val imageRequest = ImageRequest.Builder(LocalContext.current.applicationContext)
            .data("https://assets.pokemon.com/assets/cms2/img/pokedex/full/${id}.png")
            .crossfade(true).diskCacheKey(pokemon.id).diskCachePolicy(CachePolicy.ENABLED)
            .setHeader("Cache-Control", "max-age=604800") // garder en cache 7 jours
            .build()

        Box(modifier = modifier
            .background(Color(242, 242, 242))
            .clickable { onClick() }) {
            AsyncImage(
                model = imageRequest,
                contentDescription = "Image du Pokémon $pokemon.name", // requis pour accessibilité
                contentScale = ContentScale.FillBounds,
                modifier = Modifier.fillMaxSize()
            )
            PokemonTypeBadge(pokemon.type!!)
            Text(
                text = "#${pokemon.id}",
                fontSize = 6.em,
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(8.dp)
            )
            Text(
                text = pokemon.name!!.titleCase(),
                fontSize = 8.em,
                textAlign = TextAlign.Center,
                color = Color.White,
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .background(color = Color.Black.copy(alpha = 0.6f))
                    .fillMaxWidth(1f)
                    .padding(3.dp)
            )
        }
        Spacer(modifier = Modifier.size(1.dp))
    }

    @Composable
    fun PokemonTypeBadge(type: String) {
        val colors = getPokemonTypeColors(type)
        val backgroundColor = colors.first
        val textColor = colors.second

        Box(
            Modifier
                .padding(8.dp)
                .clip(RoundedCornerShape(8.dp))
                .shadow(2.dp)
        ) {
            Text(
                text = type.titleCase(),
                fontSize = 5.em,
                color = textColor,
                modifier = Modifier
                    .background(backgroundColor)
                    .padding(5.dp)
            )
        }
    }

    /**
     * Obtient la couleur correspondant au type de pokémon spécifié. Non-fourni par l'API.
     * Le when statement de Kotlin est un if très puissant.
     *
     * @param type: Le type de pokémon.
     */
    private fun getPokemonTypeColors(type: String): Pair<Color, Color> = when (type) {
        "grass" -> Pair(
            Color(155, 204, 80), Color.Black
        )
        "fire" -> Pair(
            Color(253, 125, 36), Color.White
        )
        "water" -> Pair(
            Color(69, 465, 196), Color.White
        )
        "normal" -> Pair(
            Color(164, 140, 33), Color.Black
        )
        "poison" -> Pair(
            Color(185, 127, 201), Color.White
        )
        "rock" -> Pair(
            Color(163, 140, 33), Color.White
        )
        "psychic" -> Pair(
            Color(243, 102, 185), Color.White
        )
        "ice" -> Pair(
            Color(81, 196, 231), Color.Black
        )
        "dragon" -> Pair(
            Color(83, 164, 207), Color.White
        )
        "ground" -> Pair(
            Color(83, 164, 207), Color.White
        )
        "bug" -> Pair(
            Color(114, 159, 63), Color.White
        )
        "fighting" -> Pair(
            Color(213, 103, 35), Color.White
        )
        "fairy" -> Pair(
            Color(253, 185, 233), Color.Black
        )
        "electric" -> Pair(
            Color(238, 213, 53), Color.White
        )
        "ghost" -> Pair(
            Color(213, 98, 163), Color.White
        )
        else -> Pair(
            Color.Black, Color.White
        )
    }

    // fonction d'extension de la classe String
    private fun String.titleCase(): String
        = this.first().uppercaseChar() + this.drop(1)


