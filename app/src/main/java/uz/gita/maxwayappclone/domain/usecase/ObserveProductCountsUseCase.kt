package uz.gita.maxwayappclone.domain.usecase

import kotlinx.coroutines.flow.Flow

interface ObserveProductCountsUseCase {
    operator fun invoke(): Flow<Map<Long, Int>>
}
