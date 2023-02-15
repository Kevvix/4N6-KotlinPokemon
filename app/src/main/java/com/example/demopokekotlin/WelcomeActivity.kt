package com.example.demopokekotlin

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import com.example.demopokekotlin.ui.theme.DemoPokeKotlinTheme

class WelcomeActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DemoPokeKotlinTheme {
                WelcomeActivityPage()
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun WelcomeActivityPage() {
    DemoPokeKotlinTheme {
        Surface(modifier = Modifier.fillMaxSize(), color = Color(246,222,130)) {

            var spaceBetweenElement = 10.dp

            IconButton(onClick = {
                /* TODO : Démarrer la musique nostalgique de pokémon en background */
            }) {
                Icon(Icons.Outlined.Lock, contentDescription = "Localized description")
                Text("Input Chip")
            }

            Column(horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center){
                Text("Bienvenue dans le monde", fontSize = 6.em)

                Spacer(modifier = Modifier.size(spaceBetweenElement))
                Text("de JectPack Compose")

                Spacer(modifier = Modifier.size(spaceBetweenElement))

                /* TODO : Animé que ça passe de professeur Chen, à Pikachu, à un autre mignon à autre chose */
                Image(
                        painter = painterResource(id = R.drawable.professeur_chen),
                        contentDescription = "Image du professeur sympathique",
                        modifier = Modifier.size(300.dp)
                )

                Spacer(modifier = Modifier.size(spaceBetweenElement))
                Row {
                    Button(onClick = { /*TODO*/

                    }) {
                        Text(text = "Explorer")
                    }
                }
            }
        }
    }
}