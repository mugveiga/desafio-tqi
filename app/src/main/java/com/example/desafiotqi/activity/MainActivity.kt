package com.example.desafiotqi.activity

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.desafiotqi.R
import com.example.desafiotqi.viewmodel.MainViewModel
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*

class MainActivity : AppCompatActivity() {

  private lateinit var viewModel: MainViewModel

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)
    setSupportActionBar(toolbar)

    viewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)

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
