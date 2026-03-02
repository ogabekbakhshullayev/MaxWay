package uz.gita.maxwayappclone.domain.usecase

import kotlinx.coroutines.flow.Flow
import uz.gita.maxwayappclone.data.source.remote.response.EditProfileResponse

interface EditProfileDialogUseCase{

    operator fun invoke(token: String, name: String, birthDate: String): Flow<Result<String>>

}
