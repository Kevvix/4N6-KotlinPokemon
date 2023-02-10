package com.example.demopokekotlin

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import com.example.demopokekotlin.ui.theme.DemoPokeKotlinTheme

class DetailPokemonActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Surface(
                modifier = Modifier.fillMaxSize(),
                color = MaterialTheme.colorScheme.background
            ) {
                val context = LocalContext.current
                val noPokemon = intent.getIntExtra("numeroPokemon", 1)
                val pokemonInfo = AllPokemons.first { it.pokemonId == noPokemon}
                DetailPokemonPage(pokemonInfo)
            }
        }
    }
}

@Composable
fun DetailPokemonPage(pokemon : PokemonInfo) {
    Row() {
        AfficherPokemon(pokemonInfo = pokemon, modifier = Modifier.fillMaxSize(0.5f))
        Column() {
            Text("BLABLABLA")
            Text("BLABLABLA")
            Text("BLABLABLA")
            Text("BLABLABLA")
            Text("BLABLABLA")
            Text("BLABLABLA")
        }
    }

}

@Preview(showBackground = true)
@Composable
fun Preview() {
    DemoPokeKotlinTheme {
        val pokemonInfo = AllPokemons.first()
        DetailPokemonPage(pokemonInfo)
    }
}