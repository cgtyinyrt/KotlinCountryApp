package com.cagatayinyurt.kotlincountryapp.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.cagatayinyurt.kotlincountryapp.R
import com.cagatayinyurt.kotlincountryapp.databinding.FragmentCountryBinding
import com.cagatayinyurt.kotlincountryapp.viewmodel.CountryViewModel

class CountryFragment : Fragment() {

    private lateinit var dataBinding: FragmentCountryBinding
    private var countryUuid = 0
    private lateinit var viewModel: CountryViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        dataBinding =
            DataBindingUtil.inflate(
                inflater,
                R.layout.fragment_country,
                container,
                false
            )
        return dataBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.let {
            countryUuid = CountryFragmentArgs.fromBundle(it).countryUuid
        }

        viewModel = ViewModelProvider(this).get(CountryViewModel::class.java)
        viewModel.getDataFromRoom(countryUuid)

        observeLiveData()
    }

    private fun observeLiveData() {
        viewModel.countryLiveData.observe(viewLifecycleOwner, Observer { country ->
            country?.let {
                dataBinding.selectedCountry = it
                
//                dataBinding.tvCountryNameDetail.text = country.countryName
//                dataBinding.tvCountryCapitalDetail.text = country.countryCapital
//                dataBinding.tvCountryRegionDetail.text = country.countryRegion
//                dataBinding.tvCountryCurrencyDetail.text = country.countryCurrency
//                dataBinding.tvCountryLanguageDetail.text = country.countryLanguage
//                context?.let {
//                    dataBinding.ivCountryFlag.downloadFromUrl(country.imageUrl, placeHolderProgressBar(it))
//                }
            }
        })
    }
}