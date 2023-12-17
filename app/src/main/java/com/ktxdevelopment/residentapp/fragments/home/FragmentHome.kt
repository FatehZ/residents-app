package com.ktxdevelopment.residentapp.fragments.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.ktxdevelopment.common.Resource
import com.ktxdevelopment.residentapp.adapters.CityFilterAdapter
import com.ktxdevelopment.residentapp.adapters.CountryFilterAdapter
import com.ktxdevelopment.residentapp.adapters.ResidentCardsAdapter
import com.ktxdevelopment.residentapp.databinding.FragmentHomeBinding
import com.ktxdevelopment.residentapp.databinding.LayoutCityFilterSheetBinding
import dagger.hilt.android.AndroidEntryPoint
import java.net.ConnectException


@AndroidEntryPoint
class FragmentHome : Fragment() {
    companion object { fun newInstance() = FragmentHome() }

    private val viewModel: FragmentHomeViewModel by viewModels()
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private var rvAdapter: ResidentCardsAdapter? = null
    private var countryFilterAdapter: CountryFilterAdapter? = null
    private var cityFilterAdapter: CityFilterAdapter? = null
    private var cityFilterDialog: BottomSheetDialog? = null
    private var cityDialogBinding: LayoutCityFilterSheetBinding? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initializeRecyclerView()

        cityFilterDialog = BottomSheetDialog(requireContext())
        cityDialogBinding = LayoutCityFilterSheetBinding.inflate(layoutInflater)

        cityFilterAdapter = CityFilterAdapter(requireContext()) { viewModel.updateCityFilter(it) }
        cityDialogBinding!!.lvCityFilters.adapter = cityFilterAdapter
        cityFilterDialog!!.setContentView(cityDialogBinding!!.root)


        binding.btnFilterCities.setOnClickListener {
            val cityData = viewModel.cities.value
            if (cityData == null || cityData.isNotEmpty() || cityData.any { it.present }) cityFilterDialog!!.show()
            else Toast.makeText(requireContext(), "No city present or selected", Toast.LENGTH_SHORT).show()
            binding.cvFilterCountries.visibility = View.GONE
        }

        viewModel.residents.observe(viewLifecycleOwner) {
            binding.swipeRefreshLayout.isRefreshing = false
            rvAdapter!!.setData(it)
        }

        initSwipeRefreshLayoutState()

        binding.btnFilterCountries.setOnClickListener { openCountriesFilter() }

        countryFilterAdapter = CountryFilterAdapter(requireContext()) { viewModel.updateCountryFilter(it) }
        binding.lvFilterCountries.adapter = countryFilterAdapter


        viewModel.countries.observe(viewLifecycleOwner) { list ->
            Log.i("LTS_TAG", list.joinToString { it.country.name + " - - - " + it.selected.toString() })
            countryFilterAdapter!!.setData(list)
        }

        viewModel.cities.observe(viewLifecycleOwner) {
            cityFilterAdapter!!.setData(it)
        }
    }

    private fun initSwipeRefreshLayoutState() {

        binding.swipeRefreshLayout.setOnRefreshListener {
            binding.swipeRefreshLayout.isRefreshing = true
            viewModel.fetchRemoteData()
        }
        viewModel.apiState.observe(viewLifecycleOwner) {
            when (it) {
                is Resource.Error -> {
                    if (it.exception is ConnectException) Toast.makeText(requireContext(), "No Network Connection", Toast.LENGTH_SHORT).show()
                    else Toast.makeText(requireContext(), "Network Error", Toast.LENGTH_SHORT).show()
                    binding.swipeRefreshLayout.isRefreshing = false
                }
                is Resource.Success -> binding.swipeRefreshLayout.isRefreshing = false
                else -> Unit
            } //   Success state is called when the data is fetched from the local database, only a few ms difference, but more logical.
        }
    }

    private fun openCountriesFilter() {
        binding.cvFilterCountries.visibility = View.VISIBLE
        binding.bottomBarView.setOnClickListener { binding.cvFilterCountries.visibility = View.GONE }
        binding.clCountryFilterDown.setOnClickListener { binding.cvFilterCountries.visibility = View.GONE }
    }


    private fun initializeRecyclerView() {
        rvAdapter = ResidentCardsAdapter()
        binding.rvResidents.apply {
            adapter = rvAdapter
            layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
        }
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        cityFilterDialog?.dismiss()
        rvAdapter = null
        countryFilterAdapter = null
        cityFilterAdapter = null
        cityFilterDialog = null
        cityDialogBinding = null
        _binding = null
    }
}