package com.ktxdevelopment.residentapp.fragments.home

import androidx.fragment.app.viewModels
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.PopupMenu
import androidx.core.os.postDelayed
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ktxdevelopment.residentapp.R
import com.ktxdevelopment.residentapp.adapters.ResidentCardsAdapter
import com.ktxdevelopment.residentapp.databinding.FragmentHomeBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class FragmentHome : Fragment() {

    companion object {
        fun newInstance() = FragmentHome()
    }

    private val viewModel: FragmentHomeViewModel by viewModels()
    private lateinit var binding: FragmentHomeBinding

    private lateinit var rvAdapter: ResidentCardsAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initializeRecyclerView()


        binding.swipeRefreshLayout.setOnRefreshListener {
            binding.swipeRefreshLayout.isRefreshing = true
            viewModel.fetchRemoteData()
        }

        viewModel.residents.observe(viewLifecycleOwner) {
            binding.swipeRefreshLayout.isRefreshing = false
            rvAdapter.setData(it)
        }

        viewModel.countr

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








    fun showFilterDialog(view: View) {
        val optionsArray = arrayOf("Filter 1", "Filter 2", "Filter 3")
        val checkedItems = booleanArrayOf(false, false, false)

        val builder = AlertDialog.Builder(this)
        builder.setTitle("Select Filters")

        builder.setMultiChoiceItems(optionsArray, checkedItems) { dialog, which, isChecked ->
            // Handle the clicked item
        }

        builder.show()
    }

}