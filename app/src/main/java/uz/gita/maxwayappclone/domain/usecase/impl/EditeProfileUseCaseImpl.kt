package uz.gita.maxwayappclone.domain.usecase.impl

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import uz.gita.maxwayappclone.data.source.remote.response.EditProfileResponse
import uz.gita.maxwayappclone.domain.repository.EditeProfileRepository
import uz.gita.maxwayappclone.domain.usecase.EditeProfileUseCase

class EditeProfileUseCaseImpl(private val repository: EditeProfileRepository): EditeProfileUseCase {
    override fun invoke(name: String, birthDate: String): Flow<Result<String>> = flow {
        emit(repository.updateProfileInfo(name,birthDate))
    }
        .catch { emit(Result.failure(it))}
        .flowOn(Dispatchers.IO)

    override fun invoke(token: String): Flow<Result<EditProfileResponse>> = flow {
        emit(repository.getProfileInfo(token))
    }
        .catch { emit(Result.failure(it)) }
        .flowOn(Dispatchers.IO)

}