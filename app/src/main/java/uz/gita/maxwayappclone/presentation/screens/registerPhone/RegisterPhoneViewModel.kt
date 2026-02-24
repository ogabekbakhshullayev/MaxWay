package uz.gita.maxwayappclone.presentation.screens.registerPhone

import androidx.lifecycle.LiveData

interface RegisterPhoneViewModel {
    val loadingLiveData: LiveData<Boolean>
    val successLiveData: LiveData<String>
    val errorMessageLiveData: LiveData<String>
    val noConnectionLiveData: LiveData<Boolean>

    fun register(phone: String)
}