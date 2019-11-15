package com.example.desafiotqi.network

import com.example.desafiotqi.MyApplication
import com.example.desafiotqi.R
import com.example.desafiotqi.model.MyError
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RequestController(
  private val internetServices: InternetServices
) {

  fun <T> runAssync(call: Call<T>, listener: RequestCallback<T>?) {
    if (internetServices.isNetworkConnected()) {
      call.enqueue(object : Callback<T> {
        override fun onResponse(call: Call<T>, response: Response<T>) {
          if (response.isSuccessful && response.body() != null) {
            listener?.onSuccess(response.body()!!)
          } else {
            listener?.onFailure(handleFailure(response))
          }
        }

        override fun onFailure(call: Call<T>, t: Throwable) {
          listener?.onFailure(handleThrowable(t))
        }
      })
    } else {
      listener?.onFailure(internetError())
    }
  }

  private fun internetError(): MyError {
    return MyError(MyApplication.context.getString(R.string.no_internet), -1)
  }

  private fun handleFailure(response: Response<*>): MyError {
    val context = MyApplication.context
    var error = context.getString(R.string.generic_error)
    if (response.errorBody() != null) {
      try {
        val tmp = response.errorBody()!!.string()
        if (response.code() < 500 && tmp.isNotEmpty()) error = tmp
        if (response.code() == 504) error = context.getString(R.string.timeout_error)
      } catch (e: Exception) {
        e.printStackTrace()
      }
    }
    return MyError(error, response.code())
  }

  private fun handleThrowable(t: Throwable): MyError {
    t.printStackTrace()
    var localizedMessage = t.localizedMessage
    val context = MyApplication.context
    val genericError = context.getString(R.string.generic_error)
    if (localizedMessage == null) return MyError(
      genericError,
      -1
    )
    if (localizedMessage.contains("timeout")) {
      localizedMessage = context.getString(R.string.timeout_error)
      return MyError(localizedMessage, 504)
    }
    return MyError(localizedMessage, -1)
  }
}
