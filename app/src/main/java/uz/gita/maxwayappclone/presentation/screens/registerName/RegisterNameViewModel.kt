package uz.gita.maxwayappclone.presentation.screens.registerName

import androidx.lifecycle.LiveData

interface RegisterNameViewModel {
    val loadingLiveData: LiveData<Boolean>
    val successLiveData: LiveData<String>
    val errorMessageLiveData: LiveData<String>
    val noConnectionLiveData: LiveData<Boolean>

    fun nameDate(token: String, name: String, date: String)
}