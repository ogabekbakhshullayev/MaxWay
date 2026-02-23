package uz.gita.maxwayappclone.domain.usecase.impl

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import uz.gita.maxwayappclone.domain.model.Branch
import uz.gita.maxwayappclone.domain.repository.BranchRepository
import uz.gita.maxwayappclone.domain.usecase.GetBranchesUseCase

class GetBranchesUseCaseImpl(
    private val repository: BranchRepository
) : GetBranchesUseCase {

    override fun invoke(): Flow<Result<List<Branch>>> = flow {
        emit(repository.getBranches())
    }
        .catch { emit(Result.failure(it)) }
        .flowOn(Dispatchers.IO)
}
