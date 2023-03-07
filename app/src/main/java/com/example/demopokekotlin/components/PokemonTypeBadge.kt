package com.example.demopokekotlin.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import com.example.demopokekotlin.utilities.titleCase

@Composable
fun PokemonTypeBadge(type: String) {
    val colors = getPokemonTypeColors(type)
    val backgroundColor = colors.first
    val textColor = colors.second

    Box(
        Modifier
            .padding(8.dp)
            .clip(RoundedCornerShape(8.dp))
            .shadow(2.dp)
            .border(BorderStroke(3.dp, Color.Black))

    ) {
        Text(
            text = type.titleCase(),
            fontSize = 5.em,
            color = textColor,
            modifier = Modifier
                .background(backgroundColor)
                .padding(5.dp)
        )
    }
}

@Preview()
@Composable
fun PokemonTypeBadgePreviewGrass() {
    PokemonTypeBadge(type = "grass")
}

@Preview()
@Composable
fun PokemonTypeBadgePreviewFire() {
    PokemonTypeBadge(type = "fire")
}