package com.rshea.wmtpeymano.repository

import com.rshea.wmtpeymano.datasource.CountryRemoteDataSource
import com.rshea.wmtpeymano.models.Country
import com.rshea.wmtpeymano.ui.uistate.CountryItemUiState
import com.rshea.wmtpeymano.ui.uistate.CountryUiState
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class CountryRepository(
    private val countryRemoteDataSource: CountryRemoteDataSource
) {

    suspend fun fetchCountryInfoList(): CountryUiState {
        return suspendCoroutine { continuation ->
            val call: Call<List<Country>> = countryRemoteDataSource.fetchCountryInfoList()
            call.enqueue(object : Callback<List<Country>?> {
                override fun onResponse(
                    call: Call<List<Country>?>,
                    response: Response<List<Country>?>
                ) {
                    if (response.isSuccessful) {
                        val countryItemUiStateList: MutableList<CountryItemUiState> = mutableListOf()
                        response.body()!!.forEach{
                            countryItemUiStateList.add(CountryItemUiState(name = it.name, region = it.region, code = it.code, capital = it.capital, flag = it.flag))
                        }
                        continuation.resume(CountryUiState(false, countryItemUiStateList, ""))
                    } else {
                        continuation.resume(CountryUiState(false, listOf(), response.errorBody()!!.toString()))
                    }
                }

                override fun onFailure(call: Call<List<Country>?>, t: Throwable) {
                    val userMessages: String = t.toString()
                    continuation.resume(CountryUiState(false, listOf(), userMessages))
                }
            })

        }
    }
}