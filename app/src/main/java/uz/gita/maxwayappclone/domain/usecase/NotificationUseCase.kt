package uz.gita.maxwayappclone.domain.usecase

import kotlinx.coroutines.flow.Flow
import uz.gita.maxwayappclone.data.source.remote.response.NotificationResponse

interface NotificationUseCase {

    operator fun invoke() : Flow<Result<List<NotificationResponse>>>
}