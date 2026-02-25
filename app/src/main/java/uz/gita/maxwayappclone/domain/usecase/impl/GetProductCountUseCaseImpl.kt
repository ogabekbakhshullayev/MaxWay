package uz.gita.maxwayappclone.domain.usecase.impl

import uz.gita.maxwayappclone.domain.repository.ProductRepository
import uz.gita.maxwayappclone.domain.usecase.GetProductCountUseCase

class GetProductCountUseCaseImpl(
    private val repository: ProductRepository
) : GetProductCountUseCase {
    override fun invoke(productId: Long): Int {
        return repository.getProductCount(productId)
    }
}
