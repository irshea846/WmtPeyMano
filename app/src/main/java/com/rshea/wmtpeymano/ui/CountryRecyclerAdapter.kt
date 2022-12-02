package com.rshea.wmtpeymano.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.rshea.wmtpeymano.databinding.LayoutCountryListItemBinding
import com.rshea.wmtpeymano.models.Country

class CountryRecyclerAdapter(countryList: List<Country>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var items = countryList
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val countryListItemBinding: LayoutCountryListItemBinding =
            LayoutCountryListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CountryViewHolder(countryListItemBinding)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item: Country = items[position]
        when(holder) {
            is CountryViewHolder -> {
                holder.bind(item)
            }
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }

    class CountryViewHolder constructor(
        private val itemBinding: LayoutCountryListItemBinding
    ): RecyclerView.ViewHolder(itemBinding.root) {

        fun bind(country: Country) {
            itemBinding.name.text = StringBuilder(country.name).append(", ").toString()
            itemBinding.region.text = country.region
            itemBinding.code.text = country.code
            itemBinding.capital.text = country.capital
        }

    }
}