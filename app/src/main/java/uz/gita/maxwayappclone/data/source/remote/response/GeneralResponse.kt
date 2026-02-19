package uz.gita.maxwayappclone.data.source.remote.response

data class GeneralResponse<T>(
    val message: String,
    val data: T
)
