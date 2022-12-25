package uz.salikhdev.seyg.core.models.request


import com.google.gson.annotations.SerializedName

data class RegisterRequest(
    @SerializedName("confirm")
    val confirm: String,
    @SerializedName("password")
    val password: String,
    @SerializedName("username")
    val username: String
)