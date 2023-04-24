package hu.zsof.gitsearchapp.network.repository

import hu.zsof.gitsearchapp.network.ApiService
import hu.zsof.gitsearchapp.network.model.SearchResponse
import javax.inject.Inject

class SearchRepository @Inject constructor(private val apiService: ApiService) {

    suspend fun getSearchResult(query: String): List<SearchResponse> {
        return try {
            apiService.getRepositories(query)
        } catch (e: Exception) {
            e.printStackTrace()
            mutableListOf()
        }
    }
}
