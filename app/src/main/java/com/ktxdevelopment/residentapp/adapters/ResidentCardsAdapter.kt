package com.ktxdevelopment.residentapp.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.ktxdevelopment.domain.model.ResidentModel
import com.ktxdevelopment.residentapp.databinding.ResidentCardBinding

class ResidentCardsAdapter : RecyclerView.Adapter<ResidentCardsAdapter.ResidentCardViewHolder>() {  // add DiffUtil


    inner class ResidentCardViewHolder(var binding: ResidentCardBinding) : ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ResidentCardViewHolder {
        val binding = ResidentCardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ResidentCardViewHolder(binding)
    }

    override fun getItemCount(): Int = asyncListDiffer.currentList.size ?: 0

    override fun onBindViewHolder(holder: ResidentCardViewHolder, position: Int) {
        val item = asyncListDiffer.currentList[position]
        holder.binding.tvResidentName.text = item.name + " " + item.surname
    }

    fun setData(list: List<ResidentModel>) {
        var data = asyncListDiffer.submitList(list)
    }

    private val diffUtil = object : DiffUtil.ItemCallback<ResidentModel>() {
        override fun areItemsTheSame(oldItem: ResidentModel, newItem: ResidentModel): Boolean {
            return oldItem.humanId == newItem.humanId
        }

        override fun areContentsTheSame(oldItem: ResidentModel, newItem: ResidentModel): Boolean {
            return oldItem == newItem
        }
    }

    private val asyncListDiffer = AsyncListDiffer(this, diffUtil)

}