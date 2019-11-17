package com.example.desafiotqi.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.desafiotqi.model.Bank
import com.example.desafiotqi.model.MainListItem
import com.example.desafiotqi.model.MyError
import com.example.desafiotqi.network.RequestCallback
import com.example.desafiotqi.repository.BankRepository

class MainViewModel(
  var repository: BankRepository
) : ViewModel() {

  val loadingState: MutableLiveData<Boolean> = MutableLiveData(true)
  val items: MutableLiveData<ArrayList<MainListItem>> = MutableLiveData(ArrayList())
  val error: MutableLiveData<String> = MutableLiveData("")

  init {
    retrieveBanks()
  }

  fun refresh() {
    retrieveBanks()
  }

  private fun retrieveBanks() {
    loadingState.postValue(true)
    repository.getBanks(object : RequestCallback<List<Bank>> {
      override fun onSuccess(response: List<Bank>) {
        items.postValue(MainListItem.addCategories(response))
        loadingState.postValue(false)
      }

      override fun onFailure(myError: MyError) {
        if (myError.message != null) error.postValue(myError.message)
        loadingState.postValue(false)
      }
    })
  }
}
