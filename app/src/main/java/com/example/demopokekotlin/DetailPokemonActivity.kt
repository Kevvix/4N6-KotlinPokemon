package com.example.demopokekotlin

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier

class DetailPokemonActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Surface(
                modifier = Modifier.fillMaxSize(),
                color = MaterialTheme.colorScheme.background
            ) {
                val noPokemon = intent.getIntExtra("numeroPokemon", 1)
                //val pokemonInfo = AllPokemons.first { it.pokemonId == noPokemon}

                //DetailPokemonPage(pokemonInfo)
            }
        }
    }
}

/*
@Composable
fun DetailPokemonPage(pokemon : Pokemon) {
    Row {
        AfficherPokemon(pokemon = pokemon, modifier = Modifier.fillMaxSize(0.5f))
        Column {
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
}*/
