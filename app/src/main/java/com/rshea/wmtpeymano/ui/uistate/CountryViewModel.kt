package com.rshea.wmtpeymano.ui.uistate

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rshea.wmtpeymano.repository.CountryRepository
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.io.IOException

class CountryViewModel(
    private val countryRepository: CountryRepository, application: Application
): AndroidViewModel(application) {

    private val _uiState = MutableStateFlow(CountryUiState(isFetchingData = false, countriesItems = listOf(), userMessages = ""))
    val uiState: StateFlow<CountryUiState> = _uiState.asStateFlow()

    private var fetchJob: Job? = null

    fun fetchCountries() {
        fetchJob?.cancel()
        fetchJob = viewModelScope.launch {
            try {
                val countryUiState = countryRepository.fetchCountryInfoList()
                _uiState.update {
                    it.copy(
                        isFetchingData = countryUiState.isFetchingData,
                        countriesItems = countryUiState.countriesItems,
                        userMessages = countryUiState.userMessages
                    )
                }
            } catch (ioe: IOException) {
                _uiState.update {
                    val messages: String? = ioe.message
                    it.copy(
                        isFetchingData = false,
                        countriesItems = listOf(),
                        userMessages = messages!!
                    )
                }
            }
        }
    }
}