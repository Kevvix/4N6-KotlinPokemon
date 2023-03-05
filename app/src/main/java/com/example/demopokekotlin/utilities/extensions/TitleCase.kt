package com.example.demopokekotlin.utilities

// fonction d'extension de la classe String
public fun String.titleCase(): String
    = this.first().uppercaseChar() + this.drop(1)