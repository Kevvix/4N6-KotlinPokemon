package com.example.demopokekotlin

import android.content.Context
import android.media.MediaPlayer
import android.os.Bundle
import android.os.StrictMode
import android.os.StrictMode.ThreadPolicy
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.demopokekotlin.components.screens.PokemonDetailScreen
import com.example.demopokekotlin.components.screens.PokemonListScreen
import com.example.demopokekotlin.components.screens.WelcomeActivityScreen
import com.example.demopokekotlin.repositories.PokemonRepository


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val policy = ThreadPolicy.Builder().permitAll().build() //TODO : Temporaire, faire autrement!
        StrictMode.setThreadPolicy(policy) //TODO : Temporaire, faire autrement!

        setContent {
            MainActivityPage()
        }
    }
}

@Composable
fun MainActivityPage() {
    val navController = rememberNavController()
    var pokemonRepository = PokemonRepository()
    NavHost(
        navController = navController,
        startDestination = "welcome"
    ) {
        composable(route = "welcome") {
            WelcomeActivityScreen(navController)
        }

        composable(route = "pokemon/list") {
            var data = pokemonRepository.getAll(LocalContext.current)
            PokemonListScreen(navController, data)
        }

        composable(route = "pokemon/detail/{id}",
                   arguments = listOf(navArgument("id") { type = NavType.IntType }))
        { backStackEntry ->
            var id = backStackEntry.arguments?.getInt("id")!!;
            var data = pokemonRepository.getAll(LocalContext.current).get(id - 1) //TODO : BAD! Faire un service pour le détail uniquement!
            PokemonDetailScreen(navController, data)
        }

    }
}