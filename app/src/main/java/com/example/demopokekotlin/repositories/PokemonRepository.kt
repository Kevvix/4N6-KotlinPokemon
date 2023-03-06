package com.example.demopokekotlin.repositories

import android.content.Context
import android.util.Log
import com.example.demopokekotlin.viewmodels.Pokemon
import org.json.JSONArray
import org.json.JSONObject
import java.io.File

class PokemonRepository {
    var failure = Pokemon()
    private val client = GraphQLApi("https://beta.pokeapi.co/graphql/v1beta/")

    fun getAll(context: Context): MutableList<Pokemon> {
        val pokemons = mutableListOf<Pokemon>()
        val response: String?
        val cache = File(context.cacheDir, "pokemons_list.cache.json")

        if (cache.exists()) {
            response = cache.readText()
            Log.i("POKEMONS", "Using cached response for getAll()")
        } else {
            val query = """
                query pokemons_list {
                    pokemon_v2_pokemon {
                        id
                        name
                        pokemon_v2_pokemontypes {
                            pokemon_v2_type {
                                id
                                name
                            }
                        }
                    }
                }
            """.trimIndent()
            response = client.queryJson(query)
        }

        failure.name = "Failed to load"
        failure.type = "Rip bozo"

        if (response == null) {
            Log.w("POKEMONS", "Failed to load pokemons: request failed.")
            return mutableListOf<Pokemon>()
        }

        val json = JSONObject(response)

        if (json.has("errors")) {
            Log.w("POKEMONS", "Failed to load pokemons: $response.")
            return mutableListOf<Pokemon>()
        }

        try {
            parsePokemons(json, pokemons)
        } catch(e: Exception) {
            Log.w("POKEMONS", "Failed to load pokemons: ${e.message}.")
            return mutableListOf<Pokemon>()
        }

        if (!cache.exists())
            cache.writeText(response)

        return pokemons
    }

    fun getById(id: String): Pokemon {
        val dummy = Pokemon()

        dummy.id = "448"
        dummy.name = "Kevvix"
        dummy.type = "Desjardins"

        return dummy
    }

    private fun parsePokemons(json: JSONObject, pokemons: MutableList<Pokemon>) {
        val dataObject = json.getJSONObject("data")
        val pokemonsArray = dataObject.getJSONArray("pokemon_v2_pokemon")

        for (i in 0 until pokemonsArray.length()) {
            try {
                parsePokemon(pokemonsArray, i, pokemons)
            } catch(e: Exception) {
                Log.w("POKEMONS", "Failed to load pokemon $i: ${e.message}.")
                //pokemons.add(failure)
            }
        }
    }

    private fun parsePokemon(
        pokemonsArray: JSONArray,
        i: Int,
        pokemons: MutableList<Pokemon>
    ) {
        val pokemonObject = pokemonsArray.getJSONObject(i)
        val pokemon = Pokemon()

        pokemon.id = pokemonObject.getString("id")
        pokemon.name = pokemonObject.getString("name")

        val pokemonTypesArray = pokemonObject.getJSONArray("pokemon_v2_pokemontypes")

        for (j in 0 until pokemonTypesArray.length()) {
            val pokemonTypeObject =
                pokemonTypesArray.getJSONObject(j).getJSONObject("pokemon_v2_type")
            pokemon.type = pokemonTypeObject.getString("name")
        }

        pokemons.add(pokemon)
    }
}