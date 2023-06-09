package hu.zsof.gitsearchapp.ui.search

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import hu.zsof.gitsearchapp.R
import hu.zsof.gitsearchapp.adapter.SearchAdapter
import hu.zsof.gitsearchapp.databinding.FragmentSearchBinding
import hu.zsof.gitsearchapp.network.wrapper.ResultWrapper
import hu.zsof.gitsearchapp.util.Constants
import hu.zsof.gitsearchapp.util.hideKeyboard
import hu.zsof.gitsearchapp.util.showToast
import kotlin.math.ceil

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
        binding = FragmentSearchBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupBindings()
        subscribeToObservers()
        setupAdapter()
    }

    private fun setupAdapter() {
        viewModel.searchResult.observe(viewLifecycleOwner) { result ->
            searchAdapter = SearchAdapter(result.data?.items ?: mutableListOf())
            binding.recyclerSearchItem.adapter = searchAdapter
        }
    }

    private fun setupBindings() {
        if ((viewModel.searchResult.value?.data?.totalCount ?: 0) == 0) {
            arrowButtonsInvisible()
            Log.d("SearchFragment", "No data available")
        }

        binding.searchTextInput.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                search()
                this@SearchFragment.hideKeyboard()
                return@setOnEditorActionListener true
            }
            return@setOnEditorActionListener false
        }

        binding.searchButton.setOnClickListener {
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
            viewModel.currentPageNumber++
            search(viewModel.currentPageNumber)
        }

        binding.prevButton.setOnClickListener {
            viewModel.currentPageNumber--
            search(viewModel.currentPageNumber)
        }
    }

    private fun search(page: Int = 0) {
        viewModel.search(binding.searchTextInput.text.toString(), page)
    }

    private fun subscribeToObservers() {
        viewModel.searchResult.observe(viewLifecycleOwner) {
            when (it) {
                is ResultWrapper.Loading -> {
                    arrowButtonsInvisible()

                    binding.animationView.visibility = View.VISIBLE
                    binding.animationView.playAnimation()
                }
                is ResultWrapper.Success -> {
                    binding.animationView.visibility = View.GONE
                    binding.animationView.pauseAnimation()

                    if (it.data?.totalCount == 0) {
                        showToast(
                            getString(R.string.no_data),
                        )
                        arrowButtonsInvisible()
                    } else {
                        val totalItems = it.data?.totalCount!!
                        viewModel.totalPageNumber =
                            ceil(totalItems / Constants.RESULT_PER_PAGE.toDouble()).toInt()

                        if (viewModel.currentPageNumber == viewModel.totalPageNumber) {
                            binding.nextButton.setImageResource(R.drawable.ic_next_disable)
                            binding.nextButton.isEnabled = false
                        } else {
                            binding.nextButton.setImageResource(R.drawable.ic_next)
                            binding.nextButton.isEnabled = true
                        }

                        if (viewModel.currentPageNumber == 1) {
                            binding.prevButton.setImageResource(R.drawable.ic_prev_disable)
                            binding.prevButton.isEnabled = false
                        } else {
                            binding.prevButton.setImageResource(R.drawable.ic_prev)
                            binding.prevButton.isEnabled = true
                        }
                    }
                }
                is ResultWrapper.Error -> {
                    arrowButtonsInvisible()

                    binding.animationView.visibility = View.GONE
                    binding.animationView.pauseAnimation()
                    showToast(it.message)
                }
            }
        }
    }

    private fun arrowButtonsInvisible() {
        binding.prevButton.setImageResource(R.drawable.ic_prev_disable)
        binding.prevButton.isEnabled = false

        binding.nextButton.setImageResource(R.drawable.ic_next_disable)
        binding.nextButton.isEnabled = false
    }
}
