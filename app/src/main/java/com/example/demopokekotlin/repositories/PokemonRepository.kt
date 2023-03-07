package com.example.demopokekotlin.repositories

import android.content.Context
import android.util.Log
import com.example.demopokekotlin.viewmodels.Pokemon
import com.example.demopokekotlin.viewmodels.PokemonDetail
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import org.json.JSONArray
import org.json.JSONObject
import java.io.File
class PokemonRepository {

    fun getAllPokemons(context: Context) : Flow<List<Pokemon>> = flow {
        val noDataYet = emptyList<Pokemon>()
        emit(noDataYet) //Émettre qu'on n'a pas encore de données

        context.run {
            //Il peut y avoir appel bloquant ici

            val data = getAllPokemonsWithGraphQL(context) //Ici le call est bloquant
            //delay(5000) //Fack une attente de 5 secondes
            emit(data) //Émettre les nouveaux résultats
        }
    }

    fun getPokemonById(context: Context, id: Int) : Flow<PokemonDetail> = flow {
        val noDataYet = PokemonDetail()
        emit(noDataYet)

        context.run {
            val data = getPokemonByIdWithGraphQL(context, id) //Ici le call est bloquant
            emit(data) //Émettre les nouveaux résultats
        }
    }


    private fun getPokemonByIdWithGraphQL(context: Context, id: Int): PokemonDetail {
        val response: String?
        val cache = File(context.cacheDir, "pokemons_detail${id}.cache.json")

        if (cache.exists()) {
            response = cache.readText()
            Log.i("POKEMONS", "Using cached response for gelDetail(${id})")
        } else {
            val query = """
                    query pokemon_detail($${"id"}: Int = ${id}){
                      result : pokemon_v2_pokemon(where: {id: {_eq: $${"id"}}}) {
                        
                        id
                        name
                        height
                        weight
                        
                        abilities: pokemon_v2_pokemonabilities{
                          ability: pokemon_v2_ability {
                            name
                          }
                        }
                        
                        specy: pokemon_v2_pokemonspecy{
                          is_legendary
                          is_mythical
                        }
                        
                        types: pokemon_v2_pokemontypes{
                          type: pokemon_v2_type {
                            name
                          }
                        }
                        
                        moves: pokemon_v2_pokemonmoves{
                          move: pokemon_v2_move {
                            name
                          }
                        }
                        
                        stats: pokemon_v2_pokemonstats {
                          value: base_stat
                          statInfo: pokemon_v2_stat {
                            statName : pokemon_v2_statnames(where: {language_id: {_eq: 5}}) {
                              name
                            }
                          }
                        }
                        
                      }
                    }
            """.trimIndent()
            var client = GraphQLApi("https://beta.pokeapi.co/graphql/v1beta/")
            response = client.queryJson(query)
        }

        if (response == null) {
            Log.w("POKEMONS", "Failed to load detail pokemon ${id}: request failed.")
        }

        val json = JSONObject(response)

        if (json.has("errors")) {
            Log.w("POKEMONS", "Failed to load detail pokemon ${id}: $response.")
        }

        try {
            val dataFromServerParsed = parsePokemonsDetailJSON(json)
            if (!cache.exists())  cache.writeText(response!!)
            return dataFromServerParsed
        } catch(e: Exception) {
            Log.w("POKEMONS", "Failed to load detail pokemon ${id}: ${e.message}.")
        }

        return PokemonDetail()
    }


