package hu.zsof.gitsearchapp.ui.detail

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import hu.zsof.gitsearchapp.network.repository.SearchRepository
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(private val searchRepository: SearchRepository) : ViewModel()
