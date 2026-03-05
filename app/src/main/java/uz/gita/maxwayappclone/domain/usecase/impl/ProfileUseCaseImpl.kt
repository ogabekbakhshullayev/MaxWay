package uz.gita.maxwayappclone.domain.usecase.impl

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import uz.gita.maxwayappclone.data.source.local.TokenManager
import uz.gita.maxwayappclone.data.source.remote.response.EditProfileResponse
import uz.gita.maxwayappclone.domain.repository.EditeProfileRepository
import uz.gita.maxwayappclone.domain.usecase.ProfileUseCase

class ProfileUseCaseImpl(private val repository: EditeProfileRepository):
    ProfileUseCase {

    override fun invoke(): Flow<Result<EditProfileResponse>> = flow {
        if (TokenManager.token.isNotEmpty()){
        emit(repository.getProfileInfo(TokenManager.token))}
    }
        .catch { emit(Result.failure(it)) }
        .flowOn(Dispatchers.IO)
}