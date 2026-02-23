package uz.gita.maxwayappclone.domain.usecase.impl

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import uz.gita.maxwayappclone.domain.repository.AuthRepository
import uz.gita.maxwayappclone.domain.usecase.NameDateUseCase

class NameDateUseCaseImpl(private val repository: AuthRepository) : NameDateUseCase {
    override fun invoke(token: String, name: String, date: String): Flow<Result<String>> = flow {
        emit(repository.nameDate(token, name, date))
    }.catch {
        emit(Result.failure(it))
    }.flowOn(Dispatchers.IO)
}