package com.example.demopokekotlin

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    var pokemons = listOf<Pokemon>()

    init { // à la création de MainActivity
        // charger la liste des pokémons de manière asynchrone
        CoroutineScope(Dispatchers.IO).launch {
            pokemons = Pokemon.getAll()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent { Main() }
    }

    /**
     * Le composant racine de l'application Jetpack Compose.
     * Les fonctions marquées avec l'annotation @Composable peuvent être utilisées comme COMPOSants
     * d'interface déclaratifs entre lesquels du code Kotlin peut est évalué normalement.
     */
    @Composable
    fun Main() {
        // conteneur de base d'une application Material Design
        Surface(Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
            val context = LocalContext.current

            // équivalent du RecycleView
            LazyColumn(Modifier.fillMaxHeight()) {
                items(
                    count = pokemons.size,
                    key = { pokemons[it].id!! }) // clé primaire
                {
                    val currentPokemon = pokemons[it]

                    // composant personnalisé qui sert de template pour les items
                    PokemonListDelegate(
                        Modifier.fillParentMaxHeight(0.45f),
                        currentPokemon,
                        onClick = {
                            val intent = Intent(context, DetailPokemonActivity::class.java)
                            intent.putExtra("pokemonId", currentPokemon.id!!)
                            context.startActivity(intent)
                        }
                    )
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
        modifier: Modifier = Modifier,
        pokemon: Pokemon,
        onClick: () -> Unit = {}
    ) {
        val context = LocalContext.current
        val id = pokemon.id.toString().padStart(3, '0') // formatter le id pour le site

        val loader = PokemonImageLoader()
        var pokemonBitmap by remember { mutableStateOf(value = loader.emptyBitmap) }

        Box(modifier = modifier
            .background(Color(242, 242, 242))
            .clickable {
                onClick()
            }
        ) {
            Image(
                contentScale = ContentScale.FillBounds,
                bitmap = pokemonBitmap, contentDescription = "Image du Pokémon",
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
            )
            BadgeTypePokemon(pokemon.type!!, modifier = Modifier.align(Alignment.TopEnd))
            Text(
                text = "#${pokemon.id}", fontSize = 6.em,
                modifier = Modifier.align(Alignment.TopStart)
            )
            Text(
                text = pokemon.name!!,
                fontSize = 10.em,
                textAlign = TextAlign.Center,
                color = Color.White,
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .background(color = Color.Black.copy(alpha = 0.6f))
                    .fillMaxWidth(1f)
            )
        }
        Spacer(modifier = Modifier.size(1.dp))

        LaunchedEffect("setBitmap") {
            CoroutineScope(Dispatchers.IO).launch {
                pokemonBitmap = loader.get(id)

                if (pokemonBitmap == loader.emptyBitmap) {
                    CoroutineScope(Dispatchers.Main).launch {
                        val toast = Toast.makeText(
                            context,
                            "Un ou des pokémons n'ont pas pu être chargés.",
                            Toast.LENGTH_LONG
                        )
                        toast.show()
                    }
                }
            }
        }
    }

    @Composable
    fun BadgeTypePokemon(type: String, modifier: Modifier = Modifier) {
        val backgroundColor: Color
        val textColor: Color

        when (type) {
            "Grass" -> {
                backgroundColor = Color(155, 204, 80)
                textColor = Color.Black
            }
            "Fire" -> {
                backgroundColor = Color(253, 125, 36)
                textColor = Color.White
            }
            "Water" -> {
                backgroundColor = Color(69, 465, 196)
                textColor = Color.White
            }
            "Normal" -> {
                backgroundColor = Color(164, 140, 33)
                textColor = Color.Black
            }
            "Poison" -> {
                backgroundColor = Color(185, 127, 201)
                textColor = Color.White
            }
            "Rock" -> {
                backgroundColor = Color(163, 140, 33)
                textColor = Color.White
            }
            "Psychic" -> {
                backgroundColor = Color(243, 102, 185)
                textColor = Color.White
            }
            "Ice" -> {
                backgroundColor = Color(81, 196, 231)
                textColor = Color.Black
            }
            "Dragon" -> {
                backgroundColor = Color(83, 164, 207)
                textColor = Color.White
            }
            "Ground" -> {
                backgroundColor = Color(83, 164, 207)
                textColor = Color.White
            }
            "Bug" -> {
                backgroundColor = Color(114, 159, 63)
                textColor = Color.White
            }
            "Fighting" -> {
                backgroundColor = Color(213, 103, 35)
                textColor = Color.White
            }
            "Fairy" -> {
                backgroundColor = Color(253, 185, 233)
                textColor = Color.Black
            }
            "Electric" -> {
                backgroundColor = Color(238, 213, 53)
                textColor = Color.White
            }
            "Ghost" -> {
                backgroundColor = Color(213, 98, 163)
                textColor = Color.White
            }
            else -> {
                backgroundColor = Color.Black
                textColor = Color.White
            }
        }

        Text(
            text = type, fontSize = 6.em, color = textColor,
            modifier = modifier
                .background(color = backgroundColor)
                .padding(3.dp)
        )
    }

    @Preview(showBackground = true)
    @Composable
    fun DefaultPreview() {
        Main()
    }
}


