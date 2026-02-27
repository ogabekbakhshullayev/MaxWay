package uz.gita.maxwayappclone.domain.usecase

interface SetProductCountUseCase {
    operator fun invoke(productId: Long, count: Int)
}
