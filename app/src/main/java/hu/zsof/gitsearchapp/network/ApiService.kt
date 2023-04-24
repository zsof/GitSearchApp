package hu.zsof.gitsearchapp.network

import hu.zsof.gitsearchapp.network.response.RepositoryResultResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("/search/repositories")
    suspend fun getRepositories(@Query("q") query: String): RepositoryResultResponse
}
