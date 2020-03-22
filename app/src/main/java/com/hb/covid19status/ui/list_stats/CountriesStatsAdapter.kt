package com.hb.covid19status.ui.list_stats

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.hb.covid19status.R
import com.hb.covid19status.databinding.ItemCountryStatsBinding
import com.hb.covid19status.model.CountryStat

class CountriesStatsAdapter(
    var listStats: List<CountryStat>,
    val countriesItemClickListener: CountriesItemClickListener
) :
    RecyclerView.Adapter<CountriesStatsAdapter.ViewHolder>(), Filterable {

    override fun getItemCount(): Int {
        return listStats.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.item_country_stats,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindViewHolder(listStats[position])
    }

    inner class ViewHolder(private val viewDataBinding: ItemCountryStatsBinding) :
        RecyclerView.ViewHolder(viewDataBinding.root) {

        fun bindViewHolder(countryStats: CountryStat) {
             viewDataBinding.item = countryStats
            viewDataBinding.itemClickListener = countriesItemClickListener
        }
    }

    private var tempListCountries: List<CountryStat> = listStats
    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(charSequence: CharSequence): FilterResults {
                val charString = charSequence.toString()
                if (charString.isEmpty()) {
                    listStats = tempListCountries
                } else {
                val filteredList = ArrayList<CountryStat>()
                    for (row in tempListCountries) {
                        if (row.country_name.toLowerCase().contains(charString.toLowerCase())) {
                            filteredList.add(row)
                        }
                    }
                    listStats = filteredList
                }
                val filterResults = FilterResults()
                filterResults.values = listStats
                return filterResults
            }
            override fun publishResults(charSequence: CharSequence, filterResults: FilterResults) {
                listStats = filterResults.values as List<CountryStat>
                notifyDataSetChanged()
            }
        }
    }

}