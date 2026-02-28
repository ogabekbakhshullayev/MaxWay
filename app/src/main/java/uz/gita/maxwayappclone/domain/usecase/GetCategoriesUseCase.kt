package uz.gita.maxwayappclone.domain.usecase

import kotlinx.coroutines.flow.Flow
import uz.gita.maxwayappclone.domain.model.Category

interface GetCategoriesUseCase {
    operator fun invoke(): Flow<Result<List<Category>>>
}
