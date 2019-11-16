package com.example.desafiotqi.model

import java.io.Serializable

open class MainListItem : Serializable {

  lateinit var name: String

  constructor()

  constructor(name: String) {
    this.name = name
  }

  companion object {
    private const val FAVORITES = "Mais utilizadas"
    private const val OTHERS = "Outras"

    fun addCategories(banks: List<Bank>): ArrayList<MainListItem> {
      val sorted = banks.sorted()
      val list = ArrayList<MainListItem>()
      list.add(MainListItem(FAVORITES))
      var secondHeaderAdded = false
      for (bank in sorted) {
        if (!bank.favorite && !secondHeaderAdded) {
          list.add(MainListItem(OTHERS))
          secondHeaderAdded = true
        }
        list.add(bank)
      }
      return list
    }

    fun filterBanks(list: ArrayList<MainListItem>): List<Bank> {
      val ret = ArrayList<Bank>()
      for (item in list) {
        if (item is Bank) ret.add(item)
      }
      return ret
    }
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
