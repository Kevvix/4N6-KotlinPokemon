package com.example.demopokekotlin.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

/**
 * Obtient la couleur correspondant au type de pokémon spécifié. Non-fourni par l'API.
 * Le when statement de Kotlin est un if très puissant.
 *
 * @param type: Le type de pokémon.
 */
@Composable
public fun getPokemonTypeColors(type: String): Pair<Color, Color> = when (type) {
    "grass" -> Pair(
        Color(155, 204, 80), Color.Black
    )
    "fire" -> Pair(
        Color(253, 125, 36), Color.White
    )
    "water" -> Pair(
        Color(69, 465, 196), Color.White
    )
    "normal" -> Pair(
        Color(164, 140, 33), Color.Black
    )
    "poison" -> Pair(
        Color(185, 127, 201), Color.White
    )
    "rock" -> Pair(
        Color(163, 140, 33), Color.White
    )
    "psychic" -> Pair(
        Color(243, 102, 185), Color.White
    )
    "ice" -> Pair(
        Color(81, 196, 231), Color.Black
    )
    "dragon" -> Pair(
        Color(83, 164, 207), Color.White
    )
    "ground" -> Pair(
        Color(83, 164, 207), Color.White
    )
    "bug" -> Pair(
        Color(114, 159, 63), Color.White
    )
    "fighting" -> Pair(
        Color(213, 103, 35), Color.White
    )
    "fairy" -> Pair(
        Color(253, 185, 233), Color.Black
    )
    "electric" -> Pair(
        Color(238, 213, 53), Color.White
    )
    "ghost" -> Pair(
        Color(213, 98, 163), Color.White
    )
    "steel" -> Pair(
        Color(153, 183, 184), Color.Black
    )
    "flying" -> Pair(
        Color(189, 185, 184), Color.Black
    )
    "dark" -> Pair(
        Color(112, 112, 112), Color.Black
    )
    else -> Pair(
        Color.Black, Color.White
    )
}