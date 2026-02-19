package uz.gita.maxwayappclone.presentation.screens.registerVerify

import androidx.lifecycle.LiveData

interface RegisterVerifyViewModel {
    val loadingLiveData: LiveData<Boolean>
    val successLiveData: LiveData<String>
    val errorMessageLiveData: LiveData<String>

    fun verify(phone: String, code: Int)
}