package uz.gita.maxwayappclone.presentation.screens.storiesScreen

import androidx.lifecycle.LiveData
import uz.gita.maxwayappclone.data.source.remote.response.StoryData

interface StoriesViewModel {
    val storiesLiveData: LiveData<Array<StoryData>>
    fun getStories()
}