package com.example.demopokekotlin

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.media.Image
import android.os.Bundle
import android.os.Looper
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.autoSaver
import androidx.compose.runtime.saveable.listSaver
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.*

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Page()
        }
    }
}

@Composable
fun Page() {
    //Composant racine d'une activité utilisant Matérial Design
    Surface(modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background) {

        //Contexte courant de l'application
        val context = LocalContext.current

        //Récupérer la liste des pokémons (et ma variable globale lol)
        val allPokemonsInfo = AllPokemons

        //Faire l'équivalent d'un recyclerView
        LazyColumn(modifier = Modifier.fillMaxHeight()) {
            items(
                count = allPokemonsInfo.size /* Le nombre d'éléments de la liste */ ,
                key = { allPokemonsInfo[it].pokemonId} /* fonction permettant de déterminer l'unicité d'un élément  */)
            {
                //Fonction servant à déterminer comment afficher un item dans le LayColums
                var pokemonInfoCourant = allPokemonsInfo[it]
                AfficherPokemon(pokemonInfoCourant, modifier = Modifier.fillParentMaxHeight(0.45f), onClick = {

                    var intent = Intent(context, DetailPokemonActivity::class.java)
                    intent.putExtra("numeroPokemon", pokemonInfoCourant.pokemonId)
                    context.startActivity(intent)

                })
            }
        }
    }
}



@Composable
fun AfficherPokemon(pokemonInfo: PokemonInfo, onClick : () -> Unit = {}, modifier : Modifier = Modifier) {
    val context =  LocalContext.current
    val id = pokemonInfo.pokemonId.toString().padStart(3, '0')
    val loader = PokemonImageLoader()
    var pokemonBitmap by remember { mutableStateOf(value = loader.emptyBitmap) }

    Box(modifier = modifier
        .background(Color(242, 242, 242))
        .clickable {
            onClick()
        }
    ) {
        Image(contentScale = ContentScale.FillBounds,
            bitmap = pokemonBitmap, contentDescription = "Image du Pokémon",
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight())
        BadgeTypePokemon(pokemonInfo.type, modifier = Modifier.align(Alignment.TopEnd))
        Text(text = "#${pokemonInfo.pokemonId}", fontSize = 6.em,
            modifier = Modifier.align(Alignment.TopStart))
        Text(text = pokemonInfo.nom, fontSize = 10.em, textAlign = TextAlign.Center, color = Color.White,
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .background(color = Color.Black.copy(alpha = 0.6f))
                .fillMaxWidth(1f))
    }
    Spacer(modifier = Modifier.size(1.dp))

    LaunchedEffect("setBitmap") {
        CoroutineScope(Dispatchers.IO).launch {
            pokemonBitmap = loader.get(id)

            if (pokemonBitmap == loader.emptyBitmap) {
                CoroutineScope(Dispatchers.Main).launch {
                    val toast = Toast.makeText(context, "Un ou des pokémons n'ont pas pu être chargés.", Toast.LENGTH_LONG);
                    toast.show()
                }
            }
        }
    }
}

@Composable
fun BadgeTypePokemon(type : String, modifier : Modifier = Modifier) {
    var backgroundColor : Color;
    var textColor : Color;

    if(type == "Grass") {
        backgroundColor = Color(155,204,80)
        textColor = Color.Black;
    } else if(type == "Fire") {
        backgroundColor = Color(253,125,36)
        textColor = Color.White
    } else if(type == "Water") {
        backgroundColor = Color(69,465,196)
        textColor = Color.White
    } else if(type == "Normal") {
        backgroundColor = Color(164,140,33)
        textColor = Color.Black;
    } else if(type == "Poison") {
        backgroundColor = Color(185,127,201)
        textColor = Color.White;
    } else if(type == "Rock") {
        backgroundColor = Color(163,140,33)
        textColor = Color.White;
    } else if(type == "Psychic") {
        backgroundColor = Color(243,102,185)
        textColor = Color.White;
    } else if(type == "Ice") {
        backgroundColor = Color(81,196,231)
        textColor = Color.Black;
    } else if(type == "Dragon") {
        backgroundColor = Color(83,164,207)
        textColor = Color.White;
    } else if(type == "Ground") {
        backgroundColor = Color(83,164,207)
        textColor = Color.White;
    } else if(type == "Bug") {
        backgroundColor = Color(114,159,63)
        textColor = Color.White;
    } else if(type == "Fighting") {
        backgroundColor = Color(213,103,35)
        textColor = Color.White;
    } else if(type == "Fairy") {
        backgroundColor = Color(253,185,233)
        textColor = Color.Black;
    } else if(type == "Electric") {
        backgroundColor = Color(238,213,53)
        textColor = Color.White;
    } else if(type == "Ghost") {
        backgroundColor = Color(213,98,163)
        textColor = Color.White;
    } else {
        backgroundColor = Color.Black
        textColor = Color.White
    }

    Text(text = type, fontSize = 6.em, color = textColor,
        modifier = modifier
            .background(color = backgroundColor)
            .padding(3.dp))
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    Page()
}
