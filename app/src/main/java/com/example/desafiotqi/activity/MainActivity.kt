package com.example.desafiotqi.activity

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.Observer
import com.example.desafiotqi.R
import com.example.desafiotqi.adapter.MainAdapter
import com.example.desafiotqi.viewmodel.InjectorUtils
import com.example.desafiotqi.viewmodel.MainViewModel
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*


class MainActivity : AppCompatActivity(), SearchView.OnQueryTextListener {

  private val adapter = MainAdapter()
  private lateinit var searchView: SearchView
  val viewModel: MainViewModel by viewModels {
    InjectorUtils.provideMainViewModelFactory()
  }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)
    setSupportActionBar(toolbar)
    title = getString(R.string.finantial_institution)

    // list
    recyclerView.adapter = adapter

    // swipe refresh
    swipeRefresh.setOnRefreshListener {
      viewModel.refresh()
    }

    // observables
    viewModel.error.observe(this, Observer {
      if (it != null && it.isNotEmpty()) {
        Toast.makeText(this@MainActivity, it, Toast.LENGTH_LONG).show()
      }
    })
    viewModel.loadingState.observe(this, Observer {
      swipeRefresh.isRefreshing = it
    })
    viewModel.items.observe(this, Observer {
      adapter.submitList(it)
    })
  }

  override fun onQueryTextSubmit(query: String?): Boolean {
    adapter.filter.filter(query)
    return false
  }

  override fun onQueryTextChange(newText: String?): Boolean {
    adapter.filter.filter(newText)
    return false
  }

  override fun onCreateOptionsMenu(menu: Menu): Boolean {
    menuInflater.inflate(R.menu.menu_main, menu)
    searchView = menu.findItem(R.id.action_search).actionView as SearchView
    searchView.queryHint = getString(R.string.search_institution)
    searchView.maxWidth = Integer.MAX_VALUE
    searchView.setOnQueryTextListener(this)
    return true
  }

  override fun onOptionsItemSelected(item: MenuItem): Boolean {
    return if (item.itemId == R.id.action_search) {
      true
    } else super.onOptionsItemSelected(item)
  }

  override fun onBackPressed() {
    // close search view on back button pressed
    if (!searchView.isIconified) {
      searchView.isIconified = true
      return
    }
    super.onBackPressed()
  }
}
