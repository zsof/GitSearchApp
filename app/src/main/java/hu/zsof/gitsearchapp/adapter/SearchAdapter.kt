package hu.zsof.gitsearchapp.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import hu.zsof.gitsearchapp.databinding.ItemSearchBinding
import hu.zsof.gitsearchapp.network.model.ProjectData
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

class SearchAdapter @Inject constructor(private val searchList: List<ProjectData>) :
    RecyclerView.Adapter<SearchAdapter.ListViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val binding = ItemSearchBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return ListViewHolder(binding)
    }

    @SuppressLint("SimpleDateFormat")
    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        with(holder) {
            with(searchList[position]) {
                val formatStringToDate = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'")
                val lastUpdate: Date = formatStringToDate.parse(updateDate) as Date
                val formatter = SimpleDateFormat("yyyy-MM-dd HH:mm")

                binding.name.text = name
                binding.description.text = desc
                binding.lastUpdate.text = formatter.format(lastUpdate)
                binding.starsNumber.text = starNumber.toString()
            }
        }
    }

    override fun getItemCount() = searchList.size
    inner class ListViewHolder(val binding: ItemSearchBinding) :
        RecyclerView.ViewHolder(binding.root)
}
