package com.example.demopokekotlin

import android.util.Log
import org.json.JSONArray
import org.json.JSONObject

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

            /*val reader = FileReader("pokemons.gql")
            val query = reader.readText()
            reader.close()*/

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
                parsePokemons(json, pokemons)
            } catch(e: Exception) {
                Log.w("POKEMONS", "Failed to load pokemons: ${e.message}.")
                return listOf()
            }

            return pokemons
        }

        fun getDetailsOf() {

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
}