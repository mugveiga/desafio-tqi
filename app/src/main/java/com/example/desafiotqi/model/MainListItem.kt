package com.example.desafiotqi.model

open class MainListItem {

  lateinit var name: String

  constructor()

  constructor(name: String) {
    this.name = name
  }

  override fun equals(other: Any?): Boolean {
    if (this === other) return true
    if (other !is MainListItem) return false

    if (name != other.name) return false

    return true
  }

  override fun hashCode(): Int {
    return name.hashCode()
  }
}