    private fun parsePokemonsDetailJSON(json: JSONObject) : PokemonDetail {
        val pokemon = PokemonDetail()
        val pokemonJSONInfo = (json.getJSONObject("data").getJSONArray("result")[0] as JSONObject)
        pokemon.id = pokemonJSONInfo.getInt("id")
        pokemon.name = pokemonJSONInfo.getString("name")
        pokemon.height = pokemonJSONInfo.getDouble("height")
        pokemon.weight = pokemonJSONInfo.getDouble("weight")
        pokemon.isMythical = pokemonJSONInfo.getJSONObject("specy").getBoolean("is_mythical")
        pokemon.isLegendary = pokemonJSONInfo.getJSONObject("specy").getBoolean("is_legendary")


        val types = mutableListOf<String>()
        for (i in 0 until pokemonJSONInfo.getJSONArray("types").length()){
            types.add((pokemonJSONInfo.getJSONArray("types")[i] as JSONObject).getJSONObject("type").getString("name"))
        }
        pokemon.types = types

        val moves = mutableListOf<String>()
        for (i in 0 until pokemonJSONInfo.getJSONArray("moves").length()){
            moves.add((pokemonJSONInfo.getJSONArray("moves")[i] as JSONObject).getJSONObject("move").getString("name"))
        }
        pokemon.moves = moves.distinct() //TODO : La requête semble retournée plusieurs fois les mêmes MOVES... à voir

        val abilities = mutableListOf<String>()
        for (i in 0 until pokemonJSONInfo.getJSONArray("abilities").length()){
            abilities.add((pokemonJSONInfo.getJSONArray("abilities")[i] as JSONObject).getJSONObject("ability").getString("name"))
        }
        pokemon.abilities = abilities

        pokemon.pv = (pokemonJSONInfo.getJSONArray("stats")[0] as JSONObject).getInt("value")
        pokemon.attack = (pokemonJSONInfo.getJSONArray("stats")[1] as JSONObject).getInt("value")
        pokemon.defence = (pokemonJSONInfo.getJSONArray("stats")[2] as JSONObject).getInt("value")
        pokemon.specialAttack = (pokemonJSONInfo.getJSONArray("stats")[3] as JSONObject).getInt("value")
        pokemon.specialDefence = (pokemonJSONInfo.getJSONArray("stats")[4] as JSONObject).getInt("value")
        pokemon.speed = (pokemonJSONInfo.getJSONArray("stats")[5] as JSONObject).getInt("value")

        //TODO : Mapper les 6 stats de bases également

        return pokemon
    }


    private fun getAllPokemonsWithGraphQL(context: Context): List<Pokemon> {
        val response: String?
        val cache = File(context.cacheDir, "pokemons_list.cache.json")

        if (cache.exists()) {
            response = cache.readText()
            Log.i("POKEMONS", "Using cached response for getAll()")
        } else {
            val query = """
                query pokemons_list {
                   pokemon_v2_pokemon(where: {id: {_lte: 1008}}) {
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
            var client = GraphQLApi("https://beta.pokeapi.co/graphql/v1beta/")
            response = client.queryJson(query)
        }

        if (response == null) {
            Log.w("POKEMONS", "Failed to load pokemons: request failed.")
        }

        val json = JSONObject(response)

        if (json.has("errors")) {
            Log.w("POKEMONS", "Failed to load pokemons: $response.")
        }

        try {
            val dataFromServerParsed = parsePokemonsJSON(json)
            if (!cache.exists())  cache.writeText(response!!)
            return dataFromServerParsed
        } catch(e: Exception) {
            Log.w("POKEMONS", "Failed to load pokemons: ${e.message}.")
        }

        return emptyList()
    }

    private fun parsePokemonsJSON(json: JSONObject) : MutableList<Pokemon> {
        val pokemons = mutableListOf<Pokemon>()
        val pokemonsJSONArray = json.getJSONObject("data").getJSONArray("pokemon_v2_pokemon")

        for (i in 0 until pokemonsJSONArray.length()) {
            try {
                parsePokemonJSON(pokemonsJSONArray, pokemons, i)
            } catch(e: Exception) {
                Log.w("POKEMONS", "Failed to load pokemon $i: ${e.message}.")
            }
        }
        return pokemons
    }

    private fun parsePokemonJSON(
        pokemonsJSONArray: JSONArray,
        pokemons: MutableList<Pokemon>,
        i: Int
    ) {
        val pokemonObject = pokemonsJSONArray.getJSONObject(i)
        val pokemon = Pokemon()

        pokemon.id = pokemonObject.getInt("id")
        pokemon.name = pokemonObject.getString("name")

        val pokemonTypesArray = pokemonObject.getJSONArray("pokemon_v2_pokemontypes")
        var types = mutableListOf<String>()
        for (j in 0 until pokemonTypesArray.length()) {
            types.add(pokemonTypesArray.getJSONObject(j).getJSONObject("pokemon_v2_type").getString("name"))
        }
        pokemon.type = types

        pokemons.add(pokemon)
    }
}