package uz.gita.maxwayappclone.data.repository_impl

import com.google.gson.Gson
import uz.gita.maxwayappclone.data.mapper.toData
import uz.gita.maxwayappclone.data.model.OrdersUIData
import uz.gita.maxwayappclone.data.source.local.TokenManager
import uz.gita.maxwayappclone.data.source.remote.ApiClient
import uz.gita.maxwayappclone.data.source.remote.api.ProductApi
import uz.gita.maxwayappclone.data.source.remote.response.ErrorMessageResponse
import uz.gita.maxwayappclone.domain.repository.OrdersRepository

class OrdersRepositoryImpl private constructor(
	private val productApi: ProductApi,
	private val gson: Gson
): OrdersRepository {

	override suspend fun getOrders(): Result<List<OrdersUIData>> {
		val response = productApi.getOrders(TokenManager.token)
		return if (response.isSuccessful && response.body() != null) {
			val orders = response.body()!!.data.toData()
			Result.success(orders)
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

	companion object {
		private lateinit var instance: OrdersRepository

		fun getInstance(): OrdersRepository {
			if (!::instance.isInitialized)
				instance = OrdersRepositoryImpl(ApiClient.productApi, Gson())

			return instance
		}
	}
}