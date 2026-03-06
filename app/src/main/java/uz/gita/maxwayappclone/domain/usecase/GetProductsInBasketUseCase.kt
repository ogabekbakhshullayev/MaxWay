package uz.gita.maxwayappclone.domain.usecase

import uz.gita.maxwayappclone.data.model.ProductUIData

interface GetProductsInBasketUseCase {
    operator fun invoke() : List<ProductUIData>
}