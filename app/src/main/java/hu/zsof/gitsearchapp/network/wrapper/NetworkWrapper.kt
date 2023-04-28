package hu.zsof.gitsearchapp.network.wrapper

import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException

object NetworkWrapper {
    suspend fun <T> safeApiCall(apiToBeCalled: suspend () -> Response<T>): ResultWrapper<T> {
        // Returning api response wrapped in ResultWrapper class
        return withContext(Dispatchers.IO) {
            try {
                Log.d("Network", "Api call - success")
                val response: Response<T> = apiToBeCalled()
                ResultWrapper.Success(data = response.body()!!)
            } catch (e: HttpException) {
                // Returning HttpException's message wrapped in ResultWrapper.Error
                Log.d("Network", "Api call - Http error")
                ResultWrapper.Error(errorMessage = e.message ?: "Something went wrong")
            } catch (e: IOException) {
                // Returning no internet message wrapped in ResultWrapper.Error
                Log.d("Network", "Api call - Network error")

                ResultWrapper.Error("Please check your network connection")
            } catch (e: Exception) {
                // Returning 'Something went wrong' in case of unknown error wrapped in ResultWrapper.Error
                Log.d("Network", "Api call - error")

                ResultWrapper.Error(errorMessage = "Something went wrong")
            }
        }
    }
}
