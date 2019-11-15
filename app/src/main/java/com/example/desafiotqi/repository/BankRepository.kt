package com.example.desafiotqi.repository

import com.example.desafiotqi.model.Bank
import com.example.desafiotqi.network.RequestCallback

class BankRepositoryImpl : BankRepository {

  override fun getBanks(callback: RequestCallback<List<Bank>>) {
    // TODO

  }

}

interface BankRepository {
  fun getBanks(callback: RequestCallback<List<Bank>>)
}
