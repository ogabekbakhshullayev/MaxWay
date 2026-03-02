package uz.gita.maxwayappclone.domain.usecase.impl

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import uz.gita.maxwayappclone.data.source.remote.response.EditProfileResponse
import uz.gita.maxwayappclone.domain.repository.EditeProfileRepository
import uz.gita.maxwayappclone.domain.usecase.EditProfileDialogUseCase
import uz.gita.maxwayappclone.domain.usecase.EditeProfileUseCase


class EditProfileDialogUseCaseImp(private val repository: EditeProfileRepository): EditProfileDialogUseCase {
    override fun invoke(token: String, name: String, birthDate: String): Flow<Result<String>> = flow {
        emit(repository.updateProfileInfo(token,name,birthDate))
    }
        .catch { emit(Result.failure(it))}
        .flowOn(Dispatchers.IO)



}