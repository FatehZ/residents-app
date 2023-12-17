package com.ktxdevelopment.residentapp.adapters

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import com.ktxdevelopment.residentapp.databinding.LayoutFilterItemBinding
import com.ktxdevelopment.residentapp.model.CityFilter

class CityFilterAdapter(context: Context,
                        private var filterOptions: List<CityFilter> = arrayListOf(),
                        private var checkedChange: (CityFilter) -> Unit) :
    BaseAdapter() {

    private val inflater: LayoutInflater = LayoutInflater.from(context)

    override fun getCount(): Int = filterOptions.size

    override fun getItem(position: Int): Any = filterOptions[position]

    override fun getItemId(position: Int): Long = position.toLong()

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val binding: LayoutFilterItemBinding
        val view: View

        if (convertView == null) {
            binding = LayoutFilterItemBinding.inflate(inflater, parent, false)
            view = binding.root
            view.tag = binding
        } else {
            binding = convertView.tag as LayoutFilterItemBinding
            view = convertView
        }

        val option = filterOptions[position]

        binding.cvFilter.isChecked = option.selected
        binding.tvCountryName.text = option.city.name

        binding.cvFilter.setOnClickListener { checkedChange(option.copy(selected = binding.cvFilter.isChecked)) }

        return view
    }

    fun setData(it: List<CityFilter>?) {
        filterOptions = it?.filter { it.present } ?: arrayListOf()
        Log.e("LTS_TAG", filterOptions.joinToString { it.city.name })
        notifyDataSetChanged()
    }
}
