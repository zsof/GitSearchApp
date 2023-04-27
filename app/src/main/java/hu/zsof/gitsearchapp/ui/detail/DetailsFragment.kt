package hu.zsof.gitsearchapp.ui.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import hu.zsof.gitsearchapp.adapter.SearchAdapter
import hu.zsof.gitsearchapp.databinding.FragmentDeatilsBinding
import hu.zsof.gitsearchapp.databinding.FragmentSearchBinding
import hu.zsof.gitsearchapp.ui.search.SearchViewModel

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

    private fun setupBindings() {
        TODO("Not yet implemented")
    }
}
