package hu.zsof.gitsearchapp.ui.detail

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import dagger.hilt.android.AndroidEntryPoint
import hu.zsof.gitsearchapp.databinding.FragmentDeatilsBinding
import hu.zsof.gitsearchapp.module.LocalData
import java.text.SimpleDateFormat
import java.util.*

@AndroidEntryPoint
class DetailsFragment : Fragment() {
    private lateinit var binding: FragmentDeatilsBinding
    private val viewModel: DetailsViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentDeatilsBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupBindings()
    }

    @SuppressLint("SimpleDateFormat")
    private fun setupBindings() {
        binding.apply {
            val item = LocalData.searchItem

            val formatStringToDate = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'")
            val lastUpdate: Date = item.updateDate?.let { formatStringToDate.parse(it) } as Date
            val createDate: Date = item.createDate?.let { formatStringToDate.parse(it) } as Date
            val formatter = SimpleDateFormat("yyyy-MM-dd HH:mm")

            detailsOwnerName.text = item.owner.ownerName
            detailsOwnerLink.text = item.owner.ownerLink
            detailsRepositoryName.text = item.repositoryName
            detailsRepositoryDesc.text = item.desc
            detailsRepositoryLink.text = item.projectLink
            detailsRepositoryFork.text = item.forkNumber.toString()
            detailsRepositoryStars.text = item.starNumber.toString()
            detailsRepositoryCreate.text = formatter.format(createDate)
            detailsRepositoryUpdate.text = formatter.format(lastUpdate)

            Glide.with(requireContext()).load(item.owner.avatarUrl).into(detailsAvatar)

            detailsRepositoryLink.setOnClickListener {
                val defaultBrowse =
                    Intent.makeMainSelectorActivity(Intent.ACTION_MAIN, Intent.CATEGORY_APP_BROWSER)
                defaultBrowse.data = Uri.parse(item.projectLink)
                startActivity(defaultBrowse)
            }
            detailsOwnerLink.setOnClickListener {
                val defaultBrowse =
                    Intent.makeMainSelectorActivity(Intent.ACTION_MAIN, Intent.CATEGORY_APP_BROWSER)
                defaultBrowse.data = Uri.parse(item.owner.ownerLink)
                startActivity(defaultBrowse)
            }
        }
    }
}
