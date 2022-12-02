package com.rshea.wmtpeymano.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rshea.wmtpeymano.repository.CountryRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CountryViewModel(
    private val countryRepository: CountryRepository
): ViewModel() {

    companion object {
        private const val TAG = "CountryViewModel"
    }

    fun getCountryService() {
        viewModelScope.launch(Dispatchers.IO) {
            countryRepository.getCountryInfoList()
        }
    }
}