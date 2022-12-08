package com.rshea.wmtpeymano.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.FitCenter
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.rshea.wmtpeymano.databinding.LayoutCountryListItemBinding
import com.rshea.wmtpeymano.ui.uistate.CountryItemUiState
import com.squareup.picasso.Picasso

class CountryRecyclerAdapter(countryList: List<CountryItemUiState>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var items = countryList

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val countryListItemBinding: LayoutCountryListItemBinding =
            LayoutCountryListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CountryViewHolder(countryListItemBinding)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item: CountryItemUiState = items[position]
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

        fun bind(country: CountryItemUiState) {
            Picasso.get().load(flagBaseUrl + country.code).fit().into(itemBinding.flag)
//            Glide.with(itemView)
//                .load(flagBaseUrl + country.code)
//                .skipMemoryCache(false)//for caching the image url in case phone is offline
//                .into(itemBinding.flag)

            itemBinding.name.text = StringBuilder(country.name).append(", ").toString()
            itemBinding.region.text = country.region
            itemBinding.code.text = country.code
            itemBinding.capital.text = country.capital
        }

    }

    companion object {
        private const val flagBaseUrl = "https://countryflagsapi.com/png/"
        private var requestOptions = RequestOptions().transform(FitCenter(), RoundedCorners(16))
    }
}