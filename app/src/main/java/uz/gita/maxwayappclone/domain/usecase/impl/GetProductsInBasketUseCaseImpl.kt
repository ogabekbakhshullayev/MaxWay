package uz.gita.maxwayappclone.domain.usecase.impl

import uz.gita.maxwayappclone.data.model.ProductUIData
import uz.gita.maxwayappclone.domain.repository.ProductRepository
import uz.gita.maxwayappclone.domain.usecase.GetProductsInBasketUseCase

class GetProductsInBasketUseCaseImpl(private val repository: ProductRepository): GetProductsInBasketUseCase {

    override fun invoke(): List<ProductUIData> = repository.getProductsInBasket()
}

