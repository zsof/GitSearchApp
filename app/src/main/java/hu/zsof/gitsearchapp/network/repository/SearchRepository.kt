package hu.zsof.gitsearchapp.network.repository

import hu.zsof.gitsearchapp.network.ApiService
import hu.zsof.gitsearchapp.network.data.SearchResponse
import hu.zsof.gitsearchapp.network.wrapper.NetworkWrapper
import hu.zsof.gitsearchapp.network.wrapper.ResultWrapper
import hu.zsof.gitsearchapp.util.Constants
import javax.inject.Inject

class SearchRepository @Inject constructor(private val apiService: ApiService) {
    suspend fun getSearchResult(query: String, page: Int): ResultWrapper<SearchResponse> {
        return NetworkWrapper.safeApiCall {
            apiService.getRepositories(
                query,
                page,
                Constants.RESULT_PER_PAGE,
            )
        }
    }
}
