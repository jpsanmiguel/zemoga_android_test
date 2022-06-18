package sanmi.labs.zemogaandroidtest.network

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import okhttp3.logging.HttpLoggingInterceptor.Level
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import sanmi.labs.zemogaandroidtest.network.dto.CommentDTO
import sanmi.labs.zemogaandroidtest.network.dto.PostDTO

interface PostService {
    @GET("posts")
    suspend fun getPosts(): List<PostDTO>

    @GET("posts/{id}/comments")
    suspend fun getPostComments(@Path("id") id: Long): List<CommentDTO>

    companion object {
        private const val BASE_URL = "https://jsonplaceholder.typicode.com/"

        fun create(): PostService {
            val logger = HttpLoggingInterceptor()
            logger.level = Level.BASIC

            val client = OkHttpClient.Builder()
                .addInterceptor(logger)
                .build()

            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(PostService::class.java)
        }
    }
}