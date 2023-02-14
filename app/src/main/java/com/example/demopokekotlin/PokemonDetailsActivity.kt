package com.example.demopokekotlin

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.datasource.LoremIpsum
import androidx.compose.ui.unit.em

class PokemonDetailsActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Surface(
                modifier = Modifier.fillMaxSize(),
                color = MaterialTheme.colorScheme.background
            ) {
                val pokemonId = intent.getIntExtra("numeroPokemon", 1)
                val pokemon = Pokemon.getById(pokemonId.toString())

                PokemonDetails(pokemon)
            }
        }
    }
}

@Composable
fun PokemonDetails(pokemon : Pokemon) {
    Row {
        Column {
            Text(text = pokemon.name!!, fontSize = 24.em)
            Text(text = pokemon.type!!, fontSize = 16.em)

            LoremIpsum()
        }
    }
}
