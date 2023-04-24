package hu.zsof.gitsearchapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import hu.zsof.gitsearchapp.R
import hu.zsof.gitsearchapp.databinding.ItemSearchBinding
import hu.zsof.gitsearchapp.network.model.ProjectData
import hu.zsof.gitsearchapp.network.model.SearchResponse
import javax.inject.Inject

class SearchAdapter @Inject constructor() : RecyclerView.Adapter<SearchAdapter.ListViewHolder>() {
    var searchList: List<SearchResponse>
        get() = differ.currentList
        set(value) {
            differ.submitList(value)
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val inflater = LayoutInflater.from(parent.context)

        val binding: ItemSearchBinding =
            DataBindingUtil.inflate(inflater, R.layout.item_search, parent, false)

        return ListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        return holder.bind(searchList[position])
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    override fun getItemCount() = searchList.size

    private val diffCallback = object : DiffUtil.ItemCallback<SearchResponse>() {
        override fun areItemsTheSame(oldItem: SearchResponse, newItem: SearchResponse): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: SearchResponse, newItem: SearchResponse): Boolean {
            return oldItem.hashCode() == newItem.hashCode()
        }
    }

    private val differ = AsyncListDiffer(this, diffCallback)

    inner class ListViewHolder(private val binding: ItemSearchBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(result: SearchResponse) {
            result.items.forEach {
                binding.name.text = it.name
                binding.description.text = it.desc
                binding.lastUpdate.text = it.updateDate
                binding.starsNumber.text = it.starNumber.toString()
            }
        }
    }
}
