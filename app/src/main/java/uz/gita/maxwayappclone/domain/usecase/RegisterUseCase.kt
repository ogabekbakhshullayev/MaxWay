package uz.gita.maxwayappclone.domain.usecase

import kotlinx.coroutines.flow.Flow

interface RegisterUseCase {
    operator fun invoke(phone: String) : Flow<Result<Unit>>
}

