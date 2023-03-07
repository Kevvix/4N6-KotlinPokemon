package com.example.demopokekotlin.components.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.magnifier
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.demopokekotlin.components.PokemonImage
import com.example.demopokekotlin.components.PokemonTypeBadge
import com.example.demopokekotlin.components.getPokemonTypeColors
import com.example.demopokekotlin.viewmodels.Pokemon
import com.example.demopokekotlin.viewmodels.PokemonDetail
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PokemonDetailScreen(navController: NavController = rememberNavController(), data : Flow<PokemonDetail>) {

    val pokemon : PokemonDetail by data.collectAsState(PokemonDetail())
    val scrollState = rememberScrollState()
    val largeurEcran = LocalConfiguration.current.screenWidthDp.dp


    Scaffold {
        it ->
        if(pokemon.name != null) {
            Column(
                modifier = Modifier
                    .padding(it)
                    .fillMaxSize()
                    .background(getPokemonTypeColors(type = pokemon.types!![0]).first)
                    .verticalScroll(scrollState)
            ) {

                Box(modifier = Modifier.size(width = largeurEcran, height = largeurEcran)){
                    PokemonImage(pokemon.id!!)
                }

                Row(modifier = Modifier.align(alignment = Alignment.CenterHorizontally)) {
                    Text(text = pokemon.name!!, fontSize = 12.em)

                    for(type in pokemon.types!!.iterator()) {
                        PokemonTypeBadge(type = type)
                    }
                }
                Spacer(Modifier.size(12.dp))


                Text(text = "Stats", fontSize = 7.em)
                Text(text = "PV : ${pokemon.pv}", modifier = Modifier.padding(start = 20.dp))
                Text(text = "attaque : ${pokemon.attack}", modifier = Modifier.padding(start = 20.dp))
                Text(text = "defence : ${pokemon.defence}", modifier = Modifier.padding(start = 20.dp))
                Text(text = "Spéciale atq : ${pokemon.specialAttack}", modifier = Modifier.padding(start = 20.dp))
                Text(text = "Spéciale déf : ${pokemon.specialDefence}", modifier = Modifier.padding(start = 20.dp))
                Text(text = "Vitesse : ${pokemon.speed}", modifier = Modifier.padding(start = 20.dp))

                Spacer(Modifier.size(12.dp))

                Text(text = "Abilities", fontSize = 7.em)
                for(ability in pokemon.abilities!!.iterator()) {
                    Text(text = ability, modifier = Modifier.padding(start = 20.dp))
                }
                Spacer(Modifier.size(12.dp))

                Text(text = "Moves", fontSize = 7.em)
                for(move in pokemon.moves!!.iterator()) {
                    Text(text = move, modifier = Modifier.padding(start = 20.dp))
                }

            }
        }
    }
}

@Preview("Detail ")
@Composable
fun preview1() {
    val data : Flow<PokemonDetail> = flow {
        val pokemon = PokemonDetail()
        pokemon.id = 1
        pokemon.name = "Bulbasaure"
        pokemon.types = listOf("grass")
        pokemon.moves = listOf("TEST, TEST")
        pokemon.abilities = listOf("TEST")
        pokemon.height = 0.0
        pokemon.weight = 0.0
        pokemon.isLegendary = true
        pokemon.isMythical = true
        emit(pokemon)
    }
    PokemonDetailScreen(data = data)
}