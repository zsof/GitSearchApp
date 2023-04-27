package hu.zsof.gitsearchapp.ui.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import hu.zsof.gitsearchapp.R
import hu.zsof.gitsearchapp.adapter.SearchAdapter
import hu.zsof.gitsearchapp.databinding.FragmentSearchBinding
import hu.zsof.gitsearchapp.network.repository.Resource
import hu.zsof.gitsearchapp.util.Constants
import hu.zsof.gitsearchapp.util.hideKeyboard
import hu.zsof.gitsearchapp.util.showToast
import kotlin.math.ceil

@AndroidEntryPoint
class SearchFragment : Fragment() {

    private lateinit var binding: FragmentSearchBinding
    private lateinit var searchAdapter: SearchAdapter

    private val viewModel: SearchViewModel by viewModels()
    private var currentPageNumber = 1
    private var totalPageNumber = 0
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentSearchBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupBindings()
        subscribeToObservers()
    }

    override fun onResume() {
        super.onResume()

        currentPageNumber = 1
        arrowButtonsInvisible()
        binding.searchTextInput.setText("")
    }

    private fun setupBindings() {
        binding.searchButton.setOnClickListener {
            arrowButtonsInvisible()

            if (binding.searchTextInput.text.isNullOrEmpty()) {
                showToast(
                    getString(R.string.give_parameter),
                )
            } else {
                search()
                this@SearchFragment.hideKeyboard()
            }
        }

        binding.nextButton.setOnClickListener {
            currentPageNumber++
            search(currentPageNumber)

            binding.prevButton.visibility = View.VISIBLE
        }

        binding.prevButton.setOnClickListener {
            currentPageNumber--
            search(currentPageNumber)

            binding.prevButton.visibility =
                if (currentPageNumber == 1) View.INVISIBLE else View.VISIBLE
        }
    }

    private fun search(page: Int = 0) {
        viewModel.search(binding.searchTextInput.text.toString(), page)
        viewModel.searchResult.observe(viewLifecycleOwner) { result ->
            searchAdapter = SearchAdapter(result.data?.items ?: mutableListOf())
            binding.recyclerSearchItem.adapter = searchAdapter
        }
    }

    private fun subscribeToObservers() {
        viewModel.searchResult.observe(viewLifecycleOwner) {
            when (it) {
                is Resource.Loading -> {
                    binding.animationView.visibility = View.VISIBLE
                    binding.animationView.playAnimation()
                }
                is Resource.Success -> {
                    binding.animationView.visibility = View.GONE
                    binding.animationView.pauseAnimation()

                    if (it.data?.totalCount == 0) {
                        showToast(
                            getString(R.string.no_data),
                        )
                        arrowButtonsInvisible()
                    } else {
                        val totalItems = it.data?.totalCount!!
                        totalPageNumber =
                            ceil(totalItems / Constants.RESULT_PER_PAGE.toDouble()).toInt()

                        binding.nextButton.visibility =
                            if (currentPageNumber == totalPageNumber) View.INVISIBLE else View.VISIBLE
                    }
                }
                is Resource.Error -> {
                    showToast(it.message)
                    arrowButtonsInvisible()
                }
            }
        }
    }

    private fun arrowButtonsInvisible() {
        binding.prevButton.visibility = View.INVISIBLE
        binding.nextButton.visibility = View.INVISIBLE
    }
}
