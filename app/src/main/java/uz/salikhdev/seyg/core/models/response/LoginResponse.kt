package uz.salikhdev.seyg.core.models.response


import com.google.gson.annotations.SerializedName

data class LoginResponse(
    @SerializedName("token")
    val token: String,
    @SerializedName("user")
    val user: User
)