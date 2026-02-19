package uz.gita.maxwayappclone.presentation.screens.registerPhone

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel

interface RegisterPhoneViewModel {
    val loadingLiveData: LiveData<Boolean>
    val successLiveData: LiveData<String>
    val errorMessageLiveData: LiveData<String>

    fun register(phone: String)
}