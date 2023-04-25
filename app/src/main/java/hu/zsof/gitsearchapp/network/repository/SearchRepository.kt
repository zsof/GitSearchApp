package hu.zsof.gitsearchapp.network.repository

import hu.zsof.gitsearchapp.network.ApiService
import hu.zsof.gitsearchapp.network.model.SearchResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException
import javax.inject.Inject

class SearchRepository @Inject constructor(private val apiService: ApiService) {

    suspend fun getSearchResult(query: String): Resource<SearchResponse>{
       /* return try {
            apiService.getRepositories(query)
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }*/
        return safeApiCall { apiService.getRepositories(query) }
    }

    suspend fun <T> safeApiCall(apiToBeCalled: suspend () -> Response<T>): Resource<T> {
        // Returning api response wrapped in Resource class
        return withContext(Dispatchers.IO) {
            try {
                val response: Response<T> = apiToBeCalled()
                Resource.Success(data = response.body()!!)
            } catch (e: HttpException) {
                // Returning HttpException's message wrapped in Resource.Error
                Resource.Error(errorMessage = e.message ?: "Something went wrong")
            } catch (e: IOException) {
                // Returning no internet message wrapped in Resource.Error
                Resource.Error("Please check your network connection")
            } catch (e: Exception) {
                // Returning 'Something went wrong' in case of unknown error wrapped in Resource.Error
                Resource.Error(errorMessage = "Something went wrong")
            }
        }
    }
}
