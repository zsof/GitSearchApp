package hu.zsof.gitsearchapp.module

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import hu.zsof.gitsearchapp.BuildConfig
import hu.zsof.gitsearchapp.network.ApiService
import hu.zsof.gitsearchapp.network.model.ProjectData
import hu.zsof.gitsearchapp.network.repository.Resource
import hu.zsof.gitsearchapp.util.Constants
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.HttpException
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.io.IOException
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class InjectModule {

    @Singleton
    @Provides
    operator fun invoke(): ApiService {
        val interceptor = HttpLoggingInterceptor()
        if (BuildConfig.DEBUG) {
            interceptor.level = HttpLoggingInterceptor.Level.BODY
        } else {
            interceptor.level = HttpLoggingInterceptor.Level.BODY
        }

        val okHttpClient = OkHttpClient.Builder()
            .connectTimeout(15, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .addInterceptor(interceptor)
            .build()

        val retrofit =
            Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(MoshiConverterFactory.create())
                .client(okHttpClient)
                .build()

        return retrofit.create(ApiService::class.java)
    }
}

object NetworkWrapper {
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

object LocalData {
    var searchItem = ProjectData()
}
