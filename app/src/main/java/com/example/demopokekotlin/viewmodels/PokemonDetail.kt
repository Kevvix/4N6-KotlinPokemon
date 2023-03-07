package com.example.demopokekotlin.viewmodels

import android.net.UrlQuerySanitizer.ParameterValuePair

class PokemonDetail {
    var id: Int? = null
    var name: String? = null
    var types: List<String>? = null
    var height: Double? = null
    var weight: Double? = null
    var isLegendary: Boolean? = null
    var isMythical: Boolean? = null
    var moves: List<String>? = null
    var abilities: List<String>? = null
    var pv : Int? = null
    var attack : Int? = null
    var defence : Int? = null
    var specialAttack : Int? = null
    var specialDefence : Int? = null
    var speed : Int? = null
}