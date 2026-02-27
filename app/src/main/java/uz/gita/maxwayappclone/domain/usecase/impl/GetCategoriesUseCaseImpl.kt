package uz.gita.maxwayappclone.domain.usecase.impl

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import uz.gita.maxwayappclone.domain.model.Category
import uz.gita.maxwayappclone.domain.repository.ProductRepository
import uz.gita.maxwayappclone.domain.usecase.GetCategoriesUseCase

class GetCategoriesUseCaseImpl(
    private val repository: ProductRepository
) : GetCategoriesUseCase {
    override fun invoke(): Flow<Result<List<Category>>> = flow {
        emit(repository.getCategories())
    }
        .catch { emit(Result.failure(it)) }
        .flowOn(Dispatchers.IO)
}
