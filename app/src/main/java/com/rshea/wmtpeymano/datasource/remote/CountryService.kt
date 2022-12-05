package com.rshea.wmtpeymano.datasource.remote

import com.rshea.wmtpeymano.models.Country
import retrofit2.Call

class CountryService {

    private val baseUrl = "https://gist.githubusercontent.com/"

    fun fetchCountryInfoList() : Call<List<Country>> {
        return  RetrofitHelper(baseUrl).getInstance().create(CountryApi::class.java).getCountryList()
    }
}