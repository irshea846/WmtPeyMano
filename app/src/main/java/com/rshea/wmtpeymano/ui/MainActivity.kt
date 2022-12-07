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
import com.rshea.wmtpeymano.ui.uistate.CountryViewModelFactory
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private lateinit var countryRecyclerAdapter: CountryRecyclerAdapter
    private lateinit var binding : ActivityMainBinding
    private lateinit var countryViewModel: CountryViewModel
    private lateinit var countryModelFactory: CountryViewModelFactory

    override fun onCreate(savedInstanceState: Bundle?) {

        countryModelFactory = CountryViewModelFactory()
        countryViewModel  = ViewModelProvider(this, countryModelFactory)[CountryViewModel::class.java]
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.progressBar.visibility = View.VISIBLE
        countryViewModel.fetchCountries()

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                countryViewModel.uiState.collect {
                    binding.progressBar.isVisible = it.isFetchingData
                    initRecyclerView(binding, it.countriesItems)
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