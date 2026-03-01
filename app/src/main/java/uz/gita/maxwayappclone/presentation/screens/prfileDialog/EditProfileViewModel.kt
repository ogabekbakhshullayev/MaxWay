package uz.gita.maxwayappclone.presentation.screens.prfileDialog

import androidx.lifecycle.LiveData

interface EditProfileViewModel {


    val updateInfoLoadingLiveData: LiveData<Boolean>
    val updateInfoSuccessLiveData: LiveData<String>
    val updateInfoErrorMessageLiveData: LiveData<String>
    val isSuccess: LiveData<Boolean>



    fun updateProfileInfo(token: String, name: String, birthDate: String)

}