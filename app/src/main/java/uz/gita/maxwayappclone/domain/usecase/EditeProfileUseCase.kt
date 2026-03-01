package uz.gita.maxwayappclone.domain.usecase

import kotlinx.coroutines.flow.Flow
import uz.gita.maxwayappclone.data.source.remote.response.EditProfileResponse

interface EditeProfileUseCase {
    operator fun invoke(token: String): Flow<Result<EditProfileResponse>>


}