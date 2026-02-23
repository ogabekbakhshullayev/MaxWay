package uz.gita.maxwayappclone.domain.usecase.impl

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import uz.gita.maxwayappclone.domain.repository.AuthRepository
import uz.gita.maxwayappclone.domain.usecase.VerifyUseCase

class VerifyUseCaseImpl(private val repository: AuthRepository) : VerifyUseCase {
    override fun invoke(
        phone: String,
        code: Int
    ): Flow<Result<String>> = flow {
        emit(repository.verify(phone, code))
    }.catch { emit(Result.failure(it)) }.flowOn(Dispatchers.IO)
}