package uz.gita.maxwayappclone.presentation.screens.basket

import uz.gita.maxwayappclone.domain.model.Product

data class BasketItem(
    val product: Product,
    val count: Int
)
