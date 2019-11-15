package com.example.desafiotqi.activity

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.example.desafiotqi.R
import com.example.desafiotqi.viewmodel.InjectorUtils
import com.example.desafiotqi.viewmodel.MainViewModel
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*

class MainActivity : AppCompatActivity() {

  private val viewModel: MainViewModel by viewModels {
    InjectorUtils.provideMainViewModelFactory()
  }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)
    setSupportActionBar(toolbar)

    // TODO list adapter

    viewModel.error.observe(this, Observer {
      if (it != null && it.isNotEmpty()) {
        Toast.makeText(this@MainActivity, it, Toast.LENGTH_LONG).show()
      }
    })

    viewModel.loadingState.observe(this, Observer {
      swipeRefresh.isRefreshing = it
    })

    viewModel.items.observe(this, Observer {
      // TODO set items on adapter
    })

    swipeRefresh.setOnRefreshListener {
      viewModel.refresh()
    }
  }
}
