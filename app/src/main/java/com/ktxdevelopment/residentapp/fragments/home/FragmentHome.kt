package com.ktxdevelopment.residentapp.fragments.home

import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ktxdevelopment.residentapp.adapters.ResidentCardsAdapter
import com.ktxdevelopment.residentapp.databinding.FragmentHomeBinding

class FragmentHome : Fragment() {

    companion object {
        fun newInstance() = FragmentHome()
    }

    private val viewModel: FragmentHomeViewModel by viewModels()
    private lateinit var binding: FragmentHomeBinding

    private lateinit var rvAdapter: ResidentCardsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initializeRecyclerView()


        binding.swipeRefreshLayout.setOnRefreshListener {
            binding.swipeRefreshLayout.isRefreshing = true
            viewModel.fetchRemoteData()
        }

        viewModel.residents.observe(this) {
            binding.swipeRefreshLayout.isRefreshing = false
            rvAdapter.setData(it)
        }

        viewModel.selectedCities
        viewModel.selectedCountries

    }

    private fun initializeRecyclerView() {
        rvAdapter = ResidentCardsAdapter()
        binding.rvResidents.apply {
            adapter = rvAdapter
            layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
        }
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }
}