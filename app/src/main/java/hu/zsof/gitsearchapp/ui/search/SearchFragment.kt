package hu.zsof.gitsearchapp.ui.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import hu.zsof.gitsearchapp.R
import hu.zsof.gitsearchapp.adapter.SearchAdapter
import hu.zsof.gitsearchapp.databinding.FragmentSearchBinding
import hu.zsof.gitsearchapp.network.repository.Resource

@AndroidEntryPoint
class SearchFragment : Fragment() {

    private lateinit var binding: FragmentSearchBinding
    private lateinit var searchAdapter: SearchAdapter
    private val viewModel: SearchViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_search, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        searchAdapter = SearchAdapter()
        setupBindings()
    }

    private fun setupBindings() {
        binding.apply {
            searchButton.setOnClickListener {
                viewModel.search(searchTextInput.text.toString())
                viewModel.searchResult.observe(viewLifecycleOwner) { result ->
                    searchAdapter.searchList = result.data?.items ?: mutableListOf()
                    recyclerSearchItem.adapter = searchAdapter
                }
            }

            viewModel.searchResult.observe(viewLifecycleOwner) {
                when (it) {
                    is Resource.Loading -> {
                        animationView.visibility = View.VISIBLE
                        animationView.playAnimation()
                    }
                    is Resource.Success -> {
                        animationView.visibility = View.GONE
                        animationView.pauseAnimation()
                        if (it.data?.totalCount == 0) {
                            Toast.makeText(
                                requireContext(),
                                getString(R.string.no_data),
                                Toast.LENGTH_SHORT,
                            ).show()
                        }
                    }
                    is Resource.Error -> {
                        Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }
}
