package com.example.desafiotqi.repository

import com.example.desafiotqi.model.Bank
import com.example.desafiotqi.network.MyApi
import com.example.desafiotqi.network.RequestCallback
import com.example.desafiotqi.network.RequestController

class BankRepositoryImpl(
  private val myApi: MyApi,
  private val requestController: RequestController
) : BankRepository {

  override fun getBanks(callback: RequestCallback<List<Bank>>) {
    requestController.runAssync(myApi.getBanks(), callback)
  }
}

interface BankRepository {
  fun getBanks(callback: RequestCallback<List<Bank>>)
}
