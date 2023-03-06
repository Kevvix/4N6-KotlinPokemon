package com.example.demopokekotlin.components.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.em
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.demopokekotlin.viewmodels.Pokemon


@Composable
fun PokemonDetailScreen(navController: NavController = rememberNavController(), data : Pokemon) {
    Row {
        Column {
            Text(text = data.name!!, fontSize = 24.em)
            Text(text = data.type!!, fontSize = 16.em)
        }
    }
}
