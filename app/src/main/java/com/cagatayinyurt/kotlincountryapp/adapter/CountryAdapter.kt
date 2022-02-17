package com.cagatayinyurt.kotlincountryapp.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.cagatayinyurt.kotlincountryapp.R
import com.cagatayinyurt.kotlincountryapp.databinding.ItemCountryBinding
import com.cagatayinyurt.kotlincountryapp.model.Country
import com.cagatayinyurt.kotlincountryapp.view.FeedFragmentDirections
import kotlinx.android.synthetic.main.item_country.view.*

class CountryAdapter(
    private val countryList: ArrayList<Country>
) : RecyclerView.Adapter<CountryAdapter.CountryHolder>(),
    CountryClickListener {

    inner class CountryHolder(
        var view: ItemCountryBinding
    ) : RecyclerView.ViewHolder(view.root)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CountryHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view =
            DataBindingUtil.inflate<ItemCountryBinding>(
                inflater,
                R.layout.item_country,
                parent,
                false
            )
        return CountryHolder(view)
    }

    override fun onBindViewHolder(holder: CountryHolder, position: Int) {
        holder.view.country = countryList[position]
        holder.view.listener = this

//        holder.binding.tvCountryName.text = countryList[position].countryName
//        holder.binding.tvCountryRegion.text = countryList[position].countryRegion
//
//        holder.itemView.setOnClickListener {
//            val action =
//                FeedFragmentDirections.actionFeedFragmentToCountryFragment(countryList[position].uuid)
//            Navigation.findNavController(it).navigate(action)
//        }
//
//        holder.binding.imageView.downloadFromUrl(
//            countryList[position].imageUrl,
//            placeHolderProgressBar(holder.itemView.context)
//        )
    }

    override fun getItemCount(): Int {
        return countryList.count()
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateCountryList(newCountryList: List<Country>) {
        countryList.clear()
        countryList.addAll(newCountryList)
        notifyDataSetChanged()
    }

    override fun onCountryClicked(v: View) {
        val uuid = v.countryUuidText.text.toString().toInt()
        val action = FeedFragmentDirections.actionFeedFragmentToCountryFragment(uuid)
        Navigation.findNavController(v).navigate(action)
    }
}