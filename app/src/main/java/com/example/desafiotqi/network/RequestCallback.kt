package com.example.desafiotqi.network

import com.example.desafiotqi.model.MyError


interface RequestCallback<T> {

  fun onSuccess(response: T)

  fun onFailure(error: MyError)
}
