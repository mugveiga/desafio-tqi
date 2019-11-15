package com.example.desafiotqi.viewmodel

import com.example.desafiotqi.network.ApiServices
import com.example.desafiotqi.network.InternetServices
import com.example.desafiotqi.network.MyApi
import com.example.desafiotqi.network.RequestController
import com.example.desafiotqi.repository.BankRepositoryImpl

object InjectorUtils {

  private fun getMyApi(): MyApi {
    return ApiServices.myApi
  }

  private fun getRequestController(): RequestController {
    return RequestController(getInternetServices())
  }

  private fun getInternetServices(): InternetServices {
    return InternetServices()
  }

  private fun getBankRepository(): BankRepositoryImpl {
    return BankRepositoryImpl(
      getMyApi(),
      getRequestController()
    )
  }

  fun provideMainViewModelFactory(): MainViewModelFactory {
    return MainViewModelFactory(
      getBankRepository()
    )
  }
}
