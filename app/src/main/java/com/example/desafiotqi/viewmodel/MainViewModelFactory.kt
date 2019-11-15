package com.example.desafiotqi.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.desafiotqi.repository.BankRepository

class MainViewModelFactory(
  private val bankRepository: BankRepository
) : ViewModelProvider.NewInstanceFactory() {
  @Suppress("UNCHECKED_CAST")
  override fun <T : ViewModel> create(modelClass: Class<T>): T {
    return MainViewModel(bankRepository) as T
  }
}
