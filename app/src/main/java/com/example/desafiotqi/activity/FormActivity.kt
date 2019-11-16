package com.example.desafiotqi.activity

import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.desafiotqi.R
import com.example.desafiotqi.databinding.ActivityFormBinding
import com.example.desafiotqi.model.Bank

class FormActivity : AppCompatActivity() {

  lateinit var bank: Bank

  companion object {
    const val INTENT_PARAM_BANK = "bank"
  }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    title = getString(R.string.form)
    showBackArrow()

    val bank = intent.getSerializableExtra(INTENT_PARAM_BANK)
      ?: throw RuntimeException("expected a bank")

    this.bank = bank as Bank

    val binding = DataBindingUtil.setContentView<ActivityFormBinding>(
      this, R.layout.activity_form
    )

    binding.bank = bank
    binding.setClickListener {
      Toast.makeText(this, getString(R.string.coming_soon), Toast.LENGTH_LONG).show()
    }
  }

  private fun showBackArrow() {
    val actionBar = supportActionBar
    if (actionBar != null) {
      actionBar.setDisplayShowHomeEnabled(true)
      actionBar.setDisplayHomeAsUpEnabled(true)
    }
  }

  override fun onOptionsItemSelected(item: MenuItem): Boolean {
    if (item.itemId == android.R.id.home) {
      finish()
      return true
    }
    return super.onOptionsItemSelected(item)
  }
}
