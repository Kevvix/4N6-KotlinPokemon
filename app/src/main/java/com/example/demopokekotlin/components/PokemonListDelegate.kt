package com.example.demopokekotlin.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import com.example.demopokekotlin.utilities.titleCase
import com.example.demopokekotlin.viewmodels.Pokemon

/**
 * Le delegate pour la liste des pokémons.     *
 * Un delegate est un composant qui sert de template pour l'affichage des données d'un modèle.
 * C'est comme une partial view en MVC, on réutilise le même bout d'interface pour tous les
 * éléments de la liste des pokemons en remplaçant seulement les données.
 *
 * @param modifier: Un modifier qui spécifie des attributs spéciaux de l'élément.
 * @param pokemon: Le modèle de pokémon dont les données sont affichées.
 * @param onClick: Une fonction lambda (anonyme) qui est appelée lorsque l'item est cliqué.
 */
@Composable
fun PokemonListDelegate(
    modifier: Modifier = Modifier, pokemon: Pokemon, onClick: () -> Unit = {}
) {
    Box(modifier = modifier
        .background(Color(242, 242, 242))
        .fillMaxWidth(1f)
        .aspectRatio(1f)
        .clickable { onClick() }) {
        PokemonImage(pokemon.id!!)
        PokemonTypeBadge(pokemon.type!!.first())
        Text(
            text = "#${pokemon.id}",
            fontSize = 6.em,
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(8.dp)
        )
        Text(
            text = pokemon.name!!.titleCase(),
            fontSize = 6.em,
            textAlign = TextAlign.Center,
            color = Color.White,
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .background(color = Color.Black.copy(alpha = 0.6f))
                .fillMaxWidth(1f)
                .padding(1.dp)
        )
    }
    Spacer(modifier = Modifier.size(1.dp))
}