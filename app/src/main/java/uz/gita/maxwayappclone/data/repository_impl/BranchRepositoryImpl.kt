package uz.gita.maxwayappclone.data.repository_impl

import com.google.gson.Gson
import uz.gita.maxwayappclone.data.mapper.toDomainList
import uz.gita.maxwayappclone.data.source.remote.ApiClient
import uz.gita.maxwayappclone.data.source.remote.api.BranchApi
import uz.gita.maxwayappclone.data.source.remote.response.ErrorMessageResponse
import uz.gita.maxwayappclone.domain.model.Branch
import uz.gita.maxwayappclone.domain.repository.BranchRepository

class BranchRepositoryImpl private constructor(
    private val branchApi: BranchApi,
    private val gson: Gson
) : BranchRepository {

    companion object {
        private lateinit var instance: BranchRepository

        fun init() {
            if (!(::instance.isInitialized)) {
                instance = BranchRepositoryImpl(ApiClient.branchApi, Gson())
            }
        }

        fun getInstance(): BranchRepository = instance
    }

    override suspend fun getBranches(): Result<List<Branch>> {
        val response = branchApi.getBranches()
        return if (response.isSuccessful && response.body() != null) {
            val branches = response.body()!!.data.toDomainList()
            Result.success(branches)
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
}
