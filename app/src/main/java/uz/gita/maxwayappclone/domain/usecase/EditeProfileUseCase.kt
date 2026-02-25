package uz.gita.maxwayappclone.domain.usecase

import kotlinx.coroutines.flow.Flow
import uz.gita.maxwayappclone.data.source.remote.response.EditProfileResponse

interface EditeProfileUseCase {

    operator fun invoke(name: String, birthDate: String): Flow<Result<String>>
    operator fun invoke(token: String): Flow<Result<EditProfileResponse>>
}