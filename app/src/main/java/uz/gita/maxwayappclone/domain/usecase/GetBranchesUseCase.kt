package uz.gita.maxwayappclone.domain.usecase

import kotlinx.coroutines.flow.Flow
import uz.gita.maxwayappclone.domain.model.Branch

interface GetBranchesUseCase {
    operator fun invoke(): Flow<Result<List<Branch>>>
}
