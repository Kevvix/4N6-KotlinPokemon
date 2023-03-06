package com.example.demopokekotlin.components.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
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

                Text(text = pokemon.name!!, fontSize = 22.em)

                Text(text = "Types")
                for(type in pokemon.types!!.iterator()) {
                    PokemonTypeBadge(type = type)
                }

                Text(text = "Moves", fontSize = 10.em)
                for(move in pokemon.moves!!.iterator()) {
                    Text(text = move, modifier = Modifier.padding(start = 2.dp))
                }

                Text(text = "Abilities", fontSize = 12.em)
                for(ability in pokemon.abilities!!.iterator()) {
                    Text(text = ability, modifier = Modifier.padding(start = 2.dp))
                }
            }
        }
    }


}
