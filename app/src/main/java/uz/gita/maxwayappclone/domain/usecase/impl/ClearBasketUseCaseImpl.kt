package uz.gita.maxwayappclone.domain.usecase.impl

import uz.gita.maxwayappclone.domain.repository.ProductRepository
import uz.gita.maxwayappclone.domain.usecase.ClearBasketUseCase

class ClearBasketUseCaseImpl(
    private val repository: ProductRepository
) : ClearBasketUseCase {
    override fun invoke() {
        repository.clearProductCounts()
    }
}
