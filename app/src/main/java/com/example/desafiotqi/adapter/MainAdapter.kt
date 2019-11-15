package com.example.desafiotqi.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import com.example.desafiotqi.databinding.RowBankBinding
import com.example.desafiotqi.databinding.RowCategoryBinding
import com.example.desafiotqi.model.Bank
import com.example.desafiotqi.model.MainListItem
import java.text.Normalizer

class MainAdapter :
  RecyclerView.Adapter<RecyclerView.ViewHolder>(), Filterable {

  private lateinit var fullList: ArrayList<MainListItem>
  private lateinit var filteredList: ArrayList<MainListItem>

  companion object {
    const val TYPE_BANK = 2
    const val TYPE_CATEGORY = 1
  }

  private fun stripAccents(s: String): String {
    val temp = Normalizer.normalize(s, Normalizer.Form.NFD)
    return temp.replace("[\\p{InCombiningDiacriticalMarks}]".toRegex(), "")
  }

  override fun getFilter(): Filter {
    return object : Filter() {
      @SuppressLint("DefaultLocale")
      override fun performFiltering(charSequence: CharSequence?): FilterResults {

        val charString = stripAccents(charSequence.toString().toLowerCase())
        val results = if (charString.isEmpty()) {
          fullList
        } else {
          val newFilteredList = ArrayList<MainListItem>()
          val onlyBanks = MainListItem.filterBanks(fullList)
          for (bank in onlyBanks) {
            if (stripAccents(bank.name.toLowerCase()).contains(charString)) {
              newFilteredList.add(bank)
            }
          }
          newFilteredList
        }
        val filterResults = FilterResults()
        filterResults.values = results
        return filterResults
      }

      override fun publishResults(p0: CharSequence?, p1: FilterResults?) {
        if (p1 != null) {
          filteredList = p1.values as ArrayList<MainListItem>
          notifyDataSetChanged()
        }
      }
    }
  }

  override fun getItemCount(): Int {
    return filteredList.size
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

  fun submitList(list: ArrayList<MainListItem>) {
    fullList = list
    filteredList = list
    notifyDataSetChanged()
  }

  private fun getItem(position: Int): MainListItem {
    return filteredList[position]
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
