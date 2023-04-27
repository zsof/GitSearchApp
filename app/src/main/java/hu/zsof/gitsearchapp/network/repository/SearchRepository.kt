package hu.zsof.gitsearchapp.network.repository

import hu.zsof.gitsearchapp.module.NetworkWrapper
import hu.zsof.gitsearchapp.network.ApiService
import hu.zsof.gitsearchapp.network.model.SearchResponse
import hu.zsof.gitsearchapp.util.Constants
import javax.inject.Inject

class SearchRepository @Inject constructor(private val apiService: ApiService) {

    suspend fun getSearchResult(query: String, page: Int): Resource<SearchResponse> {
        return NetworkWrapper.safeApiCall { apiService.getRepositories(query, page, Constants.RESULT_PER_PAGE) }
    }
}
