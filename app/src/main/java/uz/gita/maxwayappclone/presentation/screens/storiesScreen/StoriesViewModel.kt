package uz.gita.maxwayappclone.presentation.screens.storiesScreen

import androidx.lifecycle.LiveData
import kotlinx.coroutines.flow.Flow
import uz.gita.maxwayappclone.data.source.remote.response.StoryData

interface StoriesViewModel {
    val storiesLiveData: LiveData<Array<StoryData>>
    val timerLiveData: LiveData<Int>
    fun getStories()
    fun startTimer()
    fun flowCountDown(): Flow<Int>
    fun endTimer()
}