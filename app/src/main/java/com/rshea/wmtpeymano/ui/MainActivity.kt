package com.rshea.wmtpeymano.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.rshea.wmtpeymano.R
import com.rshea.wmtpeymano.databinding.ActivityMainBinding
import com.rshea.wmtpeymano.datasource.dto.CountryService
import com.rshea.wmtpeymano.datasource.dto.RetrofitHelper
import com.rshea.wmtpeymano.models.Country
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    companion object{
        private const val TAG = "MainActivity"
    }
    private lateinit var countryRecyclerAdapter: CountryRecyclerAdapter
    private lateinit var countries: List<Country>
    private lateinit var binding : ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.progressBar.visibility = View.VISIBLE

        lifecycleScope.launch {
            val call = initiateCountryApi()
            call!!.enqueue(object : Callback<ArrayList<Country>?> {
                override fun onResponse(
                    call: Call<ArrayList<Country>?>,
                    response: Response<ArrayList<Country>?>
                ) {
                    if (response.isSuccessful) {
                        binding.progressBar.visibility = View.INVISIBLE

                        countries = response.body()!!
                        Log.d(TAG, countries.toString())

                        initRecyclerView(binding, countries)
                    }
                }

                override fun onFailure(call: Call<ArrayList<Country>?>, t: Throwable) {
                    // displaying an error message in toast
                    Toast.makeText(this@MainActivity, "Fail to get the data..", Toast.LENGTH_SHORT)
                        .show()
                }
            })
        }
    }

    private fun initiateCountryApi() : Call<ArrayList<Country>?>? {
        return RetrofitHelper.getInstance().create(CountryService::class.java).getCountryList()
    }

    private fun initRecyclerView(binding: ActivityMainBinding, countries: List<Country>) {
        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            countryRecyclerAdapter = CountryRecyclerAdapter(countries)
            addItemDecoration(DividerItemDecoration(this@MainActivity, DividerItemDecoration.VERTICAL))
            adapter = countryRecyclerAdapter
        }
    }

}