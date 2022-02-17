package com.cagatayinyurt.kotlincountryapp.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.cagatayinyurt.kotlincountryapp.R
import com.cagatayinyurt.kotlincountryapp.adapter.CountryAdapter
import com.cagatayinyurt.kotlincountryapp.databinding.FragmentFeedBinding
import com.cagatayinyurt.kotlincountryapp.viewmodel.FeedViewModel

class FeedFragment : Fragment() {

    private lateinit var binding: FragmentFeedBinding
    private lateinit var viewModel: FeedViewModel
    private val countryAdapter = CountryAdapter(arrayListOf())

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding =
            DataBindingUtil.inflate(
                inflater,
                R.layout.fragment_feed,
                container,
                false
            )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this).get(FeedViewModel::class.java)
        viewModel.refreshData()

        binding.rvCountryList.layoutManager = LinearLayoutManager(context)
        binding.rvCountryList.adapter = countryAdapter

        binding.swipeRefreshLayout.setOnRefreshListener {
            binding.rvCountryList.visibility = View.GONE
            binding.tvCountryError.visibility = View.GONE
            binding.pbCountryLoading.visibility = View.VISIBLE
            viewModel.refreshFromAPI()
            binding.swipeRefreshLayout.isRefreshing = false
        }

        observeLiveData()

//        binding.btnToCountry.setOnClickListener {
//            val action = FeedFragmentDirections.actionFeedFragmentToCountryFragment()
//            Navigation.findNavController(it).navigate(action)
//        }
    }

    private fun observeLiveData() {
        viewModel.countries.observe(viewLifecycleOwner, Observer { countries ->
            countries?.let {
                binding.rvCountryList.visibility = View.VISIBLE
                countryAdapter.updateCountryList(countries)
            }
        })
        viewModel.countryError.observe(viewLifecycleOwner, Observer { error ->
            error?.let {
                if (it) {
                    binding.tvCountryError.visibility = View.VISIBLE
                } else {
                    binding.tvCountryError.visibility = View.GONE
                }
            }
        })
        viewModel.countryLoading.observe(viewLifecycleOwner, Observer { loading ->
            loading?.let {
                if (it) {
                    binding.pbCountryLoading.visibility = View.VISIBLE
                    binding.rvCountryList.visibility = View.GONE
                    binding.tvCountryError.visibility = View.GONE
                } else {
                    binding.pbCountryLoading.visibility = View.GONE
                }
            }
        })
    }
}