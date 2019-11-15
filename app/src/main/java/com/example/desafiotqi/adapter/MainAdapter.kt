package com.example.desafiotqi.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.desafiotqi.databinding.RowBankBinding
import com.example.desafiotqi.databinding.RowCategoryBinding
import com.example.desafiotqi.model.Bank
import com.example.desafiotqi.model.MainListItem

class MainAdapter :
  ListAdapter<MainListItem, RecyclerView.ViewHolder>(MainListItemDiffCallback()) {

  companion object {
    const val TYPE_BANK = 2
    const val TYPE_CATEGORY = 1
  }

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
    if (viewType == TYPE_BANK) {
      return BankViewHolder(
        RowBankBinding.inflate(LayoutInflater.from(parent.context), parent, false)
      )
    }
    return CategoryViewHolder(
      RowCategoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )
  }

  override fun getItemViewType(position: Int): Int {
    if (getItem(position) is Bank) return TYPE_BANK
    return TYPE_CATEGORY
  }

  override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
    val item = getItem(position)
    if (item is Bank) {
      (holder as BankViewHolder).bind(item)
    } else {
      (holder as CategoryViewHolder).bind(item)
    }
  }

  class BankViewHolder(
    private val binding: RowBankBinding
  ) : RecyclerView.ViewHolder(binding.rootView) {
    init {
      binding.setClickListener {

        // TODO go to bank activity
//        val bank = binding.bank!!
//        val context = itemView.context
//        val intent = Intent(context, BankActivity::class.java)
//        val b = Bundle()
//        b.putSerializable(
//          BankActivity.INTENT_PARAM_BANK, bank
//        )
//        intent.putExtras(b)
//        context.startActivity(intent)
      }
    }

    fun bind(bank: Bank) {
      binding.apply {
        this.bank = bank
        executePendingBindings()
      }
    }
  }

  class CategoryViewHolder(
    private val binding: RowCategoryBinding
  ) : RecyclerView.ViewHolder(binding.rootView) {
    fun bind(category: MainListItem) {
      binding.apply {
        this.category = category
        executePendingBindings()
      }
    }
  }
}

private class MainListItemDiffCallback : DiffUtil.ItemCallback<MainListItem>() {

  override fun areItemsTheSame(oldItem: MainListItem, newItem: MainListItem): Boolean {
    return oldItem.name == newItem.name
  }

  override fun areContentsTheSame(oldItem: MainListItem, newItem: MainListItem): Boolean {
    val compare1 = oldItem.name == newItem.name
    return if (oldItem is Bank && newItem is Bank) {
      val compare2 = oldItem.favorite == newItem.favorite
      val compare3 = oldItem.code == newItem.code
      val compare4 = oldItem.image == newItem.image
      compare1 && compare2 && compare3 && compare4
    } else {
      compare1
    }
  }
}
