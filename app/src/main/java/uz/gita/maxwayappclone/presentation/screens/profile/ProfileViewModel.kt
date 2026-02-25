package uz.gita.maxwayappclone.presentation.screens.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import uz.gita.maxwayappclone.data.source.remote.response.EditProfileResponse

interface ProfileViewModel {


    val updateInfoLoadingLiveData: LiveData<Boolean>
    val updateInfoSuccessLiveData: LiveData<String>
    val updateInfoErrorMessageLiveData: LiveData<String>

    val getInfoErrorMessageLiveData: LiveData<String>
    val getInfoSuccessLiveData: MutableLiveData<EditProfileResponse>
    val getInfoLoadingLiveData: LiveData<Boolean>


    fun getProfileInfo(token: String)
    fun updateProfileInfo(token: String, name: String, birthDate: String)

}