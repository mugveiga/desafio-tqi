package com.example.desafiotqi.model

class Bank : MainListItem(), Comparable<Bank> {

  lateinit var code: String
  var favorite: Boolean = false
  lateinit var image: String

  override fun compareTo(other: Bank): Int {
    val compareFavorite = favorite.compareTo(other.favorite)
    if (compareFavorite == 0) return name.compareTo(other.name)
    return compareFavorite
  }

  fun displayName() : String {
    return "$code - $name"
  }
}
