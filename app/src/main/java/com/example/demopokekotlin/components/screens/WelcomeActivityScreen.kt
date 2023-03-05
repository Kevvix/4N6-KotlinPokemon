package com.example.demopokekotlin.components.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.demopokekotlin.R
import com.example.demopokekotlin.utilities.MediaManager
import com.example.demopokekotlin.utilities.theme.DemoPokeKotlinTheme


@Preview(showBackground = true)
@Composable
fun WelcomeActivityScreen(navController: NavController = rememberNavController()) {

    var context = LocalContext.current

    DemoPokeKotlinTheme {
        Surface(modifier = Modifier.fillMaxSize(), color = Color(246,222,130)) {


            var spaceBetweenElement = 10.dp

            Box(modifier = Modifier
                .fillMaxHeight()
                .fillMaxWidth()){

                Icon(imageVector =  ImageVector.vectorResource(id = R.drawable.icons_music),
                    contentDescription = "icone",
                    modifier = Modifier
                        .size(40.dp)
                        .align(Alignment.TopEnd)
                        .padding(5.dp)
                        .clickable {
                           //TODO : Recommencer une deuxième fois ne marche pas :(
                            if (MediaManager.getInstance(context)?.isPlaying!!) {
                                MediaManager.getInstance(context)?.stop()
                            } else {
                                MediaManager.getInstance(context)?.start()
                            }
                        })

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
                        navController.navigate("pokemon/list")
                    }) {
                        Text(text = "Explorer")
                    }
                }
            }
        }
    }
}