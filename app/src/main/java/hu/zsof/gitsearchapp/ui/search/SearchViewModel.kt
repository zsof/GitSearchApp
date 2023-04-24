package hu.zsof.gitsearchapp.ui.search

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import hu.zsof.gitsearchapp.network.model.SearchResponse
import hu.zsof.gitsearchapp.network.repository.SearchRepository
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(private val searchRepository: SearchRepository) :
    ViewModel() {

    val searchResult = MutableLiveData<List<SearchResponse>>()
    fun search(query: String) {
        viewModelScope.launch {
            searchResult.postValue(searchRepository.getSearchResult(query))
        }
    }
}
