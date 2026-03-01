package uz.gita.maxwayappclone.data.source.remote

import com.chuckerteam.chucker.api.ChuckerInterceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import uz.gita.maxwayappclone.app.MyApp
import uz.gita.maxwayappclone.data.source.remote.api.AuthApi
import uz.gita.maxwayappclone.data.source.remote.api.StoryApi
import uz.gita.maxwayappclone.data.source.remote.api.BranchApi
import uz.gita.maxwayappclone.data.source.remote.api.ProductApi
import uz.gita.maxwayappclone.data.source.remote.api.NotificationApi
import uz.gita.maxwayappclone.data.source.remote.api.EditeProfileApi
import uz.gita.maxwayappclone.data.source.remote.api.SearchApi

object ApiClient {
    private val okHttpClient = OkHttpClient.Builder()
        .addInterceptor { chain ->
            val request = chain.request().newBuilder()
                .addHeader("ngrok-skip-browser-warning", "true")
                .addHeader("Accept", "application/json")
                .build()
            chain.proceed(request)
        }
        .addInterceptor(ChuckerInterceptor.Builder(MyApp.context).build())
        .build()

    private val retrofit = Retrofit.Builder()
        .client(okHttpClient)
        .baseUrl("" +
                "" +
                "https://superzealously-frumentaceous-illa.ngrok-free.dev")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val authApi = retrofit.create<AuthApi>(AuthApi::class.java)
    val storyApi = retrofit.create<StoryApi>(StoryApi::class.java)
    val productApi = retrofit.create<ProductApi>(ProductApi::class.java)
    val branchApi = retrofit.create<BranchApi>(BranchApi::class.java)
    val notificationApi = retrofit.create<NotificationApi>(NotificationApi::class.java)
    val searchApi = retrofit.create<SearchApi>(SearchApi::class.java)
    val editeProfileApi = retrofit.create<EditeProfileApi>(EditeProfileApi::class.java)
}
