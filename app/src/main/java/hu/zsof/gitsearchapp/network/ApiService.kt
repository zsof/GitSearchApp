package hu.zsof.gitsearchapp.network

import hu.zsof.gitsearchapp.network.data.SearchResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("/search/repositories?sort=stars&order=desc")
    suspend fun getRepositories(
        @Query("q") query: String,
        @Query("page") page: Int,
        @Query("per_page") perPage: Int,
    ): Response<SearchResponse>
}
