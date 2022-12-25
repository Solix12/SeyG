package uz.salikhdev.seyg.core.models.response


import com.google.gson.annotations.SerializedName

data class Data(
    @SerializedName("password")
    val password: String,
    @SerializedName("username")
    val username: String
)