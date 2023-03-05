package com.example.demopokekotlin.components.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.demopokekotlin.components.PokemonListDelegate
import com.example.demopokekotlin.dto.Pokemon

/**
 * Le composant racine de l'application Jetpack Compose.
 * Les fonctions marquées avec l'annotation @Composable peuvent être utilisées comme COMPOSants
 * d'interface déclaratifs entre lesquels du code Kotlin peut est évalué normalement.
 */
@Composable
fun PokemonListScreen(navController: NavController = rememberNavController(), data : MutableList<Pokemon>) {

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
                        navController.navigate("pokemon/detail/" + currentPokemon.id!!)
                    })
            }
        }
    }
}