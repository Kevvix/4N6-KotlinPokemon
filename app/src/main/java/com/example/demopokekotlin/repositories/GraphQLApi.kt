package com.example.demopokekotlin.repositories

import android.util.Log
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
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

        val response = http.newCall(request).execute() //TODO : Ceci est blocquant!

        if (response.body == null) {
            Log.w("POKEMONS", "Failed to load pokemons: code ${response.code}.")
            return null
        }

        return response.body!!.string()
    }
}