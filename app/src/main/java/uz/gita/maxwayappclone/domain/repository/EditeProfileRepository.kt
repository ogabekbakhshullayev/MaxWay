package uz.gita.maxwayappclone.domain.repository

import uz.gita.maxwayappclone.data.source.remote.response.EditProfileResponse

interface EditeProfileRepository {

    suspend fun getProfileInfo(token: String): Result<EditProfileResponse>

    suspend fun updateProfileInfo(token: String, name: String, birthDate: String): Result<String>

}