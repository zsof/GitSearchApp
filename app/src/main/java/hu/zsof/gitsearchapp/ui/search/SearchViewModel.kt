package hu.zsof.gitsearchapp.ui.search

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import hu.zsof.gitsearchapp.network.data.SearchResponse
import hu.zsof.gitsearchapp.network.repository.SearchRepository
import hu.zsof.gitsearchapp.network.wrapper.ResultWrapper
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(private val searchRepository: SearchRepository) :
    ViewModel() {

    var currentPageNumber = 1
    var totalPageNumber = 0

    val searchResult = MutableLiveData<ResultWrapper<SearchResponse>>()
    fun search(query: String, page: Int) {
        viewModelScope.launch {
            searchResult.postValue(ResultWrapper.Loading())
            searchResult.postValue(searchRepository.getSearchResult(query, page))
        }
    }
}
