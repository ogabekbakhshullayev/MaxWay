package uz.gita.maxwayappclone.domain.repository

import uz.gita.maxwayappclone.data.source.remote.response.StoryData

interface StoryRepository {
    suspend fun getStories(): Result<Array<StoryData>>
}