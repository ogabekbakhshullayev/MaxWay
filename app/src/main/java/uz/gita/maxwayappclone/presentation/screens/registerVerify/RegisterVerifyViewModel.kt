package uz.gita.maxwayappclone.presentation.screens.registerVerify

import androidx.lifecycle.LiveData

interface RegisterVerifyViewModel {
    val loadingLiveData: LiveData<Boolean>
    val successLiveData: LiveData<String>
    val errorMessageLiveData: LiveData<String>
    val noConnectionLiveData:LiveData<Boolean>

    fun verify(phone: String, code: Int)
    fun repeat(phone:String)
}