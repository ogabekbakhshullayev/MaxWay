package uz.gita.maxwayappclone.data.repository_impl

import com.google.gson.Gson
import uz.gita.maxwayappclone.data.source.remote.ApiClient
import uz.gita.maxwayappclone.data.source.remote.api.NotificationApi
import uz.gita.maxwayappclone.data.source.remote.response.ErrorMessageResponse
import uz.gita.maxwayappclone.data.source.remote.response.NotificationResponse
import uz.gita.maxwayappclone.domain.repository.NotificationRepository

class NotificationRepositoryImpl private constructor(
    private val notificationApi: NotificationApi,
    private val gson: Gson): NotificationRepository{


        companion object{
            private lateinit var instance: NotificationRepository
            fun init(){
                if (!(::instance.isInitialized)){
                    instance = NotificationRepositoryImpl(ApiClient.notificationApi, Gson())
                }
            }
            fun getInstance(): NotificationRepository{
                return instance
            }
        }


    override suspend fun getAllNotifications(): Result<List<NotificationResponse>> {
        val response = notificationApi.getNotification()

        return if (response.isSuccessful && response.body() != null) {
            // response.body() GeneralResponse
            Result.success(response.body()!!.data) // <-- faqat data fieldni oling
        } else {
            val errorJson = response.errorBody()?.string()
            if (errorJson.isNullOrEmpty()) {
                Result.failure(Throwable("Unknown exception"))
            } else {
                val errorMessage = gson.fromJson(errorJson, ErrorMessageResponse::class.java)
                Result.failure(Throwable(errorMessage.message))
            }
        }
    }


//    override suspend fun getAllNotifications(): Result<List<NotificationResponse>> {
//
//        val response = notificationApi.getNotification()
//
//        return (if (response.isSuccessful && response.body() != null){
//            Result.success(response.body())
//        } else{
//            val errorJson = response.errorBody()?.string()
//            if (errorJson.isNullOrEmpty()) Result.failure(Throwable("Unknown exseption"))
//            else{
//                val errorMassage = gson.fromJson(errorJson, ErrorMessageResponse::class.java)
//                Result.failure(Throwable(errorMassage.message))
//            }
//        }) as Result<List<NotificationResponse>>
//
//    }
}