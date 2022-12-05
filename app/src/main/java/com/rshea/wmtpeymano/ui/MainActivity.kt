package com.rshea.wmtpeymano.ui

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.*
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.rshea.wmtpeymano.R
import com.rshea.wmtpeymano.databinding.ActivityMainBinding
import com.rshea.wmtpeymano.ui.uistate.CountryItemUiState
import com.rshea.wmtpeymano.ui.uistate.CountryViewModel
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private lateinit var countryRecyclerAdapter: CountryRecyclerAdapter
    private lateinit var binding : ActivityMainBinding
    private lateinit var countryViewModel: CountryViewModel

    override fun onCreate(savedInstanceState: Bundle?) {

        countryViewModel  = ViewModelProvider.AndroidViewModelFactory.getInstance(application).create(CountryViewModel::class.java)
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.progressBar.visibility = View.VISIBLE

        lifecycleScope.launch {
            countryViewModel.fetchCountries()
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                countryViewModel.uiState
                    .map { it.isFetchingData }
                    .distinctUntilChanged()
                    .collect { binding.progressBar.isVisible = it }
                countryViewModel.uiState
                    .map { it.countriesItems }
                    .distinctUntilChanged()
                    .run {
                        initRecyclerView(binding, this.first())
                    }
            }
        }
    }

    private fun initRecyclerView(binding: ActivityMainBinding, countries: List<CountryItemUiState>) {
        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            countryRecyclerAdapter = CountryRecyclerAdapter(countries)
            addItemDecoration(DividerItemDecoration(this@MainActivity, DividerItemDecoration.VERTICAL))
            adapter = countryRecyclerAdapter
        }
    }

}