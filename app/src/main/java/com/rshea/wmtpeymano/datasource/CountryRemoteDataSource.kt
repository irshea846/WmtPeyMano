package com.rshea.wmtpeymano.datasource

import com.rshea.wmtpeymano.datasource.remote.CountryService
import com.rshea.wmtpeymano.models.Country
import retrofit2.Call

class CountryRemoteDataSource(
    private val countryService: CountryService,
) {
    fun fetchCountryInfoList(): Call<List<Country>> = countryService.fetchCountryInfoList()
}
