package com.example.demopokekotlin.components.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.demopokekotlin.components.PokemonListDelegate
import com.example.demopokekotlin.viewmodels.Pokemon
import kotlinx.coroutines.launch

/**
 * Le composant racine de l'application Jetpack Compose.
 * Les fonctions marquées avec l'annotation @Composable peuvent être utilisées comme COMPOSants
 * d'interface déclaratifs entre lesquels du code Kotlin peut est évalué normalement.
 */
@Composable
fun PokemonListScreen(navController: NavController = rememberNavController(), data : MutableList<Pokemon>) {

    var pokemons : State<MutableList<Pokemon>> = remember { mutableStateOf(data)}
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    // conteneur de base d'une application Material Design
    Surface(Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {

        ModalNavigationDrawer(
            drawerState = drawerState,
            drawerContent = {
                Column {
                    Text("Text in Drawer")

                }
            },
            content = {
                Column {
                    Text(
                        text = "Liste des pokémons",
                        fontSize = 10.em,
                        modifier = Modifier.padding(10.dp).clickable {
                            scope.launch {
                                if(drawerState.isClosed) {
                                    drawerState.open();
                                } else {
                                    drawerState.close()
                                }
                            }
                        }
                    )

                    // équivalent du RecycleView
                    LazyVerticalGrid(
                        columns = GridCells.Adaptive(150.dp),
                        contentPadding  = PaddingValues(12.dp),
                        verticalArrangement = Arrangement.spacedBy(10.dp),
                        horizontalArrangement = Arrangement.spacedBy(10.dp),
                        modifier = Modifier.fillMaxHeight()) {
                        items(count = pokemons.value.size, key = { pokemons.value[it].id!! }) // clé primaire
                        {
                            val currentPokemon = pokemons.value[it]

                            // composant personnalisé qui sert de template pour les items
                            PokemonListDelegate(
                                Modifier,
                                currentPokemon,
                                onClick = {
                                    navController.navigate("pokemon/detail/" + currentPokemon.id!!)
                                })
                        }
                    }
                }
            }
        )

    }
}