package hu.zsof.gitsearchapp.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import hu.zsof.gitsearchapp.databinding.ItemSearchBinding
import hu.zsof.gitsearchapp.network.data.LocalData
import hu.zsof.gitsearchapp.network.data.ProjectData
import hu.zsof.gitsearchapp.ui.search.SearchFragmentDirections
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
                val lastUpdate: Date = updateDate?.let { formatStringToDate.parse(it) } as Date
                val formatter = SimpleDateFormat("yyyy-MM-dd HH:mm")

                binding.repositoryName.text = repositoryName
                binding.description.text = desc
                binding.lastUpdate.text = formatter.format(lastUpdate)
                binding.starsNumber.text = starNumber.toString()

                itemView.setOnClickListener {
                    LocalData.searchItem = searchList[position]
                    val action = SearchFragmentDirections.actionSearchListFrToDetailsFr()
                    itemView.findNavController().navigate(action)
                }
            }
        }
    }

    override fun getItemCount() = searchList.size
    inner class ListViewHolder(val binding: ItemSearchBinding) :
        RecyclerView.ViewHolder(binding.root)
}
