package hu.zsof.gitsearchapp.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import hu.zsof.gitsearchapp.R
import hu.zsof.gitsearchapp.databinding.ItemSearchBinding
import hu.zsof.gitsearchapp.network.model.ProjectData
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

class SearchAdapter @Inject constructor() : RecyclerView.Adapter<SearchAdapter.ListViewHolder>() {
    var searchList: List<ProjectData>
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

    private val diffCallback = object : DiffUtil.ItemCallback<ProjectData>() {
        override fun areItemsTheSame(oldItem: ProjectData, newItem: ProjectData): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: ProjectData, newItem: ProjectData): Boolean {
            return oldItem.hashCode() == newItem.hashCode()
        }
    }

    private val differ = AsyncListDiffer(this, diffCallback)

    inner class ListViewHolder(private val binding: ItemSearchBinding) :
        RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SimpleDateFormat")
        fun bind(result: ProjectData) {
            val formatStringToDate = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'")
            val lastUpdate: Date = formatStringToDate.parse(result.updateDate) as Date
            val formatter = SimpleDateFormat("yyyy-MM-dd HH:mm")

            binding.name.text = result.name
            binding.description.text = result.desc
            binding.lastUpdate.text = formatter.format(lastUpdate)
            binding.starsNumber.text = result.starNumber.toString()
        }
    }
}
