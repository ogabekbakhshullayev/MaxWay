package uz.gita.maxwayappclone.data.source.remote.response

data class EditProfileResponse (
    val id : Int,
    val name: String,
    val phone: String,
    val birthDate: String,
    val token: String
)