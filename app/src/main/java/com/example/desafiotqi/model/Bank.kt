package com.example.desafiotqi.model

import java.io.Serializable

class Bank : MainListItem(), Comparable<Bank>, Serializable {

  lateinit var code: String
  var favorite: Boolean = false
  var image: String = ""

  override fun compareTo(other: Bank): Int {
    val compareFavorite = favorite.not().compareTo(other.favorite.not())
    if (compareFavorite == 0) return name.compareTo(other.name)
    return compareFavorite
  }

  fun displayName() : String {
    return "$code - $name"
  }
}
