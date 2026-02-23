package uz.gita.maxwayappclone.data.source.remote

import com.chuckerteam.chucker.api.ChuckerInterceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import uz.gita.maxwayappclone.app.MyApp
import uz.gita.maxwayappclone.data.source.remote.api.AuthApi
import uz.gita.maxwayappclone.data.source.remote.api.BranchApi
import uz.gita.maxwayappclone.data.source.remote.api.NotificationApi
import uz.gita.maxwayappclone.data.source.remote.api.SearchApi

object ApiClient {

    private val okHttpClient = OkHttpClient.Builder()
        .addInterceptor(ChuckerInterceptor.Builder(MyApp.instance).build())
        .build()

    private val retrofit = Retrofit.Builder()
        .client(okHttpClient)
        .baseUrl("" +
                "" +
                "https://newsworthy-cosmological-song.ngrok-free.dev/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val authApi = retrofit.create<AuthApi>(AuthApi::class.java)
    val branchApi = retrofit.create<BranchApi>(BranchApi::class.java)
    val notificationApi = retrofit.create<NotificationApi>(NotificationApi::class.java)

    val searchApi = retrofit.create<SearchApi>(SearchApi::class.java)

}
