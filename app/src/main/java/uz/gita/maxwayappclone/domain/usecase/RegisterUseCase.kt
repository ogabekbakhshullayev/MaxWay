package uz.gita.maxwayappclone.domain.usecase

import kotlinx.coroutines.flow.Flow
import uz.gita.maxwayappclone.data.source.remote.response.NotificationResponse

interface RegisterUseCase {
    operator fun invoke(phone: String) : Flow<Result<Unit>>
}

