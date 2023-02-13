package com.example.demopokekotlin

import android.util.Log
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONArray
import org.json.JSONObject

class GraphQLApi(private val endpoint: String) {
    fun queryJson(query: String): String? {
        val http = OkHttpClient()

        val contentType = "application/json; charset=utf-8".toMediaType()
        val json = JSONObject()

        try {
            json.put("query", query)
            json.put("variables", JSONObject())
            json.put("operationName", "pokemons_list")
        } catch (e: Exception) {
            Log.w("POKEMONS", "Invalid query: ${e.message}.")
        }

        val body = json.toString().toRequestBody(contentType)
        val request = Request.Builder()
            .url(endpoint)
            .post(body)
            .build()

        val response = http.newCall(request).execute()

        if (response.body == null) {
            Log.w("POKEMONS", "Failed to load pokemons: code ${response.code}.")
            return null
        }

        return response.body!!.string()
    }
}

// TODO: Implement factory pattern
class Pokemon {
    var id: String? = null
    var name: String? = null
    var type: String? = null

    companion object {
        var failure = Pokemon()

        fun getAll(): List<Pokemon> {
            val pokemons = mutableListOf<Pokemon>()
            val client = GraphQLApi("https://beta.pokeapi.co/graphql/v1beta/")

            val query = "query pokemons_list {\n" +
                    "  pokemon_v2_pokemon {\n" +
                    "    id\n" +
                    "    name\n" +
                    "    pokemon_v2_pokemontypes{\n" +
                    "      pokemon_v2_type {\n" +
                    "        id\n" +
                    "        name\n" +
                    "      }\n" +
                    "    }\n" +
                    "  }\n" +
                    "}\n"

            failure.name = "Failed to load"
            failure.type = "Rip bozo"

            val response = client.queryJson(query)
            if (response == null) {
                Log.w("POKEMONS", "Failed to load pokemons: request failed.")
                return listOf()
            }

            val json = JSONObject(response)

            if (json.has("errors")) {
                Log.w("POKEMONS", "Failed to load pokemons: $response.")
                return listOf()
            }

            try {
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
            } catch(e: Exception) {
                Log.w("POKEMONS", "Failed to load pokemons: ${e.message}.")
                return listOf()
            }

            return pokemons
        }

        fun getDetailsOf() {

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
}