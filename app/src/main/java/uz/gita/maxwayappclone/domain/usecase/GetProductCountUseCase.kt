package uz.gita.maxwayappclone.domain.usecase

interface GetProductCountUseCase {
    operator fun invoke(productId: Long): Int
}
