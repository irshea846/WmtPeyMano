package com.rshea.wmtpeymano.ui.uistate

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.rshea.wmtpeymano.datasource.CountryRemoteDataSource
import com.rshea.wmtpeymano.datasource.remote.CountryService
import com.rshea.wmtpeymano.repository.CountryRepository

class CountryViewModelFactory : ViewModelProvider.Factory{

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CountryViewModel::class.java)){
            return CountryViewModel(CountryRepository(CountryRemoteDataSource(CountryService()))) as T
        }
        throw IllegalArgumentException("Unknown View Model Class")
    }

}
