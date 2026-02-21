package uz.gita.maxwayappclone.domain.usecase.impl

import android.view.PixelCopy
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import uz.gita.maxwayappclone.data.source.remote.response.NotificationResponse
import uz.gita.maxwayappclone.domain.repository.NotificationRepository
import uz.gita.maxwayappclone.domain.usecase.NotificationUseCase

class NotificationUseCaseImpl(private val repository: NotificationRepository): NotificationUseCase {
    override fun invoke(): Flow<Result<NotificationResponse>> = flow {
        emit(repository.getAllNotifications())
    }
        .catch { emit(Result.failure(it)) }
        .flowOn(Dispatchers.IO)
}